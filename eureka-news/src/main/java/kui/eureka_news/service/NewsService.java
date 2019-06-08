package kui.eureka_news.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		
		
		newsList = deleteRepeat(newsList);
		List<News> subList = new ArrayList<>();
		int num = 0;
		int index = 0;
		
		for(News n: newsList) {
			if(num++ >= 100) {
				if(newsDao.insertNewsList(subList)<=0) return false;
				num=0;
				subList.clear();
			}
			subList.add(n);
		}
		return num!=0 && subList.size()>0 && newsDao.insertNewsList(subList)>0?true:false;
	}

	
	private List<News> deleteRepeat(List<News> newsList){
		List<News> newsList2 = new ArrayList<>();
		
		List<News> allNewsList = newsDao.findAllNews();
		
		Set<String> set = new HashSet<>();
		for(News n: allNewsList) {
			set.add(n.getTitle());
		}
		for(News n: newsList) {
			if(!set.contains(n.getTitle())) {
				newsList2.add(n);
			}
		}
		return newsList2;
	}
	
	
	public int getNewsNumByType(String type) {
		// TODO Auto-generated method stub
		return newsDao.findNewsNumByType(type);
		
	}

	public List<News> findAllNews() {
		// TODO Auto-generated method stub
		return newsDao.findAllNews();
	}
	public List<News> findAllNews2() {
		// TODO Auto-generated method stub
		return newsDao.findAllNews2();
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

	public List<News> findNewsByN_noList(List<Integer> n_noList) {
		
		return newsDao.findNewsByN_noList(n_noList);
	}

	public List<News> findTopNNews(int num) {
		// TODO Auto-generated method stub
		return newsDao.findTopNNews(num);
	}
	
}
