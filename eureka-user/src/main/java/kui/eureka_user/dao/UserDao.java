package kui.eureka_user.dao;

import org.springframework.stereotype.Repository;

import kui.eureka_user.entity.User;

@Repository("userDao")
public interface UserDao {

	public User findUserById(String u_id);
	public int registerUser(User user);
}
