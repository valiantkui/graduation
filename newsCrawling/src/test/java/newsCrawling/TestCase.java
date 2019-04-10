package newsCrawling;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

public class TestCase {

	@Test
	public void test1() throws ParseException {
		Date date = new Date(1551577272801l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String limitDateStr = "2019-03-03 00:00:00";
		Date limitDate = sdf.parse(limitDateStr);
		System.out.println(limitDate.getTime());
	}
}
