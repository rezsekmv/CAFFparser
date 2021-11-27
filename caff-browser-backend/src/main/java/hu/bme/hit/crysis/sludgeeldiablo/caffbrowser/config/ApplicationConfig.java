package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ObjectMapperFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfig implements WebMvcConfigurer {

    @Value("${security.cors}")
    private Boolean cors;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (cors) {
            registry.addMapping("/**").allowedMethods("*");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return ObjectMapperFactory.createObjectMapper();
    }
}
