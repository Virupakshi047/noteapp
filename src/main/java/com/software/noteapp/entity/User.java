package com.software.noteapp.entity;

import com.software.noteapp.enums.UserEnums;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String emailId;
    @OneToMany(mappedBy = "users",cascade = CascadeType.ALL)
    private List<Note> notes;
    @Enumerated(EnumType.STRING)
    private UserEnums.UserRoles userRole;
    private String password;
}
