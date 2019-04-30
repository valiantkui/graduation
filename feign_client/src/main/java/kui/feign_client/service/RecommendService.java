package kui.feign_client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kui.feign_client.entity.News;

@FeignClient("service-recommend")
@Service
public interface RecommendService {

	
	@RequestMapping("interest/insertInterest")
	public boolean insertInterest(
			@RequestParam("id") int id,
			@RequestParam("n_no") int n_no,
			@RequestParam("val") float val,
			@RequestParam("timestamp") long timestamp);

	@RequestMapping("recommend/recommendNewsByUserId")
	public List<Integer> recommendNewsByUserId(@RequestParam("userId") int userId);

}
