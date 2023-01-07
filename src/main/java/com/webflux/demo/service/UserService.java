package com.webflux.demo.service;

import com.webflux.demo.dao.UserRepository;
import com.webflux.demo.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Mono<User> updateUser(String userId, User user) {
        return userRepository.findById(userId)
                .flatMap(dbUser -> {
                    dbUser.setName(user.getName());
                    dbUser.setDepartment(user.getDepartment());
                    dbUser.setAge(user.getAge());
                    dbUser.setSalary(user.getSalary());
                    return userRepository.save(dbUser);
                });
    }

    public Mono<User> deleteUser(String userId) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> userRepository.delete(existingUser)
                        .then(Mono.just(existingUser)));
    }

    public Flux<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }
}
