package kui.crawlingClient.task;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kui.crawlingClient.service.SohuService;
import kui.crawlingClient.service.SearchService;

@Component
public class Scheduling {
	private int NUM_CURRENT_POLITICS = 50;
	private int NUM_NBA = 20;
	private int NUM_GAME = 20;
	private int NUM_FOOD = 50;
	private int NUM_SCIENCE = 30;
	private int NUM_TOUR = 50;
	
	@Autowired
	private SohuService sohuService;
	
	@Autowired
	private SearchService searchService;
	/**
	 * 向本机发送阻塞式请求
	 */
	@Scheduled(fixedRate=86400000)
	public void task1() {
		System.out.println("task1:"+new Date());
		try {
			System.out.println("获取时政的新闻");
			replaceNews();//删掉数据库中过期的新闻
			//阻塞式请求
			Jsoup.connect("http://localhost:8800/sohu/crawlingCurrentPolitics").ignoreContentType(true).execute();
			System.out.println("获取NBA的新闻");
			Jsoup.connect("http://localhost:8800/sohu/crawlingNBA").ignoreContentType(true).execute();
			System.out.println("获取游戏的新闻");
			Jsoup.connect("http://localhost:8800/sohu/crawlingGame").ignoreContentType(true).execute();
			System.out.println("获取美食的新闻");
			Jsoup.connect("http://localhost:8800/sohu/crawlingFood").ignoreContentType(true).execute();
			System.out.println("获取科技的新闻");
			Jsoup.connect("http://localhost:8800/sohu/crawlingScience").ignoreContentType(true).execute();
			System.out.println("获取旅游的新闻");
			Jsoup.connect("http://localhost:8800/sohu/crawlingTour").ignoreContentType(true).execute();
			//更新索引
			System.out.println("重新创建索引是否成功："+searchService.createIndex());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新数据库，将数据库中日期比较老的新闻删掉
	 */
	private void replaceNews() {
		if(sohuService.getNewsCount()<10000) return;
	//	String[] typeArr = new String[] {"时政","NBA","游戏","美食","科技","旅游"};
		
		sohuService.deleteNewsByTypeAndNum("时政", NUM_CURRENT_POLITICS);
		sohuService.deleteNewsByTypeAndNum("NBA", NUM_NBA);
		sohuService.deleteNewsByTypeAndNum("游戏", NUM_GAME);
		sohuService.deleteNewsByTypeAndNum("美食", NUM_FOOD);
		sohuService.deleteNewsByTypeAndNum("科技", NUM_SCIENCE);
		sohuService.deleteNewsByTypeAndNum("旅游", NUM_TOUR);
		
	}
}
