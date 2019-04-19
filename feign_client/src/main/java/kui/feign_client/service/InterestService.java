package kui.feign_client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-recommend")
@Service
public interface InterestService {

	
	@RequestMapping("interest/insertInterest")
	public boolean insertInterest(
			@RequestParam("u_id") String u_id,
			@RequestParam("n_no") int n_no,
			@RequestParam("val") float val,
			@RequestParam("timestamp") long timestamp);

}
