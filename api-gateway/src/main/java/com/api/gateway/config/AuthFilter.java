package com.api.gateway.config;

import com.api.gateway.dto.RequestDto;
import com.api.gateway.dto.TokenDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    
    WebClient.Builder webClientBuilder;
    
    AuthFilter(WebClient.Builder webClientBuilder) {
        
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }
    
    
    @Override
    public GatewayFilter apply(final Config config) {
        
        return ((exchange, chain) -> {
            if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, HttpStatus.BAD_REQUEST);
            }
            String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] chunks = tokenHeader.split(" ");
            if(chunks.length != 2 || !chunks[0].equals("Bearer"))
                return onError(exchange,HttpStatus.BAD_REQUEST);
            return webClientBuilder.build()
                            .post()
                            .uri("http://auth-service/auth/validate?token=" + chunks[1])
                            .bodyValue(new RequestDto(exchange.getRequest().getPath().toString(),exchange.getRequest().getMethod().toString()))
                            .retrieve()
                            .bodyToMono(TokenDto.class)
                            .map(t -> {
                                t.getToken();
                                return exchange;
                            }).flatMap(chain::filter);
        });
    }
    
    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
    
    public static class Config {
    
    }
}