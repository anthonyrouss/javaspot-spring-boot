package gr.unipi.javaspot.models;

import gr.unipi.javaspot.dtos.DtoConvertible;
import gr.unipi.javaspot.dtos.QuestionDto;
import jakarta.persistence.*;
import lombok.*;

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
}
