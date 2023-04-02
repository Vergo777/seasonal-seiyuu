package moe.vergo.seasonalseiyuuapi.adapter.in.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebInputConfig {
    @Bean
    WebMvcConfigurer configurer () {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers (ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").
                        addResourceLocations("classpath:/static/");
            }
        };
    }
}
