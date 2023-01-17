package com.webflux.demo.service.mongo;

import com.webflux.demo.dao.mongo.UserDocumentRepository;
import com.webflux.demo.model.mongo.UserDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Service
public class UserDocumentService {

    private final UserDocumentRepository userRepository;

    public Mono<UserDocument> createUser(UserDocument user) {
        return userRepository.save(user);
    }

    public Flux<UserDocument> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<UserDocument> getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public Mono<UserDocument> updateUser(String userId, UserDocument user) {
        return userRepository.findById(userId)
                .flatMap(dbUser -> {
                    dbUser.setName(user.getName());
                    dbUser.setDepartment(user.getDepartment());
                    dbUser.setAge(user.getAge());
                    dbUser.setSalary(user.getSalary());
                    return userRepository.save(dbUser);
                });
    }

    public Mono<UserDocument> deleteUser(String userId) {
        return userRepository.findById(userId)
                .flatMap(existingUser -> userRepository.delete(existingUser)
                        .then(Mono.just(existingUser)));
    }

    public Flux<UserDocument> getUsersByName(String name) {
        return userRepository.findByName(name);
    }
}
