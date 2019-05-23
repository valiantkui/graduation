package tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.example.GroupLensDataModel;


public class VoteRecommend {
	
	public static Map<Long,List<Long>> voteItem() throws Exception {
		
		//<userId,itemIdList>
		//<用户id,与用户id对应的itemId集合>
		Map<Long,List<Long>> map = new HashMap<>();
		
		//准备数据 这里是电影评分数据
        File file = new File("ratings.dat");
        //将数据加载到内存中，GroupLensDataModel是针对开放电影评论数据的
        DataModel dataModel = new GroupLensDataModel(file);
        //计算相似度，相似度算法有很多种，欧几里得、皮尔逊等等。
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        //计算最近邻域，邻居有两种算法，基于固定数量的邻居和基于相似度的邻居，这里使用基于固定数量的邻居
        UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
        LongPrimitiveIterator allUserId = dataModel.getUserIDs();
  
       while(allUserId.hasNext()) {
        	
        	Map<Long,Integer> itemMap = new HashMap<>();
        	Long userId = allUserId.next();
        	        	
        	System.out.print("当前用户id:"+userId);
        	//获取与用户userId相似的用户
        	long[] similarUserIdArray = userNeighborhood.getUserNeighborhood(userId);
        	
        	for(long similarUserId: similarUserIdArray) {
        		//获取用户similarUserId喜欢的itemId
        		FastIDSet itemIDsFromUser = dataModel.getItemIDsFromUser(similarUserId);
            	
            	long[] itemIdArray = itemIDsFromUser.toArray();
            	countItems(itemIdArray, itemMap);
        	}
        	
	        LongPrimitiveIterator iterator = dataModel.getItemIDsFromUser(userId).iterator();
	        
	        Set<Long> itemSet = new HashSet<>();
	        while(iterator.hasNext()) {
	        	itemSet.add(iterator.next());
	        }
        	     
	        Long[] keyArr = sortValue(itemMap);
	        List<Long> recommendList = new ArrayList<>();
	        int len = recommendList.size();
	        //求出投票数最高的10条推荐items
	        int n = 0,index=0;
	        while(n<10 && index < keyArr.length) {
	        	if(!itemSet.contains(keyArr[index])) {
	        		index++;
	        		recommendList.add(keyArr[index]);
	        		n++;
	        	}else {
	        		index++;
	        	}
	        }
	        System.out.println("为用户1推荐的前十条新闻id:");
	        for(Long l: recommendList) {
	        	System.out.println(l);
	        }
	         Recommender recommender = new GenericUserBasedRecommender(dataModel, userNeighborhood, similarity);
	         //给用户ID等于5的用户推荐10部电影
	         List<RecommendedItem> recommendedItemList = recommender.recommend(userId, 10);
	         //打印推荐的结果
	        for(RecommendedItem item: recommendedItemList) {
	        	if(! recommendList.contains(item.getItemID())) recommendList.add(item.getItemID());
	        } 
        	map.put(userId, recommendList);
       }        
      return map;
	}
	
	public static void countItems(long[] itemIdArray,Map<Long,Integer> itemMap) {
		for(long itemId :itemIdArray)
		{
			if(itemMap.containsKey(itemId)) {
				itemMap.put(itemId, itemMap.get(itemId)+1);
			}else {
				itemMap.put(itemId, 1);
			}
		}
			
		
	}
	
	/**
	 * 根据value的值对hashset排序
	 */
	
	public  static Long[] sortValue(Map<Long,Integer> map) {
		
		
		//注意类型转换只能单个元素进行，不能整个数组进行转换；比如：(Long[]) set.toArray();这是错误的
		Set<Entry<Long, Integer>> entrySet = map.entrySet();
		Set<Long> keySet = map.keySet();
		Collection<Integer> values = map.values();
		Long[] keyArr = new Long[entrySet.size()];
		keySet.toArray(keyArr);
		Integer[] valueArr = new Integer[entrySet.size()];
		values.toArray(valueArr);
	
		for(int i = 0;i<valueArr.length;i++) {
			for(int j = i+1;j<valueArr.length;j++) {
				if(valueArr[j] > valueArr[i]) {
					swapInteger(valueArr,i,j);
					swapLong(keyArr,i,j);
				}
			}
		}
		
		/*printArray(keyArr);
		printArray(valueArr);*/
		
		return keyArr;
	}
	
	
	public static void printArray(Object[] arr) {
		System.out.println();
		for(Object o:arr) {
			System.out.print(o+" ");
		}
		System.out.println();
	}
	
	public static void swapLong(Long[] arr,int i ,int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}
	
	public static void swapInteger(Integer[] arr,int i ,int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}
}
