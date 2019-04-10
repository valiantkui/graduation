package kui.feign_client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.feign_client.service.UserService;

@CrossOrigin
@Controller
@RequestMapping("/user")
public class UserController {

	
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/checkUserId/{u_id}")
	@ResponseBody
	public boolean checkUserId(@PathVariable("u_id") String u_id) {
	
		return userService.checkUserId(u_id);
	}
	
	
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public boolean registerUser(String u_id,String name,String password,@RequestParam(value="interest_label") List<String> interestLabelList) {
		System.out.println("兴趣标签："+interestLabelList+",长度："+interestLabelList.size());
		
		return userService.registerUser(u_id, name, password, interestLabelList);
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public boolean loginUser(@RequestParam("u_id") String u_id,@RequestParam("password") String password) {
		
		System.out.println("loginUser()"+",u_id:"+u_id+",password:"+password);
		if(userService.loginUser(u_id, password)) return true;
		return false;
	}
}
