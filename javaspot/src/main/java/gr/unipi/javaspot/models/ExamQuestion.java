package gr.unipi.javaspot.models;

import gr.unipi.javaspot.embeddables.ExamQuestionId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_questions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamQuestion {

    @EmbeddedId
    private ExamQuestionId id;

    @ManyToOne
    @MapsId("examId")
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private int tries;

    @Column(nullable = false)
    private boolean foundAnswer;

}
