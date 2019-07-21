package bnu.edu.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Hello world!
 *
 */
@SpringBootApplication
@Cacheable
@EnableSwagger2
public class App 
{
	public static void main(String[] args) {
		// SpringApplication.run(App.class, args);
		SpringApplication app = new SpringApplication(App.class);
		//app.setBannerMode(Banner.Mode.);
		app.run(args);
	}
	
	
}
