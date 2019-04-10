package souhu;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entity.News;
import entity.News_souhu;

public class CrawlingCurrentPolitics {

	public static void main(String[] args) {
		try {
			CrawlingCurrentPolitics ccp = new CrawlingCurrentPolitics();
			
			ccp.crawling();
		}catch(Exception e) {
			
		}
	}
	
	
	public void crawling() throws IOException {
		String url = "http://v2.sohu.com/public-api/feed?scene=CATEGORY&sceneId=1460&page=1&size=100";
		
		Connection connection = Jsoup.connect(url).ignoreContentType(true);
		Response response = connection.execute();
		String jsonStr = response.body();
		Gson gson = new Gson();
		List<News_souhu> newsList = gson.fromJson(jsonStr, new TypeToken<List<News_souhu>>() {}.getType());
		System.out.println(newsList.size());
		
		String suffix = "http://www.sohu.com/a/";
		for(News_souhu n: newsList) {
			String id = n.getId();
			String authorId = n.getAuthorId();
			String url_newsInfo = suffix+id+"_"+authorId;
			Connection connect = Jsoup.connect(url_newsInfo);
			Document document = connect.get();
			Element mpEditor = document.select("#mp-editor").get(0);
			mpEditor.select("#backsohucom").get(0).remove();
			
			
			System.out.println("是否存在："+document.select(".article-oper .read-num em").size());
			
			String pageView = document.select(".article-oper .read-num em").get(0).text();
			
			
			System.out.println("阅读量："+pageView);
			String news_text = mpEditor.html();
			System.out.println(news_text);
			
			News news = new News();
			news.setAuthor(n.getAuthorName());
			news.setImg_url(n.getPicUrl());
			news.setNews_text(news_text);
			news.setOrigin("搜狐");
			news.setOrigin_url(url_newsInfo);
			news.setPage_view(0);
			news.setPublish_date(n.getPublicTime());
			news.setTitle(n.getTitle());
			news.setType("时政");
		}
		
		System.out.println(newsList);
		
		
	}
}
