package gr.unipi.javaspot.models;

import gr.unipi.javaspot.dtos.DtoConvertible;
import gr.unipi.javaspot.dtos.QuestionDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Question implements DtoConvertible<QuestionDTO> {

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
    public QuestionDTO toDto() {
        return new QuestionDTO(
                this.text
        );
    }
}
