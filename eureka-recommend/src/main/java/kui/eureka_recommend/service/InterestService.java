package kui.eureka_recommend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kui.eureka_recommend.dao.InterestDao;
import kui.eureka_recommend.entity.Interest;

@Service("interestService")
public class InterestService {
	
	@Autowired
	private InterestDao interestDao;
	public boolean insertInterest(Interest interest) {
		if(interestDao.insertInterest(interest)>0) return true;
		return false;
	}
	
}
