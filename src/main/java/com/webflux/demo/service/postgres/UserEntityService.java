package com.webflux.demo.service.postgres;

import com.webflux.demo.dao.postgres.UserEntityRepository;
import com.webflux.demo.model.postgres.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserEntityService {

    private final UserEntityRepository userRepository;

    public Mono<UserEntity> createUser(UserEntity user) {
        return userRepository.save(user);
    }

    public Flux<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<UserEntity> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Mono<UserEntity> updateUser(String userId, UserEntity user) {
        return userRepository.findById(userId)
                .flatMap(dbUser -> {
                    dbUser.setName(user.getName());
                    dbUser.setDepartment(user.getDepartment());
                    dbUser.setAge(user.getAge());
                    dbUser.setSalary(user.getSalary());
                    return userRepository.save(dbUser);
                });
    }

    public Mono<UserEntity> deleteUser(String userId) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> userRepository.delete(existingUser)
                        .then(Mono.just(existingUser)));
    }

// todo
//    public Flux<User> getUsersByName(String name) {
//        return userRepository.findByName(name);
//    }
}
