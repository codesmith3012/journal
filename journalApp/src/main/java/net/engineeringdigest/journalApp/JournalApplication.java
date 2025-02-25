package net.engineeringdigest.journalApp;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "net.engineeringdigest.journalApp.repository")
public class JournalApplication {
    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    @Bean// created a instance of this rest template
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public RedisTemplate redisTemplate() {
        return new RedisTemplate();
    }
}

