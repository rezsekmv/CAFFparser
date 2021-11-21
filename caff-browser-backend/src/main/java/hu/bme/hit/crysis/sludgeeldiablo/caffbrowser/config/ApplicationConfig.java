package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ObjectMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return ObjectMapperFactory.createObjectMapper();
    }
}
