package kui.eureka_search.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.eureka_search.entity.News;
import kui.eureka_search.tools.DBTools;

@Controller
@RequestMapping("index")
public class IndexController {

	@RequestMapping("/createIndex")
	@ResponseBody
	public boolean createIndex() throws IOException {
		Path indexPath = Paths.get("indexdir");
		// lucene的dir对象可以实现索引的流输出
		Directory dir = FSDirectory.open(indexPath);
		// 第二步：引入一个创建索引计算分词的分词器
		Analyzer analyzer = new SmartChineseAnalyzer();

		// 将analyzer添加到索引创建的配置对象中；
		IndexWriterConfig config = new IndexWriterConfig(analyzer);

		// 写出的indexdir文件在第二次创建时，会被覆盖
		config.setOpenMode(OpenMode.CREATE);
		// config.setOpenMode(OpenMode.APPEND);
		// config.setOpenMode(OpenMode.CREATE_OR_APPEND);
		// 第三步：生成索引文件保存的document文档对象；

		java.sql.Connection conn = DBTools.getConnection();

		IndexWriter indexWriter = new IndexWriter(dir, config);
		System.out.println(IndexWriter.MAX_DOCS);
		System.out.println(IndexWriter.MAX_STORED_STRING_LENGTH);
		System.out.println(IndexWriter.MAX_TERM_LENGTH);
		Statement stat = null;
		ResultSet rs = null;
		List<News> newsList = new ArrayList<>();
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select distinct title,n_no,author,img_url,type,origin,origin_url,publish_date,page_view from news");
			while (rs.next()) {
				News n = new News();
				n.setN_no(rs.getInt("n_no"));
				n.setTitle(rs.getString("title"));
				n.setAuthor(rs.getString("author"));
				n.setType(rs.getString("type"));
				//n.setNews_text(rs.getString("news_text"));
				n.setImg_url(rs.getString("img_url"));
				n.setOrigin(rs.getString("origin"));
				n.setPublish_date(rs.getString("publish_date"));
				newsList.add(n);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("数据条数：" + newsList.size());

		for (News n : newsList) {
			Document doc = new Document();
			System.out.println(n.getN_no());
			doc.add(new NumericDocValuesField("n_no", n.getN_no()));// 做排序使用
			doc.add(new StringField("n_no", n.getN_no() + "", Store.YES));
			doc.add(new TextField("title", n.getTitle(), Store.YES));
			doc.add(new TextField("author", n.getAuthor(), Store.YES));
			doc.add(new StringField("type", n.getType(), Store.YES));
			// doc.add(new StringField("news_text_store", n.getNews_text(), Store.YES));
			// doc.add(new TextField("news_text", new Element(n.getNews_text()).text(),
			// Store.NO));
			doc.add(new StringField("img_url", n.getImg_url() + "", Store.YES));
			doc.add(new StringField("origin", n.getOrigin(), Store.YES));
			doc.add(new StringField("publish_date", n.getPublish_date(), Store.YES));
			indexWriter.addDocument(doc);
		}
		indexWriter.commit();
		indexWriter.close();
		return true;
	}

}
