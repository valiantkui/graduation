package kui.eureka_recommend;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class JedisTest {

	@Test
	public void test01() {
		Jedis jedis = new Jedis("127.0.0.1",6379);
		jedis.select(1);
		jedis.set("hello", "我爱你");
	}
}
