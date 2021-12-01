package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST", "PUT", "DELETE");
    private static final String PATH_PATTERN = "/**";

    @Value("${security.cors.enabled}")
    private Boolean corsEnabled;

    @Value("${security.cors.frontend-url}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(@NotNull CorsRegistry registry) {
        if (corsEnabled) {
            registry.addMapping("/**").allowedMethods("*");
        }
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(frontendUrl));
        configuration.setAllowedMethods(ALLOWED_METHODS);
        source.registerCorsConfiguration(PATH_PATTERN, configuration);
        return source;
    }
}
