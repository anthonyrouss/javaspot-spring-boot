package gr.unipi.javaspot.models;

import gr.unipi.javaspot.dtos.DtoConvertible;
import gr.unipi.javaspot.dtos.QuestionDto;
import gr.unipi.javaspot.dtos.SkillLevelQuestion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question implements DtoConvertible<QuestionDto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Override
    public QuestionDto toDto() {
        return new QuestionDto(
                this.text
        );
    }

    public SkillLevelQuestion toSkillLevelQuestion() {
        return new SkillLevelQuestion(
                this.chapter.getSkillLevel(),
                this.text,
                ""
        );
    }
}
