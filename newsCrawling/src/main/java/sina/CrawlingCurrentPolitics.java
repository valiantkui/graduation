package sina;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;

import entity.sina.HelpResult;
import entity.sina.Result;

public class CrawlingCurrentPolitics {

	public static void main(String[] args) {
		CrawlingCurrentPolitics ccp = new CrawlingCurrentPolitics();
		try {
			ccp.crawlingCurrentPolitics();
			//ccp.crawlingNewsInfo();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void crawlingCurrentPolitics() throws IOException {
		String url = "https://interface.sina.cn/news/get_news_by_channel_new_v2018.d.html?cat_1=51923&show_num=500&level=1,2&page=2&_=1551542400000";
		
		Connection connect = Jsoup.connect(url).ignoreContentType(true);
		Response response = connect.execute();
		String body = response.body();
		//System.out.println(body);
		Gson gson = new Gson();
		HelpResult helpResult = gson.fromJson(body, HelpResult.class);
		Result result = helpResult.getResult();
		System.out.println(result.getData().size());
		
	}
	
	public void crawlingNewsInfo() throws IOException {
		String url = "https://news.sina.com.cn/w/2019-03-02/doc-ihrfqzkc0625507.shtml";
		Connection connect = Jsoup.connect(url);
		Document document = connect.get();
		Element ele = document.select("#article_content .article-content-left").get(0);
		System.out.println(ele.html());
		
	}
}
