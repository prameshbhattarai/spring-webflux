# spring-webflux

Integration of Spring Webflux with Mongo Reactive and Postgres Reactive.  

Run the docker to start MongoDB and PostgreSQL containers.
```
$ sudo docker-compose -f docker-compose.yml up
```

Demo spring webflux (reactive) using RouterFunction and Mongo Reactive
* REST API using RouterFunction.
* CRUD operation using ReactiveMongoRepository.
* Integration test using WebFluxTest.

Demo spring webflux (reactive) using RestController and Postgres Reactive
* REST API using RestController.
* CRUD operation using ReactiveCrudRepository.
* Integration test using WebClient.
