package kui.eureka_comment;

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
@MapperScan("kui.eureka_comment.dao")
public class EurekaCommentStarter 
{
    public static void main( String[] args )
    {
        SpringApplication.run(EurekaCommentStarter.class, args);
    }
}
