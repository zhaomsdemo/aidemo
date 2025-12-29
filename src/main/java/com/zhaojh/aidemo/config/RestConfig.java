package com.zhaojh.aidemo.config;

import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

@Configuration
public class RestConfig {

    public RestClientCustomizer restClientCustomizer(LogbookClientHttpRequestInterceptor interceptor) {
        return restClient -> restClient.requestInterceptor(interceptor);
    }
}
