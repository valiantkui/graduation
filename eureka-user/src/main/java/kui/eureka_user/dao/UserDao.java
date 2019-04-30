package kui.eureka_user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kui.eureka_user.entity.User;

@Repository("userDao")
public interface UserDao {

	public User findUserById(String u_id);
	public int registerUser(User user);
	
	public List<User> findUserByU_idList(@Param("u_idList") List<String> u_idList);
	public User findUserByUserId(int userId);
}
