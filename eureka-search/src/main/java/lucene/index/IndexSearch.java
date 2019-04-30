package lucene.index;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class IndexSearch {

	public void searchMulti() throws IOException, ParseException {
		// 1路径
		Path path = Paths.get("indexdir");
		Directory dir = FSDirectory.open(path);
		// 2搜索对象创建searcher
		// 对接一个输入流
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		// 搜索使用的分词器IK
		Analyzer analyzer = new SmartChineseAnalyzer();
		// 3构建查询条件
		// 指定，查询的多个与名称
		String[] fields = { "title", "sell_point" };
		// 利用多域对象fields,和分词器构造查询条件
		// 查询条件解析器，可以手机查询的环境，生成查询条件对象
		MultiFieldQueryParser paser = new MultiFieldQueryParser(fields, analyzer);
		Query query = paser.parse("平板");
		// 这条件可以使用查询，只要两个域有一个包含词项，就会搜索到
		// 打桩，输出query
		System.out.println(query.toString());
		// 4获取数据for循环遍历；默认情况下，每个doc返回时
		// 封装到一个topDoc的对象中，底层包装了一个数组，评分doc;循环数组，调用api获取数据
		TopDocs topDoc = searcher.search(query, 10);// 返回所有数据前10条

		// 封装了获取doc的所有条件的docs对象
		ScoreDoc[] docs = topDoc.scoreDocs;
		for (ScoreDoc scoreDoc : docs) {
			// 获取document的文件
			Document document = searcher.doc(scoreDoc.doc);
			System.out.println("title:" + document.get("title"));
			System.out.println("sell_point:" + document.get("sell_point"));
			System.out.println("id:" + document.get("id"));
		}

	}

	/**
	 * 词项查询(单域查询) 所有的不同功能
	 */
	@Test
	public void termQuery() throws Exception {
		// 1路径
		Path path = Paths.get("indexdir");
		Directory dir = FSDirectory.open(path);
		// 2搜索对象创建searcher
		// 对接一个输入流
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		// 搜索使用的分词器IK
		Analyzer analyzer = new SmartChineseAnalyzer();

		// TODO 词项查询条件
		Term term = new Term("title", "国家");
		Query query = new TermQuery(term);

		// 这条件可以使用查询，只要两个域有一个包含词项，就会搜索到
		// 打桩，输出query
		System.out.println(query.toString());
		// 4获取数据for循环遍历；默认情况下，每个doc返回时
		// 封装到一个topDoc的对象中，底层包装了一个数组，评分doc;循环数组，调用api获取数据
		TopDocs topDoc = searcher.search(query, 10);// 返回所有数据前10条

		// 封装了获取doc的所有条件的docs对象
		ScoreDoc[] docs = topDoc.scoreDocs;
		for (ScoreDoc scoreDoc : docs) {
			// 获取document的文件
			Document document = searcher.doc(scoreDoc.doc);
			System.out.println("title:" + document.get("title"));
			System.out.println("11:" + document.get("11"));
			System.out.println("id:" + document.get("id"));
		}
	}

	@Test
	public void booleanQuery() throws IOException {
		// 1路径
		Path path = Paths.get("indexdir");
		Directory dir = FSDirectory.open(path);
		// 2搜索对象创建searcher
		// 对接一个输入流
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		// 搜索使用的分词器IK
		Analyzer analyzer = new SmartChineseAnalyzer();

		// TODO 词项查询条件
		Term term1 = new Term("title", "苹果");
		Query query1 = new TermQuery(term1);

		Term term2 = new Term("sell_point", "平板");
		Query query2 = new TermQuery(term2);

		// 指定逻辑关系，title有苹果，sellpoint没平板
		BooleanClause bc1 = new BooleanClause(query1, Occur.MUST);
		BooleanClause bc2 = new BooleanClause(query2, Occur.MUST_NOT);
		BooleanQuery query = new BooleanQuery.Builder().add(bc1).add(bc2).build();

		// 这条件 可以使用查询，只要两个域有一个包含词项，就会搜索到
		// 打桩，输出query
		System.out.println(query.toString());
		// 4获取数据for循环遍历；默认情况下，每个doc返回时
		// 封装到一个topDoc的对象中，底层包装了一个数组，评分doc;循环数组，调用api获取数据
		TopDocs topDoc = searcher.search(query, 10);// 返回所有数据前10条

		// 封装了获取doc的所有条件的docs对象
		ScoreDoc[] docs = topDoc.scoreDocs;
		for (ScoreDoc scoreDoc : docs) {
			// 获取document的文件
			Document document = searcher.doc(scoreDoc.doc);
			System.out.println("title:" + document.get("title"));
			System.out.println("sell_point:" + document.get("sell_point"));
			System.out.println("id:" + document.get("id"));
		}
	}
}
