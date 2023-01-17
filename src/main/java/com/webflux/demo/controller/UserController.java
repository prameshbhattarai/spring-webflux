package com.webflux.demo.controller;

import com.webflux.demo.model.postgres.UserEntity;
import com.webflux.demo.service.postgres.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserEntityService userService;

    @GetMapping()
    public Flux<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<UserEntity> getUserById(@PathVariable("id") String userId) {
        return userService.getUserById(userId);
    }

    @PostMapping()
    public Mono<UserEntity> create(@RequestBody UserEntity user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public Mono<UserEntity> updateUserById(@PathVariable("id") String userId, @RequestBody UserEntity user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/{id}")
    public Mono<UserEntity> deleteUser(@PathVariable("id") String userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/name/{name}")
    public Flux<UserEntity> getAllUsersByName(@PathVariable("name") String name) {
        return userService.getUsersByName(name);
    }
}
