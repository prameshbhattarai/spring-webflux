package com.webflux.demo.handler;

import com.webflux.demo.config.RouterConfig;
import com.webflux.demo.model.mongo.UserDocument;
import com.webflux.demo.service.mongo.UserDocumentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RouterConfig.class, UserHandler.class})
@WebFluxTest
public class UserHandlerTestIT {

    private static final String GET_ALL_USERS = "/handler/users";
    private static final String GET_ALL_USER_BY_NAME = "/handler/users/name/{name}";
    private static final String GET_USER_BY_ID = "/handler/users/{id}";
    private static final String UPDATE_USER_BY_ID = "/handler/users/{id}";
    private static final String DELETE_USER_BY_ID = "/handler/users/{id}";
    private static final String CREATE_USER = "/handler/users";
    private static final UserDocument user = UserDocument.builder()
            .id("001")
            .name("pramesh")
            .age(30)
            .department("IT")
            .salary(45000)
            .build();
    private static final ArgumentMatcher<UserDocument> USER_ARGUMENT_MATCHER = (arg) ->
            arg.getName().equals("pramesh") &&
                    arg.getAge() == 30 &&
                    arg.getDepartment().equals("IT") &&
                    arg.getSalary() == 45000;

    @MockBean
    private UserDocumentService userService;

    @Autowired
    private WebTestClient client;

    @Test
    void testCreateUser() {
        Mockito.when(userService.createUser(Mockito.argThat(USER_ARGUMENT_MATCHER)))
                .thenReturn(Mono.just(user));

        client.post()
                .uri(CREATE_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isCreated();

        Mockito.verify(userService).createUser(Mockito.argThat(USER_ARGUMENT_MATCHER));
    }

    @Test
    void testGetAllUsers() {
        var users = Flux.fromIterable(List.of(user));

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        client.get().uri(GET_ALL_USERS).exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(UserDocument.class);

        Mockito.verify(userService).getAllUsers();
    }

    @Test
    void testGetUserById() {
        Mockito.when(userService.getUserById(Mockito.eq(user.getId()))).thenReturn(Mono.just(user));

        client.get().uri(GET_USER_BY_ID, user.getId()).exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.id").isEqualTo(user.getId())
                .jsonPath("$.name").isEqualTo(user.getName())
                .jsonPath("$.age").isEqualTo(user.getAge())
                .jsonPath("$.department").isEqualTo(user.getDepartment())
                .jsonPath("$.salary").isEqualTo(user.getSalary());

        Mockito.verify(userService).getUserById(Mockito.eq(user.getId()));
    }

    @Test
    void testUpdateUserById() {
        Mockito.when(userService.updateUser(Mockito.eq(user.getId()), Mockito.argThat(USER_ARGUMENT_MATCHER)))
                .thenReturn(Mono.just(user));

        client.put()
                .uri(UPDATE_USER_BY_ID, user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().is2xxSuccessful();

        Mockito.verify(userService).updateUser(Mockito.eq(user.getId()), Mockito.argThat(USER_ARGUMENT_MATCHER));
    }

    @Test
    void testDeleteUserById() {
        Mockito.when(userService.deleteUser(Mockito.eq(user.getId()))).thenReturn(Mono.just(user));

        client.delete()
                .uri(DELETE_USER_BY_ID, user.getId())
                .exchange()
                .expectStatus().is2xxSuccessful();

        Mockito.verify(userService).deleteUser(Mockito.eq(user.getId()));
    }

    @Test
    void testGetAllUsersByName() {
        var users = Flux.fromIterable(List.of(user));

        Mockito.when(userService.getUsersByName(Mockito.eq(user.getName()))).thenReturn(users);

        client.get().uri(GET_ALL_USER_BY_NAME, user.getName()).exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(UserDocument.class);

        Mockito.verify(userService).getUsersByName(Mockito.eq(user.getName()));
    }

}
