package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${security.cors.enabled}")
    private Boolean corsEnabled;

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        if (corsEnabled) {
            registry.addMapping("/**").allowedMethods("*");
        }
    }

}
