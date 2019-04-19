package kui.eureka_user.controller;

import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.client.http.HttpRequest;

import kui.eureka_user.entity.User;
import kui.eureka_user.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private StringRedisTemplate srt;
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping("/getCurrentUser")
	public User getCurrentUser(HttpServletRequest request) {
		System.out.println(request);

		HttpSession session = request.getSession();
		  Enumeration<String> headerNames = request.getHeaderNames();
		
		while(headerNames.hasMoreElements()) {
			String nextElement = headerNames.nextElement();
			System.out.println("name:"+nextElement+",,value:"+request.getHeader(nextElement));
		}
		
		
		String cookie = request.getHeader("cookie");
		if(cookie == null || "".equals(cookie)) return null;
		String[] arr = cookie.split(";");
		if(arr.length < 2) return null;
		String sessionIdKV = arr[1];
		
		if(sessionIdKV == null) return null;
		String sessionId = sessionIdKV.split("=")[1];
		String u_id = (String) srt.opsForValue().get(sessionId);
		//System.out.println("request.SessionId:"+);
		
		//System.out.println("sessionId:"+session.getId());
		System.out.println("u_id:"+u_id);
		if(u_id==null || "".equals(u_id)) return null;
		User user = userService.findUserByU_id(u_id);
		System.out.println("user:"+user);
		return user;
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
	public boolean loginUser(String u_id,String password,HttpServletRequest request) {
		boolean login = userService.loginUser(u_id, password);
		if(login) {
			/*session.setAttribute("u_id", u_id);
			session.setAttribute("password", password);*/
			
			//System.out.println(session.getId());
			//System.out.println("request.SessionId:"+);
			String cookie = request.getHeader("cookie");
			if(cookie == null || "".equals(cookie)) return false;
			String[] arr = cookie.split(";");
			if(arr.length < 2) return false;
			String sessionIdKV = arr[1];
			
			if(sessionIdKV == null) return false;
			String sessionId = sessionIdKV.split("=")[1];
			srt.opsForValue().set(sessionId, u_id);
		//	srt.opsForValue().set("password", password);
			return true;
		}
		return false;
	}
	@RequestMapping(value="/findUserByU_idList",produces="application/json")
	@ResponseBody
	public List<User> findUserByU_idList(@RequestBody List<String> u_idList){
		
		
		return userService.findUserByU_idList(u_idList);
	}
}
