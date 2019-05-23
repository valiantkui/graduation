package kui.feign_client.controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.internal.util.privilegedactions.NewSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.feign_client.entity.Interest;
import kui.feign_client.entity.News;
import kui.feign_client.entity.User;
import kui.feign_client.service.NewsService;
import kui.feign_client.service.RecommendService;
import kui.feign_client.service.UserService;

@Controller
@RequestMapping("recommend")
public class RecommendController {

	
	@Autowired
	private NewsService newsService;

	
	@Autowired
	private UserService userService;
	@Autowired
	private RecommendService recommendService;
	
	 @RequestMapping("/insertInterest")
	 @ResponseBody
	 public boolean insertInterest(int id,int n_no,float val,long timestamp) {
		System.out.println("---------insertInterest()--------");
		 
		 return recommendService.insertInterest(id,n_no,val,timestamp);
	 }
	 
	 
	 
	 
	 
/*	@RequestMapping("/recommendNewsByUserId")
	@ResponseBody
	public List<News> recommendNewsByUserId(@RequestParam("userId") int userId){
		
	System.out.println("测试内容，，，，为什么不打印这句话？？？？？");
		System.out.println("----------推荐---------------userid:"+userId);
		List<Integer> n_noList = recommendService.recommendNewsByUserId(userId);
		System.out.println("集合n_noList:"+n_noList);
		List<News> newsList = new ArrayList<>();
		if(n_noList != null && n_noList.size() != 0) {
			newsList.addAll(newsService.findNewsByN_noList(n_noList));
		}
		System.out.println("获取前60热度的新闻");
		//获取60个最热度新闻
		newsList.addAll(newsService.findTopNNews(60));
		System.out.println("推荐的新闻："+newsList);
		return newsList;
	}
	*/
	
	/**
	 * 随机生成num条用户行为信息，仅供推荐系统的测试数据
	 * 思路：
	 * 首先，获取所有的用户自增id，存到数组里
	 * 获取所有的新闻id，存到数组里
	 * 随机生成Interest对象，生成的interest对象存到set集合中
	 * 注意：Interest类要写equeals方法。
	 * @param num
	 * @return
	 */
	@RequestMapping(value="/randomInterest",produces="application/json;charset=utf-8")
	@ResponseBody
	public boolean randomInterest(int num) {
		// TODO Auto-generated method stub
		System.out.println("获取的num的值："+num);
		List<User> userList = userService.findAllUser();
		int userLen = userList.size();
		int[] userIdArr = new int[userLen];
		int i = 0;
		for(User u: userList) userIdArr[i++]=u.getId();
		
		//获取所有的新闻id
		List<News> newsList = newsService.findAllNews();
		int newsLen = newsList.size();
		int[] n_noArr = new int[newsLen];
		i = 0;
		
		for(News n : newsList) n_noArr[i++] = n.getN_no();
		
		//创建一个set集合
		Set<Interest> interestSet = new HashSet<>(); 
		
		System.out.println("userLen:"+userLen+",,newsLen:"+newsLen);
		
		//获取到的userIdArr和n_noArr
		while(interestSet.size() < num) {
			int userIndex = (int) (Math.random() * userLen);
			int newsIndex = (int) (Math.random() * newsLen);
			
			float val = (float) (Math.random() * 50);
			Interest interest = new Interest();
			interest.setId(userIdArr[userIndex]);
			interest.setN_no(n_noArr[newsIndex]);
			interest.setVal(val);
			interest.setTimestamp(new Date().getTime());
			interestSet.add(interest);
			System.out.println("---生成的interest："+interest+",,此时set集合长度："+interestSet.size());
		}
		List<Interest> interestList = new ArrayList<>();
		interestList.addAll(interestSet);
		
		System.out.println("要添加的行为信息："+interestList);
		return recommendService.insertInterestList(interestList);
		
	}
	 
}
