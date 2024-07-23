package gr.unipi.javaspot.models;

import gr.unipi.javaspot.dtos.ChapterDto;
import gr.unipi.javaspot.dtos.DtoConvertible;
import gr.unipi.javaspot.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "chapters")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chapter implements DtoConvertible<ChapterDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private SkillLevel skillLevel;

    @ManyToOne
    @JoinColumn(name = "prerequisite_id")
    private Chapter prerequisite;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL)
    List<Question> questions;

    @OneToOne(mappedBy = "chapter")
    private Exam exam;

    @Override
    public ChapterDto toDto() {
        return new ChapterDto(
                this.id,
                this.title,
                this.description,
                this.skillLevel
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(id, chapter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
