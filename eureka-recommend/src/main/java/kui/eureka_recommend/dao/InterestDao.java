package kui.eureka_recommend.dao;

import org.springframework.stereotype.Repository;

import kui.eureka_recommend.entity.Interest;


@Repository("interestDao")
public interface InterestDao {

	public int insertInterest(Interest interest);
}
