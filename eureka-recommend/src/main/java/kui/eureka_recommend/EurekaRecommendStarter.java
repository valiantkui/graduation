package kui.eureka_recommend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@MapperScan("kui.eureka_recommend.dao")
public class EurekaRecommendStarter 
{
    public static void main(String[] args)
    {
        SpringApplication.run(EurekaRecommendStarter.class, args);
        System.out.println("EurekaRecommendStarter");  
    }
    
    @Value("${redis.host}")
    private String host;
    
    @Value("${redis.port}")
    private int port;
    
    @Bean
    public Jedis getJedis() {
    	return new Jedis(host,port);
    }
    
	@Bean
	public MysqlDataSource getMysqlDataSource() {
		MysqlDataSource dataSource = new MysqlDataSource(); 
        dataSource.setServerName("39.105.76.3"); 
        dataSource.setUser("root"); 
        dataSource.setPassword("uAiqwVwjJ8-i"); 
        dataSource.setDatabaseName("graduation");   
        return dataSource;
	}
}