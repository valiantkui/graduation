package kui.eureka_recommend.task;

import java.util.List;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import kui.eureka_recommend.entity.Interest;
import kui.eureka_recommend.service.InterestService;
import redis.clients.jedis.Jedis;

@Component
public class SchedulingTask {
	
	@Autowired
	private InterestService interestService;
	
	
	@Autowired
	private Jedis jedis;
	/**
	 * 每一小时调度一次；
	 * 主要任务：
	 * 1. 后台为每个用户计算其协同过滤推荐的新闻
	 * 2. 将计算结果保存到redis中
	 * 3. 在保存之前，需要将redis清空
	 */
	@Scheduled(fixedRate=21600000)
	public void task1() {
		System.out.println("task1----------");
		
		//List<Integer> userIdList = interestService.findAllUserId();
		
		jedis.select(1);
		try {
			 MysqlDataSource dataSource = new MysqlDataSource(); 
	        dataSource.setServerName("39.105.76.3"); 
	        dataSource.setUser("root"); 
	        dataSource.setPassword("uAiqwVwjJ8-i"); 
	        dataSource.setDatabaseName("graduation");                                                                   
	        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, "interest", "id", "n_no", "val", "timestamp"); 
	        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel); 
	        UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,similarity,dataModel); 
	        Recommender recommender = new GenericUserBasedRecommender(dataModel,neighborhood,similarity); 
	        
	        //获取interest表中的所有用户id
	       LongPrimitiveIterator userIDs = recommender.getDataModel().getUserIDs();
	       
	       //迭代器
	       while(userIDs.hasNext()) {
	    	   Long id = userIDs.next();
	    	   List<RecommendedItem> recommendations = recommender.recommend(id, 20); 
				String value = "";
				int len = recommendations.size();
				if(len == 0) break;//不写入到redis中
				
				for(int j = 0;j<len-1;j++) {
					value+= recommendations.get(j).getItemID()+"#";
				}
				value+=recommendations.get(len-1);
				
				
				jedis.set(id.toString(), value);
	    	   System.out.println("userIds:"+id);
	       }
	    	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
