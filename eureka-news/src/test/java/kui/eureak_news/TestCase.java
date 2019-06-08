package kui.eureak_news;

import java.util.HashSet;
import java.util.Set;

public class TestCase {

	
	public static void main(String[] args) {
		System.out.println("Fef");

		Set<String> set = new HashSet<>();
		
		set.add("111");
		set.add("111");
		set.add("122");
		set.add("123");
		set.add("aa");
		System.out.println(set);
		
		
		for(String s: set) {
			if("122".equals(s)) set.remove(s);
		}
		System.out.println(set);
		
	}
}
