package kui.feign_client.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import kui.feign_client.entity.News;
import kui.feign_client.entity.User;
import kui.feign_client.service.NewsService;
import kui.feign_client.service.RecommendService;
import kui.feign_client.service.SearchService;
import kui.feign_client.service.UserService;
import kui.feign_client.util.UUIDGenerator;

@Controller
@RequestMapping("news")
public class NewsController {

	@Autowired
	private NewsService newsService;

	@Autowired
	private SearchService searchService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping("/findNewsByType")
	public void findNewsByType(@RequestParam("type") String type,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage,HttpServletRequest request,HttpServletResponse response){
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if("推荐".equals(type)) {
			cookieCheck(request, response);
		
			User currentUser = userService.getCurrentUser();
			List<Integer> n_noList = recommendService.recommendNewsByUserId(currentUser.getId());
			 System.out.println("----------推荐---------------userId:"+currentUser.getId());
			System.out.println("集合n_noList:"+n_noList);
			Set<News> newsSet = new HashSet<>();
			
			if(n_noList != null && n_noList.size() != 0) {
				newsSet.addAll(newsService.findNewsByN_noList(n_noList));
			}
			System.out.println("获取前60热度的新闻");
			//获取60个最热度新闻
			newsSet.addAll(newsService.findTopNNews(60));
			System.out.println("推荐的新闻："+newsSet);
			
			out.print(new Gson().toJson(newsSet));
			return;
		}
		out.print(new Gson().toJson(newsService.findNewsByType(type, currentPage, numPerPage)));
		out.close();
		
	}
	
	@RequestMapping("/findAllNews")
	@ResponseBody
	public List<News> findAllNews(){
		return newsService.findAllNews();
	}
	@RequestMapping("/getNewsNumByType")
	@ResponseBody
	public int getNewsNumByType(@RequestParam("type") String type) {
		
		
		return newsService.getNewsNumByType(type);
	}
	
	@RequestMapping("/loadNewsContent")
	
	public void loadNewsContent(@RequestParam("n_no") int n_no,HttpServletResponse response,HttpServletRequest request) {
		cookieCheck(request, response);
		
		try {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().print(newsService.findNewsByN_no(n_no).getNews_text());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/searchNews")
	@ResponseBody
	public List<News> searchNews(@RequestParam("searchContent") String searchContent,@RequestParam("currentPage") int currentPage,@RequestParam("numPerPage") int numPerPage){
		return searchService.searchNews(searchContent,currentPage,numPerPage);
	}
	
	@RequestMapping("/getSearchNewsNum")
	@ResponseBody
	public int getSearchNewsNum(@RequestParam("searchContent") String searchContent) {
		return searchService.getSearchNewsNum(searchContent);
	}
	
	private void cookieCheck(HttpServletRequest request,HttpServletResponse response) {
					//1.判断当前用户是否登陆了
					//2. 如果未登陆，则判断浏览器是否存有uuid_label的cookie信息
					Cookie[] cookies = request.getCookies();
					String cookieValue = request.getHeader("cookie");
					
					System.out.println("--cookieValue:"+cookieValue);
					
					boolean isExits = false;
					String uuid_value = null;
					for(Cookie c: cookies) {
						if("uuid_label".equals(c.getName())) {
							//3. 如果存在，则直接加载新闻即可
							uuid_value = c.getValue();
							isExits = true;
							break;
						}
					}
					if(isExits) {
						//如果存在且未登录
						System.out.println("不存在cookie.....");
						if(userService.getCurrentUser() == null)
							//未登录，重新登陆一次，将状态信息保存到redis中
							userService.loginUser(uuid_value, "123");
					}else {
						//如果不存在则生成
						System.out.println("存在cookie.....");
						String u_id = UUIDGenerator.getUUID();
						
						Cookie uuid_label = new Cookie("uuid_label",u_id);
						Cookie test = new Cookie("test","yuankui");
						test.setMaxAge(100*60*60);
						uuid_label.setMaxAge(60*60*24*30*12);//周期一百年
						uuid_label.setPath("/");
						List<String> list = new ArrayList<>();
						list.add("时政");
						userService.registerUser(u_id, "cookie", "123", list);
						userService.loginUser(u_id, "123");
						
						response.addCookie(uuid_label);		
						response.addCookie(test);		
					}
	}
}
