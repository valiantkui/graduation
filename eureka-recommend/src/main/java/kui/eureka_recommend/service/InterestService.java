package kui.eureka_recommend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kui.eureka_recommend.dao.InterestDao;
import kui.eureka_recommend.entity.Interest;

@Service
public class InterestService {
	
	@Autowired
	private InterestDao interestDao;

	/**
	 * 插入一条兴趣到数据库
	 * @param interest
	 * @return
	 */
	
	
	public boolean insertInterest(Interest interest) {
		//判断该兴趣是否存在，如果存在则更新那条记录
		if(interestDao.findInterestByIdN_no(interest.getId(),interest.getN_no()) != null) {
			//更新数据库
			return interestDao.updateIterestVal(interest)>0;
		}else {
			//如果不存在，就向其添加一条记录
			return interestDao.insertInterest(interest)>0;
		}
	}


	public List<Integer> findAllUserId() {
		return interestDao.findAllUserId();
	}


	
	public boolean insertInterestList(List<Interest> interestList) {
		// TODO Auto-generated method stub
		return interestDao.insertInterestList(interestList) > 0;
	}
}
