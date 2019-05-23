package kui.eureka_recommend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import kui.eureka_recommend.dao.InterestDao;
import kui.eureka_recommend.entity.Interest;
import tools.VoteRecommend;

@Service
public class RecommendService {
	
	
	
	@Autowired
	private InterestDao interestDao;
	
	@Autowired
	private MysqlDataSource dataSource;
	
	
	/**
	 * 对所有的用户进行投票，获取所有用户推荐的新闻，然后保存到redis中。
	 */
	public Map<Long,List<Long>> caculateAllRecommends() {
		  try {
			  
		  List<Interest> interestList = interestDao.findAll();
			
			File file = new File("ratings.dat");
			if(!file.exists()) file.createNewFile();
			FileWriter fw = new FileWriter(file);
			
			for(Interest in : interestList) {
				String line = in.getId()+"::"+in.getN_no()+"::"+in.getVal()+"::"+in.getTimestamp();
				fw.write(line+"\r\n");
			}
			
			fw.flush();
			return VoteRecommend.voteItem();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		  
		  
	}
	
	public List<Integer> recommendNewsByUserId(int userId) {
		List<Integer> n_noList = new ArrayList<>();
		try {
			List<Interest> interestList = interestDao.findAll();
			
			File file = new File("ratings.dat");
			if(!file.exists()) file.createNewFile();
			FileWriter fw = new FileWriter(file);
			
			for(Interest in : interestList) {
				String line = in.getId()+"::"+in.getN_no()+"::"+in.getVal()+"::"+in.getTimestamp();
				fw.write(line+"\r\n");
			}
			
			fw.flush();
			
	      
/*			System.out.println("dataSource连接池打印："+dataSource);
			                                                        
	        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, 
	        		"interest", "id", "n_no", "val", "timestamp"); 
	        UserSimilarity similarity=new PearsonCorrelationSimilarity(dataModel); 
	        UserNeighborhood neighborhood=new NearestNUserNeighborhood(2,similarity,dataModel); 
	        Recommender recommender=new GenericUserBasedRecommender(dataModel,neighborhood,similarity); 
	        List<RecommendedItem> recommendations = recommender.recommend(userId, 10); */
			
			
		         //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
		         DataModel dataModel = new GroupLensDataModel(file);
		         //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
		         UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		         //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
		         UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
		         //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
		         Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
		         //给用户ID等于5的用户推荐10部电影
		         List<RecommendedItem> recommendedItemList = recommender.recommend(userId, 10);
		         //打印推荐的结果
		       
	        for (RecommendedItem recommendation : recommendedItemList) { 
	            System.out.println(recommendation); 
	            n_noList.add((int)recommendation.getItemID());
	        } 	
	           
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return n_noList;
	}
	

}
