package gr.unipi.javaspot.models;


import jakarta.persistence.*;

@Entity
@Table(name = "sections")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    // chapter-x/section-y.mp4
    private String videoSourcePath;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Chapter chapter;

}
