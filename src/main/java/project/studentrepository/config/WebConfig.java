package project.studentrepository.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class WebConfig {
    
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow production and Vercel preview URLs using patterns
        // Patterns allow wildcards which is needed for Vercel preview deployments
        config.setAllowedOriginPatterns(Arrays.asList(
            "https://repository-react-iota.vercel.app",
            "https://repository-react-*.vercel.app",
            "https://*.vercel.app",
            "http://localhost:*",
             "http://127.0.0.1:*"
        ));
        
        // Allow all HTTP methods including OPTIONS for preflight
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"
        ));
        
        // Allow all headers (including custom headers)
        config.setAllowedHeaders(Collections.singletonList("*"));
        
        // Expose all headers in response
        config.setExposedHeaders(Collections.singletonList("*"));
        
        // Allow credentials (cookies, authorization headers)
        config.setAllowCredentials(true);
        
        // Cache preflight response for 1 hour
        config.setMaxAge(3600L);
        
        // Apply CORS configuration to all paths
        source.registerCorsConfiguration("/**", config);
        
        // Create filter registration with highest priority to ensure it runs first
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
            new CorsFilter(source)
        );
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setUrlPatterns(Collections.singletonList("/*"));
        
        return bean;
    }
}
