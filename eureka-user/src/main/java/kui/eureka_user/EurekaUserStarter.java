package kui.eureka_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("kui.eureka_user.dao")
public class EurekaUserStarter 
{
    public static void main( String[] args )
    {
       SpringApplication.run(EurekaUserStarter.class, args);
    }
    
    @Value("${redis.host}")
    private String host;
    
    @Value("${redis.port}")
    private int port;
    
    @Bean
    public Jedis getJedis() {
    	return new Jedis(host,port);
    }
}
