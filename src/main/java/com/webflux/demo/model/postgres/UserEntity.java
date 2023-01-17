package com.webflux.demo.model.postgres;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@EqualsAndHashCode(of = {"id", "name", "department"})
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    private String id;
    private String name;
    private int age;
    private double salary;
    private String department;
}
