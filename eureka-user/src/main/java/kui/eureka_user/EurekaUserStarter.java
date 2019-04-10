package kui.eureka_user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

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
}
