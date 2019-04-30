package lucene.index;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

public class IndexCreate {

	/**
	 * 创建索引，基本单位文档，只要有文档对象 索引就可以创建，手动拼接文档数据
	 * 
	 * @throws IOException
	 */
	@Test
	public void createIndex() throws IOException {
		// 第一步：指向一个索引文件的目录(文件夹),写出的索引文件都保存在这个目录
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
		Document doc1 = new Document();

		// 在其中添加些数据，3-5域字段值
		// id表示当前域的名称，相当于字段名，100表示值
		// Store.YES表示文档对象一旦存储到索引文件，占用空间
		// 对于一些没有必要存储在文件中的数据可以调用Store.NO
		// 在创建索引文件时，该字段的值，不会保存在文档中，即使搜到了文档对象，也不能获取
		// 域的类型，String---varchar--StringField/TextField
		doc1.add(new StringField("id", "100", Store.YES));
		doc1.add(new TextField("title", "全国人大代表：续征国家重大水利工程建设基金，保障三峡后续", Store.NO));
		doc1.add(new StringField("11", "全国人大代表：续征国家重大水利工程建设基金，保障三峡后续", Store.YES));

		// 第四步，将文档数据输出到索引文件
		IndexWriter writer = new IndexWriter(dir, config);
		// 将文档数据添加到输出流中
		writer.addDocument(doc1);

		writer.commit();
		writer.close();
		dir.close();

	}

	/**
	 * 更新索引
	 */
	@Test
	public void updataIndex() {
		Path indexPath = Paths.get("indexdir");
		Directory dir;
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		try {
			// 第一步：指向dir
			dir = FSDirectory.open(indexPath);
			// 第二部：构造新的对象数据
			Document doc = new Document();
			IndexWriter writer = new IndexWriter(dir, config);
			doc.add(new StringField("id", "2", Store.YES));
			doc.add(new TextField("title", "达内大平板，我们不是专业的", Store.YES));
			doc.add(new TextField("sell_point", "好用", Store.YES));
			// 第二步，根据条件更新覆盖原文档对象
			// 参数term：域名，指定要更新的域名（域名，根据域名搜索）
			// 参数2：与数据的分词词项，比如三星，搜索带有三星分词词项的
			// 第一个document对象进行覆盖
			writer.updateDocument(new Term("title", "苹果"), doc);
			writer.commit();
			writer.close();
			dir.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除索引
	 */
	@Test
	public void deleteIndex() {
		Path indexPath = Paths.get("indexdir");
		Directory dir;
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		try {
			dir = FSDirectory.open(indexPath);
			IndexWriter writer = new IndexWriter(dir, config);

			// 词项对比删除：必须词项完全匹配，才可以删除
			// Query：查询对象,Term,匹配规则
			writer.deleteDocuments(new Term("sell_point", "不要钱"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
