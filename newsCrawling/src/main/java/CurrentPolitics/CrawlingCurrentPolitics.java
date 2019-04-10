package CurrentPolitics;

import java.io.IOException;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entity.News_souhu;
/**
 * 爬取新闻的详细信息
 * @author KUIKUI
 */
public class CrawlingCurrentPolitics {
	public static String prefix = "http://www.sohu.com/a/";
	private static String url_currentPolitics = "http://v2.sohu.com/public-api/feed?scene=CATEGORY&sceneId=1460&page=1&size=10";//新浪的有关时政的新闻
	private static CrawlingCurrentPolitics ccp = new CrawlingCurrentPolitics();
	public static void main(String[] args) {
		try {
			ccp.getCurrentPoliticsNews(url_currentPolitics);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getCurrentPoliticsNews(String url) throws IOException {
		Connection connect = Jsoup.connect(url).ignoreContentType(true);
		connect.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36");
		 Response response = connect.execute();
		 String jsonBody = response.body();
		 
		 Gson gson = new Gson();
		 List<News_souhu> newsList = gson.fromJson(jsonBody, new TypeToken<List<News_souhu>>() {}.getType());
		 System.out.println(newsList.size());
		 
		 for(News_souhu news: newsList) {
			 System.out.println(news);
		 }
		 getNewsInfo(newsList.get(3));
		
	}
	
	public  void getNewsInfo(News_souhu news) {
		String authorId = news.getAuthorId();
		String id = news.getId();
		String url = prefix+=id+"_"+authorId;
		Connection connect = Jsoup.connect(url);
	
		try {
			 Document document = connect.get();
			 Elements textEles = document.select(".text");
			 Element textTitleEle = textEles.select(".text-title").get(0);
			 Element mpEditor = textEles.select("#mp-editor").get(0);
			 mpEditor.select("#backsohucom").remove();
			// mpEditor = mpEditor.select("#backsohucom").remove().get(0);
			 String titleAndTime = textTitleEle.html();
			 String content = mpEditor.html();
			 System.out.println(titleAndTime);
			 System.out.println(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
