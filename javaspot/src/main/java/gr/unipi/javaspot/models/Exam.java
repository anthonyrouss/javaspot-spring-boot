package gr.unipi.javaspot.models;

import gr.unipi.javaspot.dtos.ExamQuestionDto;
import gr.unipi.javaspot.dtos.ExamStats;
import gr.unipi.javaspot.embeddables.ExamQuestionId;
import gr.unipi.javaspot.enums.ExamStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "exams")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    // Association with a user
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    // Association with a chapter
    @OneToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExamStatus status;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private List<ExamQuestion> examQuestions;

    public void addQuestions(List<Question> questions) {
        questions.forEach(q -> {

            ExamQuestionId examQuestionId = ExamQuestionId.builder()
                    .examId(this.id)
                    .questionId(q.getId())
                    .build();

            ExamQuestion examQuestion = ExamQuestion.builder()
                    .id(examQuestionId)
                    .exam(this)
                    .question(q)
                    .tries(0)
                    .foundAnswer(false)
                    .build();

            examQuestions.add(examQuestion);
        });
    }

    public ExamStats toExamStats() {
        List<ExamQuestionDto> examQuestionDtoList = examQuestions.stream().map(ExamQuestion::toDto).toList();
        return new ExamStats(
                chapter.toDto(),
                examQuestionDtoList
        );
    }

    public Exam(User user, Chapter chapter, ExamStatus status) {
        this.user = user;
        this.chapter = chapter;
        this.status = status;
    }
}
