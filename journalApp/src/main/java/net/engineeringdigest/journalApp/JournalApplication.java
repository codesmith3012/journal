package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
    // this is the key generator method which is used to generate the key for symmetric encryption
    KeyGenerator keyGenerator;
    {
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom=new SecureRandom();
            keyGenerator.init(128,secureRandom);
            SecretKey secretKey=keyGenerator.generateKey();
            String base64Key= Base64.getEncoder().encodeToString(secretKey.getEncoded());
            System.out.println(base64Key);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}

