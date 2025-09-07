package com.example.bigbisort_be.common.Internationalization;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;
@Configuration
public class InternationalizationLocaleResolver extends AcceptHeaderLocaleResolver
        implements WebMvcConfigurer {


    /**
     * this method resolves the locale for incoming HTTP requests based on the Accept-Language header
     * defaulting to English if not provided
     */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.ENGLISH
                : Locale.forLanguageTag(headerLang);
    }

    /**
     * this method configures a ResourceBundleMessageSource bean to load localized messages
     * from messages_en.properties with UTF-8 encoding
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("config/i18n/messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}
