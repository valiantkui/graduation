package kui.crawlingClient.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kui.crawlingClient.entity.News;
import kui.crawlingClient.entity.News_souhu;
import kui.crawlingClient.entity.SohuNBA;
import kui.crawlingClient.entity.SohuResult;
import kui.crawlingClient.service.SohuService;



@Controller
@RequestMapping("sohu")
public class SohuController {

	@Autowired
	private SohuService sohuService;
	
	/**
	 * 一次爬取50条
	 * @return
	 */
	@RequestMapping("/crawlingCurrentPolitics")
	@ResponseBody
	public boolean crawlingCurrentPolitics() {
		String url = "http://v2.sohu.com/public-api/feed?scene=CATEGORY&sceneId=1460&page=1&size=50";
		List<News> list = getNewsByUrl(url, "时政");
		return sohuService.insertNewsList(list);
	}
	
	/**
	 * 一次爬取20条
	 * @return
	 */
	@RequestMapping("/crawlingNBA")
	@ResponseBody
	public boolean crawlingNBA() {
		String url = "http://v2.sohu.com/integration-api/mix/region/4358?adapter=pc&page=5&pvId=15515964486545mv656i&requestId=1901081934443675_1551596496996&_=1551596449416";
		List<News> list = getNewsByUrl2(url, "NBA");
		
		return sohuService.insertNewsList(list);
	}
	
	/**
	 * 一次爬取20条
	 * @return
	 */
	@RequestMapping("/crawlingGame")
	@ResponseBody
	public boolean crawlingGame() {
		String url = "http://v2.sohu.com/public-api/feed?scene=CHANNEL&sceneId=42&page=1&size=20";
		List<News> list = getNewsByUrl(url, "游戏");
		return sohuService.insertNewsList(list);
	}
	
	/**
	 * 一次爬取50条
	 * @return
	 */
	@RequestMapping("/crawlingFood")
	@ResponseBody
	public boolean crawlingFood() {
		String url = "http://v2.sohu.com/public-api/feed?scene=CHANNEL&sceneId=28&page=1&size=50";
		List<News> list = getNewsByUrl(url, "美食");
		return sohuService.insertNewsList(list);
	}
	
	/**
	 * 一次爬取30条
	 * @return
	 */
	@RequestMapping("/crawlingScience")
	@ResponseBody
	public boolean crawlingScience() {
		String url = "http://v2.sohu.com/integration-api/mix/region/5676?size=25&adapter=pc&secureScore=50&page=1&pvId=1552808645294fGGADkR&requestId=1901081934443675_1552808680162";
		List<News> list = getNewsByUrl2(url, "科技");
		return sohuService.insertNewsList(list);
	}
	/**
	 * 一次爬取50条
	 * @return
	 */
	@RequestMapping("/crawlingTour")
	@ResponseBody
	public boolean crawlingTour() {
		String url = "http://v2.sohu.com/public-api/feed?scene=CHANNEL&sceneId=29&page=1&size=50";
		List<News> list = getNewsByUrl(url,"旅游");
		return sohuService.insertNewsList(list);
	}
	
	private List<News> getNewsByUrl2(String url,String type){
		
		List<News> list = new ArrayList<News>();
		Connection connection = Jsoup.connect(url).ignoreContentType(true);
		Response response = null;
		try {
			response = connection.execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jsonStr = response.body();
		Gson gson = new Gson();
		SohuResult sr = gson.fromJson(jsonStr, new TypeToken<SohuResult>(){}.getType());
		List<SohuNBA> newsList = sr.getData();
		//System.out.println(newsList.size());
		for(SohuNBA n: newsList) {
			if(n.getUrl()==null || "".equals(n.getUrl().trim())) continue;
			
			String url2 = "http:"+n.getUrl();
			Connection connect = Jsoup.connect(url2).ignoreContentType(true);
			Document document = null;
			String pageView = null;
			try {
				document = connect.get();
				pageView = Jsoup.connect("http://v2.sohu.com/public-api/articles/"+n.getId()+"/pv").ignoreContentType(true).execute().body();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			Elements eles = document.select("#mp-editor");
			if(eles.isEmpty()) continue;
	
			Element mpEditor = eles.get(0);
			mpEditor.select("#backsohucom").get(0).remove();
			
			//System.out.println("是否存在："+document.select(".article-oper .read-num em").size());
			
			
			System.out.println("阅读量："+pageView);
			String news_text = mpEditor.html();
			//System.out.println(news_text);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//System.out.println(n.getPublicTime());
			String date = sdf.format(new Date(Long.parseLong(n.getPublicTime())));
			News news = new News();
			news.setAuthor(n.getAuthorName());
			if(n.getImages()==null || n.getImages().size()==0) news.setImg_url(null);
			else news.setImg_url(n.getImages().get(0));
			
			
			news.setNews_text(news_text);
			news.setOrigin("搜狐");
			news.setOrigin_url(url2);
			if(pageView !=null) news.setPage_view(Integer.parseInt(pageView));
			news.setPublish_date(date);
			news.setTitle(n.getTitle());
			news.setType(type);
			list.add(news);
		}
		return list;
	}
	
	private List<News> getNewsByUrl(String url,String type){
		List<News> list = new ArrayList<News>();
		//String url = "http://v2.sohu.com/public-api/feed?scene=CHANNEL&sceneId=28&page=1&size=200";
		
		Connection connection = Jsoup.connect(url).ignoreContentType(true);
		Response response = null;
		try {
			response = connection.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jsonStr = response.body();
		Gson gson = new Gson();
		List<News_souhu> newsList = gson.fromJson(jsonStr, new TypeToken<List<News_souhu>>() {}.getType());
		//System.out.println(newsList.size());
		
		String suffix = "http://www.sohu.com/a/";
		for(News_souhu n: newsList) {
			String id = n.getId();
			String authorId = n.getAuthorId();
			String url_newsInfo = suffix+id+"_"+authorId;
			//System.out.println("连接地址："+url_newsInfo);
			Connection connect = Jsoup.connect(url_newsInfo);
			String pageView = null;
			Document document = null;
			try {
				document = connect.get();
				pageView = Jsoup.connect("http://v2.sohu.com/public-api/articles/"+id+"/pv").ignoreContentType(true).execute().body();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Elements eles = document.select("#mp-editor");
			if(eles.isEmpty()) continue;
	
			Element mpEditor = eles.get(0);
			mpEditor.select("#backsohucom").get(0).remove();
			//System.out.println("是否存在："+document.select(".article-oper .read-num em").size());
			System.out.println("阅读量："+pageView);
			String news_text = mpEditor.html();
			//System.out.println(news_text);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//System.out.println(n.getPublicTime());
			String date = sdf.format(new Date(Long.parseLong(n.getPublicTime())));
			News news = new News();
			news.setAuthor(n.getAuthorName());
			news.setImg_url(n.getPicUrl());
			news.setNews_text(news_text);
			news.setOrigin("搜狐");
			news.setOrigin_url(url_newsInfo);
			if(pageView !=null)
			news.setPage_view(Integer.parseInt(pageView));
			news.setPublish_date(date);
			news.setTitle(n.getTitle());
			news.setType(type);
			list.add(news);
		}
		return list;
	}
	
	
	public boolean storeNewsFile(String content,String path) throws IOException {
		File file = new File(path);
		
		if(!file.getParentFile().exists())file.getParentFile().mkdirs();
		if(!file.exists()) file.createNewFile();
		
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		bos.write(content.getBytes());
		bos.flush();
		
		return true;
		
		
		
		
	}
}
