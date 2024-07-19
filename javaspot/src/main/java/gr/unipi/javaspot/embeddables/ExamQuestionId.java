package gr.unipi.javaspot.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ExamQuestionId implements Serializable {

    @Column(name = "exam_id")
    private int examId;

    @Column(name = "question_id")
    private int questionId;

}
