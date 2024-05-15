package gr.unipi.javaspot.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean enabled;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // Association with the role id

    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;
}
