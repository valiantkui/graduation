package kui.feign_client.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.feign_client.entity.News;
import kui.feign_client.service.NewsService;
import kui.feign_client.service.SearchService;

@Controller
@RequestMapping("news")
public class NewsController {

	
	@Autowired
	private NewsService newsService;

	@Autowired
	private SearchService searchService;
	
	@RequestMapping("/findNewsByType")
	@ResponseBody
	public List<News> findNewsByType(@RequestParam("type") String type,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){
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
