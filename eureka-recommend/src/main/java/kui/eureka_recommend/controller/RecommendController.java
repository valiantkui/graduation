package kui.eureka_recommend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kui.eureka_recommend.service.RecommendService;
import redis.clients.jedis.Jedis;


@Controller
@RequestMapping("recommend")
public class RecommendController {

	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private Jedis jedis;
	
	/**
	 * 根据用户的id来为用户推荐若干条新闻
	 * @param userId
	 * @return 新闻编号的集合List<n_no>
	 */
	@RequestMapping(value="/recommendNewsByUserId",produces="application/json")
	@ResponseBody
	public List<Integer> recommendNewsByUserId(@RequestParam("userId") int userId){
		
		jedis.select(1);
		String key = String.valueOf(userId);
		System.out.println("是否存在key值："+jedis.exists(key));
		if(jedis.exists(key)) {
			//表示redis中存在要推荐的内容(userId对应的用户)
			String valueStr = jedis.get(key);
			if(valueStr==null || "".equals(valueStr)) return null;
			String[] n_noArr = valueStr.split("#");
			List<Integer> n_noList = new ArrayList<>();
			for(int i =0;i<n_noArr.length;i++) {
				n_noList.add(Integer.parseInt(n_noArr[i]));
			}
			return n_noList;
		}else {
			List<Integer> n_noList = recommendService.recommendNewsByUserId(userId);	
			
			System.out.println(n_noList);
			if(n_noList==null || n_noList.size()==0) return null;//如果没有推荐的内容，就返回空
			System.out.println("推荐的新闻no："+n_noList);
			String value = "";
			int len = n_noList.size();
			for(int i = 0;i<len-1;i++) {
				value += n_noList.get(i)+"#";
			}
			if(len>0) value += n_noList.get(len-1);
			
			System.out.println("要写入的key和value为："+key+":"+value);
			jedis.set(key, value);
			return n_noList;
		}
	}
}
