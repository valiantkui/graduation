package crawling;

import java.io.IOException;
import java.util.Date;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class Test1 {

	
	@Test
	public void test01() throws IOException {
		String url = "https://www.sina.com.cn/";
		
		Connection connect = Jsoup.connect(url);
		Document document = connect.get();
		Elements elements_ul = document.select(".main-nav .nav-mod-1").get(0).select("ul");
		for(Element ele: elements_ul) {
			Elements elements_li = ele.select("li");
			for(int i = 0 ;i<elements_li.size();i++) {
				if(i==elements_li.size()-1) {
					System.out.println(elements_li.get(i).text());
				}else {
					System.out.print(elements_li.get(i).text()+"  ");
				}
			}
		}
		
		
	}
	
	@Test
	public void test2() throws IOException {
		Date date = new Date(1551427874000l);
		System.out.println(date);
	}
	
}
