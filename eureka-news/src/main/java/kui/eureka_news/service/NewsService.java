package kui.eureka_news.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kui.eureka_news.dao.NewsDao;
import kui.eureka_news.entity.News;

@Service
public class NewsService {

	
	@Resource(name="newsDao")
	private NewsDao newsDao;
	
	/**
	 * 根据新闻类型找到新闻
	 * @param type 类型
	 * @param currentPage 当前的页数
	 * @param numPerPage 一页的要展示的数量
	 * @return 新闻的集合
	 */
	public List<News> findNewsByType(String type,int currentPage,int numPerPage){
		
		int offset = (currentPage-1) * numPerPage; 
		return newsDao.findNewsByType(type, offset, numPerPage);
	}
	
	/**
	 * 存储一定量的新闻
	 * @param newsList
	 * @return 是否插入成功
	 */
	public boolean insertNewsList(List<News> newsList) {
		return newsDao.insertNewsList(newsList)>0?true:false;
	}

	public int getNewsNumByType(String type) {
		// TODO Auto-generated method stub
		return newsDao.findNewsNumByType(type);
		
	}

	public List<News> findAllNews() {
		// TODO Auto-generated method stub
		return newsDao.findAllNews();
	}
	
	public News findNewsByN_no(int n_no) {
		return newsDao.findNewsByN_no( n_no);
	}
	/**
	 * 获取新闻的总条数
	 * @return
	 */
	public int getNewsCount() {
		// TODO Auto-generated method stub
		return newsDao.getNewsCount();
	}

	public boolean deleteNewsByTypeAndNum(String type, int limit) {
		if(newsDao.deleteNewsByTypeAndNum(type,limit) > 0) return true;
		return false;
	}
	
}
