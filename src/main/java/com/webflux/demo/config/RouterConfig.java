package com.webflux.demo.config;

import com.webflux.demo.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {
    @Bean
    public RouterFunction<ServerResponse> routes(UserHandler userHandler) {
        return route(GET("/handler/users"), userHandler::getAllUsers)
                .andRoute(GET("/handler/users/name/{name}"), userHandler::getAllUsersByName)
                .andRoute(GET("/handler/users/{id}"), userHandler::getUserById)
                .andRoute(PUT("/handler/users/{id}"), userHandler::updateUserById)
                .andRoute(DELETE("/handler/users/{id}"), userHandler::deleteUser)
                .andRoute(POST("/handler/users"), userHandler::create);
    }
}
