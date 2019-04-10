package kui.eureka_search.controller;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.common.entity.News;

@Controller
@RequestMapping("search")
public class SearchController {

	@RequestMapping("/getSearchNewsNum")
	@ResponseBody
	public int getSearchNewsNum(@RequestParam("searchContent") String searchContent) {
		Object[] arr = null;
		try {
			arr = getTopDocs(searchContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TopDocs topDoc = (TopDocs) arr[0];
		return (int) topDoc.totalHits.value;
	}
	
	
	
	@RequestMapping("/searchNews")
	@ResponseBody
	public List<News> searchNews(@RequestParam("searchContent") String searchContent,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){
		
		System.out.println("searchContent:"+searchContent+",currentPage:"+currentPage+",numPerPage:"+numPerPage);
		try {
			Object[] arr = getTopDocs(searchContent);
			TopDocs topDoc = (TopDocs) arr[0];
			IndexSearcher searcher =(IndexSearcher) arr[1];
			//封装了获取doc的所有条件的docs对象
			ScoreDoc[] docs = topDoc.scoreDocs;
			
			int begin = (currentPage-1)*numPerPage;
			int end = (int) Math.min(begin + numPerPage,topDoc.totalHits.value);
			List<News> newsList = new ArrayList<News>();
			for (int i = begin;i<end;i++) {
				//获取document的文件
				Document document = searcher.doc(docs[i].doc);
				System.out.print("n_no:" + document.get("n_no")+",");
				System.out.println("title:" + document.get("title"));
				
				News n = new News();
				n.setN_no(Integer.parseInt(document.get("n_no")));
				n.setTitle(document.get("title"));
				n.setAuthor(document.get("author"));
				n.setOrigin(document.get("origin"));
				n.setType(document.get("type"));
				n.setImg_url(document.get("img_url"));
				n.setPublish_date(document.get("publish_date"));
				
				newsList.add(n);
			} 
			return newsList;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return null;
	}
	
	
	public List<String>  participle(String searchContent) throws Exception {
		List<String> list = new ArrayList<String>();
		
		Analyzer analyzer = new SmartChineseAnalyzer();
		StringReader reader = new StringReader(searchContent);
    	//analyzer的实现主要是靠一个叫做tokenStream的流
    	//作用就是把流对象转化成根据底层分词计算得到的数据获取
    	//原有的流对象可以被其计算分成流的集合
    	TokenStream token = analyzer.tokenStream("msg", reader);
    	token.reset();
    	//重置初始化信息，此项数据结构携带(词项，位移，偏移量，频率)
    	//重置可以是一些多余的信息从初始化开始计算，被携带
    	//从token的流中获取我们真正想要的分词结果
    	CharTermAttribute attribute = token.getAttribute(CharTermAttribute.class);
    	while(token.incrementToken()) {
    		System.out.println(attribute.toString());
    		list.add(attribute.toString());
    	}
    	token.close();
    	return list;
	}
	public Object[] getTopDocs(String searchContent) throws IOException, ParseException, Exception {
		//搜索使用的分词器:智能分词器
		Analyzer analyzer = new SmartChineseAnalyzer();
		List<String> searchList = participle(searchContent);
		Path path = Paths.get("indexdir");
		Directory dir = FSDirectory.open(path);
		//2搜索对象创建searcher
		//对接一个输入流
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		//BooleanQuery bq = new BooleanQuery.Builder().add(bc1).add(bc2).build();
		BooleanQuery.Builder builder = new BooleanQuery.Builder();
		for(String sl:searchList) {
			String[] fields = {"title", "author","type","origin"};
			MultiFieldQueryParser paser = new MultiFieldQueryParser(fields, analyzer);
			Query query = paser.parse(searchContent);
			BooleanClause bc = new BooleanClause(query,Occur.SHOULD);
			builder.add(bc);
		}
		BooleanQuery booleanQuery = builder.build();
		System.out.println("查询条件："+booleanQuery.toString());
		Sort sort = new Sort(new SortField("n_no", Type.INT,true));
		
		//4获取数据for循环遍历；默认情况下，每个doc返回时
		//封装到一个topDoc的对象中，底层包装了一个数组，评分doc;循环数组，调用api获取数据 
		TopDocs topDoc = searcher.search(booleanQuery, Integer.MAX_VALUE,sort);//返回所有数据前10条
		Object[] arr = new Object[2];
		arr[0] = topDoc; 
		arr[1] = searcher;
		return arr;
	}
}
