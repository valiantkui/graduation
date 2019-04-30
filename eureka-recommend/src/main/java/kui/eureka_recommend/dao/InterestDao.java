package kui.eureka_recommend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kui.eureka_recommend.entity.Interest;


@Repository("interestDao")
public interface InterestDao {

	public int insertInterest(Interest interest);
	
	public List<Interest> findAll();

	public List<Integer> findAllUserId();
	
	
	
	public Interest findInterestByIdN_no(@Param("id") int id,@Param("n_no") int n_no);
	
	/**
	 * 更新指定interest的val值(源数据库val值与参数interst.val相加)
	 * @param interest
	 */
	public int updateIterestVal(Interest interest);
	
	
}
