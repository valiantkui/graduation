package kui.eureka_search.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author KUIKUI
 *此类是用来连接数据库的封装类
 */
public class DBTools {
	/**
	 * 获取数据库的连接
	 * @return 数据库的连接（Connection类型）
	 */
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//return DriverManager.getConnection("jdbc:mysql://39.106.124.138:3306/bookshop?characterEncoding=utf8","root","0107");
			return DriverManager.getConnection("jdbc:mysql://39.105.76.3:3306/graduation?characterEncoding=utf8","root","uAiqwVwjJ8-i");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(SQLException e)
		{
			e.printStackTrace();
		}catch(Exception e )
		{
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 关闭connection连接
	 * @param con
	 */
	public static void close(Connection con)
	{
		if(con!=null)
		{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭statement
	 */
	public static void close(Statement stat)
	{
		if(stat!=null)
		{
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭ResultSet连接
	 * @param rs
	 */
	public static void close(ResultSet rs)
	{
		if(rs!=null)
		{
			
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
