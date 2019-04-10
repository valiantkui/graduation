package kui.eureka_news.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kui.eureka_news.entity.News;

@Repository("newsDao")
public interface NewsDao {

	/**
	 * 根据类型查找一定指定位置指定数量的新闻
	 * @param type 新闻的类型	
	 * @param offset 偏移量
	 * @param numPerPage 获取新闻的数量
	 * @return 返回新闻的集合
	 */
	public List<News> findNewsByType(@Param("type") String type,@Param("offset") int offset,@Param("numPerPage") int numPerPage);
	
	/**
	 * 向数据库中插入新闻
	 * 一次性插入多条新闻对象
	 * @param newsList新闻的集合
	 * @return 插入新闻的数量
	 */
	public int insertNewsList(@Param("newsList") List<News> newsList);

	public int findNewsNumByType(String type);

	public List<News> findAllNews();

	public News findNewsByN_no(int n_no);

	public int getNewsCount();

	
	/**
	 * 将新闻按照日期排序，删除日期最久的若干条指定类型的新闻记录
	 * @param type 新闻类型
	 * @param limit 删除的条数
	 * @return 删除的记录条数
	 */
	public int deleteNewsByTypeAndNum(@Param("type") String type,@Param("limit") int limit);

	
}
