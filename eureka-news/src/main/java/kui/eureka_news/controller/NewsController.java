package kui.eureka_news.controller;

import java.util.List;

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

	
	@Autowired
	private NewsService newsService;
	
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
	
	@RequestMapping("/insertNewsList")
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
}
