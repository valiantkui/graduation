package kui.feign_client.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-user")

@Service
public interface UserService {

	
	@RequestMapping("user/checkUserId/{u_id}")
	public boolean checkUserId(@PathVariable("u_id") String u_id);
	
	
	@RequestMapping(value="user/registerUser",method=RequestMethod.POST)
	public boolean registerUser(
			@RequestParam("u_id") String u_id,
			@RequestParam("name") String name,
			@RequestParam("password") String password,
			@RequestParam("interest_label") List<String> interestLabelList);
	
	
	@RequestMapping(value="user/loginUser",method=RequestMethod.POST)
	public boolean loginUser(
			@RequestParam("u_id") String u_id,
			@RequestParam("password") String password);
	
}
