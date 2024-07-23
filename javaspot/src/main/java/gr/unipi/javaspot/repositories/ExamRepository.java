package gr.unipi.javaspot.repositories;

import gr.unipi.javaspot.enums.ExamStatus;
import gr.unipi.javaspot.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Integer>  {
    Optional<Exam> findByUser_UsernameAndChapter_Id(String username, int sectionId);
    Optional<List<Exam>> findAllByUser_UsernameAndStatus(String username, ExamStatus status);
}
