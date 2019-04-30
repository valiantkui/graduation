package kui.feign_client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kui.feign_client.entity.News;

@FeignClient("service-news")
@Service
public interface NewsService {

	
	@RequestMapping("news/findNewsByType")
	public List<News> findNewsByType(@RequestParam("type") String type,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage);

	
	@RequestMapping("news/getNewsNumByType")
	public int getNewsNumByType(@RequestParam("type") String type);


	@RequestMapping("news/findAllNews")
	public List<News> findAllNews();

	@RequestMapping("news/findNewsByN_no")
	public News findNewsByN_no(@RequestParam("n_no") int n_no);

	@RequestMapping("news/findNewsByN_noList")
	public List<News> findNewsByN_noList(@RequestBody List<Integer> n_noList);

	@RequestMapping("news/findTopNNews")
	public List<News> findTopNNews(@RequestParam("num") int num);

	
	
}
