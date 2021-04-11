package org.linlinjava.litemall.core.config;

import org.linlinjava.litemall.core.storage.config.StorageProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StaticImageConfiguration implements WebMvcConfigurer {

    private final StorageProperties properties;

    public StaticImageConfiguration(StorageProperties properties) {
        this.properties = properties;
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + properties.getLocal().getStoragePath());
    }

}
