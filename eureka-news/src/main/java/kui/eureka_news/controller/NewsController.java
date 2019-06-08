package kui.eureka_news.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.eureka_news.entity.News;
import kui.eureka_news.service.NewsService;

@Controller
@RequestMapping("news")
public class NewsController {

	
	private  List<News> list = new ArrayList<>();
	
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping("/findNewsByType")
	@ResponseBody
	public List<News> findNewsByType(@RequestParam("type") String type,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){
		return newsService.findNewsByType(type, currentPage, numPerPage);
	}
	
	
	
	@RequestMapping("/quchong")
	@ResponseBody
	public boolean deleteChongfu() {
		
		List<News> newsList = newsService.findAllNews2();
		
		Set<String> set = new HashSet<>();
		Set<Integer> integerSet = new HashSet<>();
		for(News n: newsList) {
			if(!set.contains(n.getTitle())) {
				
				set.add(n.getTitle());
				integerSet.add(n.getN_no());
			}
			
		}
		
		for(News n: newsList) {
			if(integerSet.contains(n.getN_no())) {
				list.add(n);
			}
		}
		
		System.out.println();
		
		return false;
	}
	
	

	
	@RequestMapping("/charu")
	@ResponseBody
	public boolean charu() {
		System.out.println(list.size());
		return newsService.insertNewsList(list);
		
	}
	
	@RequestMapping("/findAllNews")
	@ResponseBody
	public List<News> findAllNews(){
		return newsService.findAllNews();
	}
	
	@RequestMapping(value="/insertNewsList",produces="application/json;charset=utf-8")
	@ResponseBody
	public boolean insertNewsList(@RequestBody List<News> newsList) {
		
		System.out.println("NewsController---size:"+newsList.size());
		return newsService.insertNewsList(newsList);
	}
	@RequestMapping("/getNewsNumByType")
	@ResponseBody
	public int getNewsNumByType(@RequestParam("type") String type) {
		
		
		return newsService.getNewsNumByType(type);
	}
	
	@RequestMapping("/findNewsByN_no")
	@ResponseBody
	public News findNewsByN_no(@RequestParam("n_no") int n_no) {
		return newsService.findNewsByN_no(n_no);
	}
	
	@RequestMapping("/findNewsByN_noList")
	@ResponseBody
	public List<News> findNewsByN_noList(@RequestBody List<Integer> n_noList){
		
		
		return newsService.findNewsByN_noList(n_noList);
	}
	
	
	@RequestMapping("/getNewsCount")
	@ResponseBody
	public int getNewsCount() {
		return newsService.getNewsCount();
	}
	/**
	 * 将新闻按照日期排序，删除日期最久的若干条指定类型的新闻记录
	 * @param type 新闻类型
	 * @param limit 删除的条数
	 * @return 是否成功
	 */
	@RequestMapping("/deleteNewsByTypeAndNum")
	@ResponseBody
	public boolean deleteNewsByTypeAndNum(@RequestParam("type") String type,@RequestParam("limit") int limit) {

		
		return newsService.deleteNewsByTypeAndNum(type,limit);
		
	}
	
	/**
	 * 获取新闻中浏览数最高的N条新闻
	 * @param num
	 * @return
	 */
	@RequestMapping(value="/findTopNNews",produces="application/json;charset=utf-8")
	@ResponseBody
	public List<News> findTopNNews(@RequestParam("num") int num){
		return newsService.findTopNNews(num);
	}
}
