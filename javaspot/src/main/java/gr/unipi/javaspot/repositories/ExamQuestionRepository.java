package gr.unipi.javaspot.repositories;

import gr.unipi.javaspot.models.ExamQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion, Integer> {
}
