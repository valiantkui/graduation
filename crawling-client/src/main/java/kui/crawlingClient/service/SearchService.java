package kui.crawlingClient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@FeignClient("service-search")
public interface SearchService {

	@RequestMapping("index/createIndex")
	public boolean createIndex();
}
