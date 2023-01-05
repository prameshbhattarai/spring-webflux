package com.webflux.demo.handler;

import com.webflux.demo.model.User;
import com.webflux.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@AllArgsConstructor
@Component
public class UserHandler {

    private static final Mono<ServerResponse> EMPTY_RESPONSE = ServerResponse.notFound().build();
    private final UserService userService;

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getAllUsers(), User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        String userId = serverRequest.pathVariable("id");
        return userService.findById(userId)
                .flatMap((user) ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user))
                .switchIfEmpty(EMPTY_RESPONSE);
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return user
                .flatMap(u -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(userService.createUser(u), User.class)
                );
    }

}
