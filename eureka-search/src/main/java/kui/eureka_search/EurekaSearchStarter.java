package kui.eureka_search;
import java.io.IOException;
import java.io.StringReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Hello world!
 */
@SpringBootApplication
public class EurekaSearchStarter {
	/**
	 * 根据词条化的理解，原文件中的内容 可以被词条化，这种计算再lucene,开放了一个接口 analyzer,只要实现了这个接口，就可以将字符串进行流的计算
	 * 不同语言有各种各样analyzer--分词器 lucene自带的分词器standardAnalyzer，whitespaceanalyzer
	 * simpleanalyzer;第三方的智能中文分词器，IK分词器
	 * @param args
	 */
	// 调用该方法，传入一个分词器
	// 传入一个字符串
	// 调用analyzer的代码，完成对msg分词计算后的词项输出
	public static void main(String[] args) {
		SpringApplication.run(EurekaSearchStarter.class, args);
	}
	// 调用该方法，传入一个分词器
	// 传入一个字符串
	// 调用analyzer的代码，完成对msg分词计算后的词项输出
	public static void printAnalyzerString(Analyzer analyzer, String msg) throws IOException {
		// 将字符串读到流对象当中
		StringReader reader = new StringReader(msg);
		// analyzer的实现主要是靠一个叫做tokenStream的流
		// 作用就是把流对象转化成根据底层分词计算得到的数据获取
		// 原有的流对象可以被其计算分成流的集合
		TokenStream token = analyzer.tokenStream("msg", reader);
		token.reset();
		// 重置初始化信息，此项数据结构携带(词项，位移，偏移量，频率)
		// 重置可以是一些多余的信息从初始化开始计算，被携带
		// 从token的流中获取我们真正想要的分词结果
		CharTermAttribute attribute = token.getAttribute(CharTermAttribute.class);
		while (token.incrementToken()) {
			System.out.println(attribute.toString());
		}
	}
	@Test
	public void testAnalyzer() throws IOException {
		String msg = "许久不见，多多挂念，明日三更，松树林见";
		Analyzer a1 = new SmartChineseAnalyzer();
		Analyzer a2 = new SimpleAnalyzer();
		Analyzer a3 = new StandardAnalyzer();
		System.out.println("****我是智能中文分词器****");
		EurekaSearchStarter.printAnalyzerString(a1, msg);
		System.out.println("****我是简单分词器****");
		EurekaSearchStarter.printAnalyzerString(a2, msg);
		System.out.println("****我是标准分词器****");
		EurekaSearchStarter.printAnalyzerString(a3, msg);
	}
}