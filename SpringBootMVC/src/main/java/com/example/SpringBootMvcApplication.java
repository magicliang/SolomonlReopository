package com.example;

import com.example.Entity.User;
import com.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

//@RestController
//@EnableAutoConfiguration

@Transactional
@SpringBootApplication
@EnableCaching
@EnableScheduling
//@EnableAspectJAutoProxy//With @SpringBootApplicationm, we do not need this, only need @Aspect and @Component
//With this, we can specify properties not in resources folder
//@PropertySource(value = { "classpath:application.properties" })//Also don't need this
public class SpringBootMvcApplication {


    @Autowired
    private Environment env;

    private static final Logger log = LoggerFactory.getLogger(SpringBootMvcApplication.class);

    //@RequestMapping("/")
    //String home(){
    //return "Hello, World";
    //}

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMvcApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            userRepository.deleteInBatch(userRepository.findAll());
            //userRepository.findByName()
            //password: Encrypted 123
            userRepository.save(new User("Chuan", "$2a$10$W7fimbsfYVHG.nS5ZhqtfeyIftVRfNGeVpsj2RvW.1B8JgKHeClDO"));
            userRepository.save(new User("Liang", "$2a$10$W7fimbsfYVHG.nS5ZhqtfeyIftVRfNGeVpsj2RvW.1B8JgKHeClDO"));
            userRepository.save(new User("Test", "$2a$10$W7fimbsfYVHG.nS5ZhqtfeyIftVRfNGeVpsj2RvW.1B8JgKHeClDO"));
            // fetch all customers
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            userRepository.findAll().stream().parallel().forEach((user)->{
                log.info(user.toString());
            });
        };
    }
//In the spring example, we don't need this
// Spring Boot automatically configures a suitable CacheManager to serve as a provider for the relevant cache. See the Spring Boot documentation for more details.
//	@Bean
//	public CacheManager cacheManager()
//	{
//		return new ConcurrentMapCacheManager("users");
//	}

}
