package kui.eureka_recommend.service;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Service;

import com.alibaba.druid.pool.DruidDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import kui.eureka_recommend.dao.InterestDao;

@Service
public class RecommendService {
	
	
	
	@Autowired
	private InterestDao interestDao;
	
	
	public List<Integer> recommendNewsByUserId(int userId) {
		List<Integer> n_noList = new ArrayList<>();
		try {
			
			MysqlDataSource dataSource = new MysqlDataSource(); 
	        dataSource.setServerName("39.105.76.3"); 
	        dataSource.setUser("root"); 
	        dataSource.setPassword("uAiqwVwjJ8-i"); 
	        dataSource.setDatabaseName("graduation");                                                                    
	        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, 
	        		"interest", "id", "n_no", "val", "timestamp"); 
	        UserSimilarity similarity=new PearsonCorrelationSimilarity(dataModel); 
	        UserNeighborhood neighborhood=new NearestNUserNeighborhood(2,similarity,dataModel); 
	        Recommender recommender=new GenericUserBasedRecommender(dataModel,neighborhood,similarity); 
	        List<RecommendedItem> recommendations = recommender.recommend(userId, 10); 
	        for (RecommendedItem recommendation : recommendations) { 
	            System.out.println(recommendation); 
	            n_noList.add((int)recommendation.getItemID());
	        } 	
	        
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return n_noList;
	}
	
	

}
