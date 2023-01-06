package com.webflux.demo.dao;

import com.webflux.demo.model.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    @Query(value = "{name: { $regex: ?0 }}", sort = "{age: 1}")
    Flux<User> findByName(String name);
}
