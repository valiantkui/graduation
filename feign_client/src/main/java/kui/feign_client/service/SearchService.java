package kui.feign_client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kui.feign_client.entity.News;

@FeignClient("service-search")
public interface SearchService {

	@RequestMapping("search/searchNews")
	public List<News> searchNews(@RequestParam("searchContent") String searchContent,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage);
		
	@RequestMapping("search/getSearchNewsNum")
	public int getSearchNewsNum(@RequestParam("searchContent") String searchContent) ;

}
