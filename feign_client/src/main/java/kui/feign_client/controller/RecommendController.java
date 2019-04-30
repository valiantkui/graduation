package kui.feign_client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.feign_client.entity.News;
import kui.feign_client.service.NewsService;
import kui.feign_client.service.RecommendService;
import kui.feign_client.service.UserService;

@Controller
@RequestMapping("recommend")
public class RecommendController {

	
	@Autowired
	private NewsService newsService;

	
	@Autowired
	private UserService userSerivce;
	@Autowired
	private RecommendService recommendService;
	
	 @RequestMapping("/insertInterest")
	 @ResponseBody
	 public boolean insertInterest(int id,int n_no,float val,long timestamp) {
		System.out.println("---------insertInterest()--------");
		 
		 return recommendService.insertInterest(id,n_no,val,timestamp);
	 }
	 
	@RequestMapping("/recommendNewsByUserId")
	@ResponseBody
	public List<News> recommendNewsByUserId(@RequestParam("userId") int userId){
		
	
		System.out.println("----------推荐---------------");
		List<Integer> n_noList = recommendService.recommendNewsByUserId(userId);
		System.out.println("集合n_noList:"+n_noList);
		List<News> newsList = new ArrayList<>();
		if(n_noList != null && n_noList.size() != 0) {
			newsList.addAll(newsService.findNewsByN_noList(n_noList));
		}
		System.out.println("获取前60热度的新闻");
		//获取60个最热度新闻
		newsList.addAll(newsService.findTopNNews(60));
		System.out.println("推荐的新闻："+newsList);
		return newsList;
	}
	 
}
