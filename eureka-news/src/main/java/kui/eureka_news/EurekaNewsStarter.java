package kui.eureka_news;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("kui.eureka_news.dao")
public class EurekaNewsStarter 
{
    public static void main(String[] args)
    {
       SpringApplication.run(EurekaNewsStarter.class, args);
    }
}
