package com.github.inncontrol.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

/**
 * Created by Alex Avila Asto - A.K.A (Ryzeon)
 * Project: inncontrol-backend
 * Date: 7/1/25 @ 15:40
 */
@Component
@Slf4j
public class WebFluxConfiguration {

    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "*");
            return chain.filter(exchange);
        };
    }

    @Bean
    public WebFilter logRequestFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            String method = exchange.getRequest().getMethod().name();
            String path = exchange.getRequest().getPath().toString();
            log.info("📌 Request received: [{}] {}", method, path);
            return chain.filter(exchange).doOnSuccess(done ->
                    log.info("✅ Response sent for: [{}] {}", method, path)
            );
        };
    }

}