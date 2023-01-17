package com.webflux.demo.dao.mongo;

import com.webflux.demo.model.mongo.UserDocument;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserDocumentRepository extends ReactiveMongoRepository<UserDocument, String> {
    @Query(value = "{name: { $regex: ?0 }}", sort = "{age: 1}")
    Flux<UserDocument> findByName(String name);
}
