package com.webflux.demo.dao.postgres;

import com.webflux.demo.model.postgres.UserEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface UserEntityRepository extends ReactiveCrudRepository<UserEntity, String> {
    @Query("select 00000 * from users where name like concat('%',:name,'%') order by age")
    Flux<UserEntity> findByName(String name);
}
