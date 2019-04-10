package film;


import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import entity.News_souhu;

public class CrawlingFilm {

	private static String url = "http://v2.sohu.com/public-api/feed?scene=CATEGORY&sceneId=1460&page=1&size=10";//新浪的有关时政的新闻
	
	public static void main(String args[]) {
		CrawlingFilm crawlingFilm = new CrawlingFilm();
		try {
			crawlingFilm.getFileNews(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据url获取所有新闻，并封装成对象
	 * @param url
	 * @throws IOException 
	 */
	public void getFileNews(String url) throws IOException {
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
		 
		
		
	}
}
