package com.hieu.crypto.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hieu.crypto.entity.Balance;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Balance> balances;
}
