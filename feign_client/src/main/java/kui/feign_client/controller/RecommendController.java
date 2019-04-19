package kui.feign_client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.feign_client.service.InterestService;

@Controller
@RequestMapping("recommend")
public class RecommendController {

	 
	@Autowired
	private InterestService interestService;
	
	 @RequestMapping("/insertInterest")
	 @ResponseBody
	 public boolean insertInterest(String u_id,int n_no,float val,long timestamp) {
		System.out.println("---------insertInterest()--------");
		 
		 
		 return interestService.insertInterest(u_id,n_no,val,timestamp);
	 }
	 
}
