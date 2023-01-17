package com.webflux.demo.model.postgres;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode(of = {"id", "name", "department"})
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private String id;
    private String name;
    private int age;
    private double salary;
    private String department;
}
