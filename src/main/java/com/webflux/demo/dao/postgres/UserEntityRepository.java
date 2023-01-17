package com.webflux.demo.dao.postgres;

import com.webflux.demo.model.postgres.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends ReactiveCrudRepository<UserEntity, String> {
}
