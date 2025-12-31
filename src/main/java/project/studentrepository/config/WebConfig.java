package project.studentrepository.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * CORS Filter Configuration - Handles preflight OPTIONS requests
     * This filter-based approach ensures CORS is applied at the filter level,
     * which is crucial for proper preflight request handling
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Allow production and Vercel preview URLs using patterns
        // Patterns allow wildcards which is needed for Vercel preview deployments
        // Using allowedOriginPatterns instead of allowedOrigins to support wildcards
        config.setAllowedOriginPatterns(Arrays.asList(
            // Production frontend URL (Vercel)
            "https://repository-react-iota.vercel.app",
            // Vercel preview deployments (wildcard pattern)
            "https://repository-react-*.vercel.app",
            // All Vercel deployments (catch-all for any Vercel app)
            "https://*.vercel.app",
            // Render deployments (all Render apps)
            "https://*.onrender.com",
            // Localhost for development (any port)
            "http://localhost:*",
            "http://127.0.0.1:*",
            // Common development ports
            "http://localhost:3000",
            "http://localhost:5173",
            "http://localhost:8080",
            "http://127.0.0.1:3000",
            "http://127.0.0.1:5173"
        ));
        
        // Allow all HTTP methods including OPTIONS for preflight requests
        config.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"
        ));
        
        // Allow all headers (including custom headers like Authorization, Content-Type, etc.)
        config.setAllowedHeaders(Collections.singletonList("*"));
        
        // Expose all headers in response (important for frontend to read response headers)
        config.setExposedHeaders(Collections.singletonList("*"));
        
        // Allow credentials (cookies, authorization headers, etc.)
        config.setAllowCredentials(true);
        
        // Cache preflight response for 1 hour (reduces number of preflight requests)
        config.setMaxAge(3600L);
        
        // Apply CORS configuration to all paths
        source.registerCorsConfiguration("/**", config);
        
        // Create filter registration with highest priority to ensure it runs first
        // This is critical for proper CORS handling before other filters process the request
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(
            new CorsFilter(source)
        );
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.setUrlPatterns(Collections.singletonList("/*"));
        
        return bean;
    }
    
    /**
     * Additional CORS Configuration using WebMvcConfigurer
     * This provides redundancy and ensures CORS is handled at both filter and MVC levels
     */
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                // Use allowedOriginPatterns for wildcard support (Vercel preview URLs)
                .allowedOriginPatterns(
                    "https://repository-react-iota.vercel.app",
                    "https://repository-react-*.vercel.app",
                    "https://*.vercel.app",
                    "https://*.onrender.com",
                    "http://localhost:*",
                    "http://127.0.0.1:*",
                    "http://localhost:3000",
                    "http://localhost:5173",
                    "http://localhost:8080"
                )
                // Explicitly allow all necessary HTTP methods
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
                // Allow all headers
                .allowedHeaders("*")
                // Expose all headers
                .exposedHeaders("*")
                // Allow credentials for authenticated requests
                .allowCredentials(true)
                // Cache preflight requests for 1 hour
                .maxAge(3600);
    }
}
