package com.example.platform.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String password;

    private HashSet<UUID> friends = new HashSet<>();

    private int streak;
    private int rank;
    private int status;
}
