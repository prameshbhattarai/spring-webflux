package com.webflux.demo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(of = {"id","name","department"})
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "users")
public class User {
    @Id
    private String id;
    private String name;
    private int age;
    private double salary;
    private String department;
}
