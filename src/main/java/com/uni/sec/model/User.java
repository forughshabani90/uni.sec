package com.uni.sec.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users",
uniqueConstraints = {
        @UniqueConstraint(columnNames ="username"),
        @UniqueConstraint(columnNames = "email")
}
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user-role", joinColumns = @JoinColumn(name = "user-id"),
            inverseJoinColumns = @JoinColumn(name = "role-id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
    }
}
