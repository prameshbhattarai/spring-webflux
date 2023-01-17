package com.webflux.demo.controller;

import com.webflux.demo.model.postgres.UserEntity;
import com.webflux.demo.service.postgres.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Mono<ServerResponse> NOT_FOUND_RESPONSE = ServerResponse.notFound().build();
    private final UserEntityService userService;

    @GetMapping()
    public Mono<ServerResponse> getAllUsers() {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getAllUsers(), UserEntity.class);
    }

    @GetMapping("/{id}")
    public Mono<ServerResponse> getUserById(@PathVariable("id") String userId) {
        return userService.getUserById(userId)
                .flatMap((user) ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(user))
                .switchIfEmpty(NOT_FOUND_RESPONSE);
    }

    @PostMapping()
    public Mono<ServerResponse> create(UserEntity user) {
        return userService.createUser(user)
                .flatMap((createdUser) ->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(createdUser))
                .switchIfEmpty(NOT_FOUND_RESPONSE);
    }

    @PutMapping("/{id}")
    public Mono<ServerResponse> updateUserById(@PathVariable("id") String userId, UserEntity user) {
        return userService.updateUser(userId, user)
                .flatMap((updatedUser) ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(updatedUser))
                .switchIfEmpty(NOT_FOUND_RESPONSE);
    }

    @DeleteMapping("/{id}")
    public Mono<ServerResponse> deleteUser(@PathVariable("id") String userId) {
        return userService.deleteUser(userId)
                .flatMap(deletedUser -> ServerResponse
                        .status(HttpStatus.ACCEPTED)
                        .bodyValue(deletedUser))
                .switchIfEmpty(NOT_FOUND_RESPONSE);
    }

// todo
//    public Mono<ServerResponse> getAllUsersByName(ServerRequest request) {
//        String name = request.pathVariable("name");
//        return ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(userService.getUsersByName(name), User.class);
//    }
}
