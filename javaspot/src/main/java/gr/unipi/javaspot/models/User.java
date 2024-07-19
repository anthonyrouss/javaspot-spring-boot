package gr.unipi.javaspot.models;

import gr.unipi.javaspot.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean enabled;

    @Column(nullable = false)
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;

    // Association with the role id
    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;

}
