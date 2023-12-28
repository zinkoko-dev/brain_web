package com.group.brain_web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    private String email;
    private String password;
    private boolean enable = false;
    private String status;

    @Lob
    @Column(columnDefinition = "longblob")
    private String userImage;

    @Enumerated(EnumType.STRING)
    private Role role;
}
