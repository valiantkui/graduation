package kui.eureka_user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kui.eureka_user.service.UserService;


@RestController
@RequestMapping("user")
public class UserController {

	@Resource(name="userService")
	private UserService userService;
	
	
	
	@RequestMapping("/checkSession")
	public boolean checkSession(HttpSession session) {
		String u_id = (String) session.getAttribute("u_id");
		if(u_id==null || "".equals(u_id)) return false;
		return true;
	}
	
	
	
	@RequestMapping("/checkUserId/{u_id}")
	public boolean checkUserId(@PathVariable String u_id) {
		if(userService.findUserByU_id(u_id) == null) return true;//该账号可以使用
		return false;
	}
	
	@RequestMapping("/registerUser")
	@ResponseBody
	public boolean registerUser(String u_id,String name,String password,@RequestParam("interest_label") List<String> interestLabelList) {
		return userService.registerUser(u_id, name, password, interestLabelList);
	}
	
	@RequestMapping("/loginUser")
	@ResponseBody
	public boolean loginUser(String u_id,String password,HttpSession session) {
		boolean login = userService.loginUser(u_id, password);
		if(login) {
			session.setAttribute("u_id", u_id);
			session.setAttribute("password", password);
			return true;
		}
		return false;
	}
}
