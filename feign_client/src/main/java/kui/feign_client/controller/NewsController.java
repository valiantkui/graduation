package kui.feign_client.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.feign_client.entity.News;
import kui.feign_client.entity.User;
import kui.feign_client.service.NewsService;
import kui.feign_client.service.RecommendService;
import kui.feign_client.service.SearchService;
import kui.feign_client.service.UserService;

@Controller
@RequestMapping("news")
public class NewsController {

	
	@Autowired
	private NewsService newsService;

	@Autowired
	private SearchService searchService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping("/findNewsByType")
	@ResponseBody
	public List<News> findNewsByType(@RequestParam("type") String type,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){
		
		if("推荐".equals(type)) {
			User currentUser = userService.getCurrentUser();
			List<Integer> n_noList = recommendService.recommendNewsByUserId(currentUser.getId());
			 System.out.println("----------推荐---------------");
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
		return newsService.findNewsByType(type, currentPage, numPerPage);
	}
	
	@RequestMapping("/findAllNews")
	@ResponseBody
	public List<News> findAllNews(){
		return newsService.findAllNews();
	}
	@RequestMapping("/getNewsNumByType")
	@ResponseBody
	public int getNewsNumByType(@RequestParam("type") String type) {
		
		
		return newsService.getNewsNumByType(type);
	}
	
	@RequestMapping("/loadNewsContent")
	@ResponseBody
	public String loadNewsContent(@RequestParam("n_no") int n_no) {
		return newsService.findNewsByN_no(n_no).getNews_text();
	}
	
	@RequestMapping("/searchNews")
	@ResponseBody
	public List<News> searchNews(@RequestParam("searchContent") String searchContent,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){
		return searchService.searchNews(searchContent,currentPage,numPerPage);
	}
	
	@RequestMapping("/getSearchNewsNum")
	@ResponseBody
	public int getSearchNewsNum(@RequestParam("searchContent") String searchContent) {
		return searchService.getSearchNewsNum(searchContent);
	}
	
}
