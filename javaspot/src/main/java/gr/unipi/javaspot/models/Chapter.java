package gr.unipi.javaspot.models;

import gr.unipi.javaspot.dtos.ChapterDTO;
import gr.unipi.javaspot.dtos.DtoConvertible;
import gr.unipi.javaspot.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "chapters")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements DtoConvertible<ChapterDTO> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillLevel skillLevel;

    @ManyToOne
    @JoinColumn(name = "prerequisite_id")
    private Chapter prerequisite;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Question> questions;

    @Override
    public ChapterDTO toDto() {
        return new ChapterDTO(
                this.id,
                this.title,
                this.description,
                this.skillLevel
        );
    }
}
