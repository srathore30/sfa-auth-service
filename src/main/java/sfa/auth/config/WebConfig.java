package sfa.auth.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://www.smartsalonbot.com/","http://localhost:3000/", "https://smartsalonbot.com/","https://smart-salon-staff.smartsalonbot.com/","https://smartsalonbot.com/","https://smart-salon-staff.smartsalonbot.com/","https://smart-salon-staff.smartsalonbot.com/","https://www.smart-salon-staff.smartsalonbot.com/","https://customer.smartsalonbot.com/")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);

    }
}
