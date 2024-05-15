package gr.unipi.javaspot.models;

import jakarta.persistence.*;

@Entity
@Table(name = "chapters")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

}
