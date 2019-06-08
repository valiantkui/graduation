package kui.crawlingClient.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kui.crawlingClient.entity.News;



@Service
@FeignClient("service-news")
public interface SohuService {

	@RequestMapping(value="news/insertNewsList",method=RequestMethod.POST)
	public boolean insertNewsList(@RequestBody List<News> newsList);
	
	@RequestMapping(value="news/getNewsCount",method=RequestMethod.GET)
	public int getNewsCount();
	@RequestMapping(value="news/deleteNewsByTypeAndNum",method=RequestMethod.GET)
	public boolean deleteNewsByTypeAndNum(@RequestParam("type") String type,@RequestParam("limit") int limit);
	
	
}
