package kui.eureka_user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import kui.eureka_user.dao.UserDao;
import kui.eureka_user.entity.User;

@Service("userService")
public class UserService {

	@Resource(name="userDao")
	private UserDao userDao;
	
	/**
	 * 根据用户id来找到某个用户
	 * @param u_id
	 * @return
	 */
	public User findUserByU_id(String u_id) {

		User user = userDao.findUserById(u_id);
		System.out.println(user);
		return user;
		
	}
	
	/**
	 * 
	 * @param user  用户的对象
	 * @return 是否注册成功
	 */
	public boolean registerUser(String u_id,String name,String password, List<String> interestLabelList) {
		String interest_label = "";
		int size = interestLabelList.size();
		if(size!=0) {
			for(int i = 0;i<size-1;i++) {
				interest_label+=interestLabelList.get(i)+"#";
			}
			interest_label+=interestLabelList.get(size-1);
		}
		System.out.println(interest_label);
		User user = new User();
		user.setU_id(u_id);
		user.setName(name);
		user.setPassword(password);
		user.setInterest_label(interest_label);
		
		
		int row = userDao.registerUser(user);
		
		System.out.println(row);
		if(row <= 0) return false;
		
		
		return true;
		
	}
	
	public boolean loginUser(String u_id,String password) {
		User user = userDao.findUserById(u_id);
		if(user==null) return false;
		if(password.equals(user.getPassword())) return true;
		
		return false;
	}

	

	public List<User> findUserByU_idList(List<String> u_idList) {
		return userDao.findUserByU_idList(u_idList);
	}

	public User findUserByUserId(int userId) {
		// TODO Auto-generated method stub
		return userDao.findUserByUserId(userId);
	}
	
	
	
	
}
