package kui.eureka_recommend;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;
import org.junit.Test;

import com.ibm.icu.text.SimpleDateFormat;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class TestCase1 {

	public static void main(String[] args) {

		
		System.out.println("main()");
		try {
			test06();
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void test01() {
		  FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();
	        PreferenceArray User1Pref = new GenericUserPreferenceArray(2);
	        User1Pref.setUserID(0, 1L);
	        User1Pref.setItemID(0, 101L);
	        User1Pref.setValue(0, 3.0f);
	        User1Pref.setItemID(1, 102L);
	        User1Pref.setValue(1, 4.0f);

	        PreferenceArray User2Pref = new GenericUserPreferenceArray(2);
	        User2Pref.setUserID(0, 2L);
	        User2Pref.setItemID(0, 101L);
	        User2Pref.setValue(0, 3.0f);
	        User2Pref.setItemID(1, 102L);
	        User2Pref.setValue(1, 4.0f);

	        preferences.put(1L, User1Pref);
	        preferences.put(2L, User2Pref);

	        DataModel model = new GenericDataModel(preferences);
	        System.out.println(model);
	}
	
	/**
	 * 基于用户的推荐
	 * @throws IOException
	 * @throws TasteException
	 */
	public static void test02() throws IOException, TasteException {
		  //准备数据 这里是电影评分数据
		         File file = new File("D:\\data\\ml-10M100K\\ratings.dat");
		         //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
		         DataModel dataModel = new GroupLensDataModel(file);
		         //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
		         UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
		         //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
		         UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
		         //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于用户的协同过滤推荐
		         Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
		         //给用户ID等于5的用户推荐10部电影
		         List<RecommendedItem> recommendedItemList = recommender.recommend(5, 10);
		         //打印推荐的结果
		         System.out.println("使用基于用户的协同过滤算法");
		         System.out.println("为用户5推荐10个商品");
		         for (RecommendedItem recommendedItem : recommendedItemList) {
		             System.out.println(recommendedItem);
		         }
	}
	
	/**
	 * 基于物品的推荐
	 * @throws Exception
	 */
	public static void test03() throws Exception {
		 File file = new File("D:\\data\\ml-10M100K\\ratings.dat");
	        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
	        DataModel dataModel = new GroupLensDataModel(file);
	        //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
	        ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);
	        //构建推荐器，协同过滤推荐有两种，分别是基于用户的和基于物品的，这里使用基于物品的协同过滤推荐
	        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, itemSimilarity);
	        //给用户ID等于5的用户推荐10个与2398相似的商品
	        List<RecommendedItem> recommendedItemList = recommender.recommendedBecause(5, 2398, 10);
	        //打印推荐的结果
	        System.out.println("使用基于物品的协同过滤算法");
	        System.out.println("根据用户5当前浏览的商品2398，推荐10个相似的商品");
	        for (RecommendedItem recommendedItem : recommendedItemList) {
	            System.out.println(recommendedItem);
	        }
	        long start = System.currentTimeMillis();
	        recommendedItemList = recommender.recommendedBecause(5, 34, 10);
	        //打印推荐的结果
	        System.out.println("使用基于物品的协同过滤算法");
	        System.out.println("根据用户5当前浏览的商品34，推荐10个相似的商品");
	        for (RecommendedItem recommendedItem : recommendedItemList) {
	            System.out.println(recommendedItem);
	        }
	        System.out.println(System.currentTimeMillis() -start);
	}
	
	/**
	 * 评估推荐模型
	 * @throws Exception
	 */
	public static void test04() throws Exception {
		  //准备数据 这里是电影评分数据
        File file = new File("D:\\data\\ml-10M100K\\ratings.dat");
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        //推荐评估，使用均方根
        //RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        //推荐评估，使用平均差值
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = new RecommenderBuilder() {

            @Override
			public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        // 用70%的数据用作训练，剩下的30%用来测试
        double score = evaluator.evaluate(builder, null, dataModel, 0.7, 1.0);
        //最后得出的评估值越小，说明推荐结果越好
        System.out.println(score);
	}
	
	
	public static void test05() throws Exception{

		 //准备数据 这里是电影评分数据
        File file = new File("D:\\data\\ml-10M100K\\ratings.dat");
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
			public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(4, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        // 计算推荐4个结果时的查准率和召回率
        //使用评估器，并设定评估期的参数
        //4表示"precision and recall at 4"即相当于推荐top4，然后在top-4的推荐上计算准确率和召回率
        IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, dataModel, null, 4, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        System.out.println(stats.getPrecision());
        System.out.println(stats.getRecall());
	}
	
	/**
	 * 使用jdbcDataModel来连接数据库  做推荐。
	 * @throws Exception
	 */
	@Test
	public static void timeTest() {
		Date date = new Date();
		date.setTime(1555411778898l);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		
		System.out.println(sdf.format(date));
	}
	public static void test06() throws Exception{
        MysqlDataSource dataSource = new MysqlDataSource(); 
        dataSource.setServerName("localhost"); 
        dataSource.setUser("root"); 
        dataSource.setPassword("yuankui1209"); 
        dataSource.setDatabaseName("graduation"); 
                                                                         
        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, "interest", "u_id", "n_no", "val", "timestamp"); 
                
        System.out.println("开始推荐....");
       // DataModel model = dataModel; 
        UserSimilarity similarity=new PearsonCorrelationSimilarity(dataModel); 
        UserNeighborhood neighborhood=new NearestNUserNeighborhood(2,similarity,dataModel); 
                  
        
        System.out.println("即将推荐...");
        Recommender recommender=new GenericUserBasedRecommender(dataModel,neighborhood,similarity); 
        System.out.println("正在推荐...");
        List<RecommendedItem> recommendations = recommender.recommend(2, 10); 
        
        System.out.println("推荐的内容");
        for (RecommendedItem recommendation : recommendations) { 
            System.out.println(recommendation); 
        } 	
        System.out.println("推荐完成");
	}
	
	
}
