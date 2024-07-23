package gr.unipi.javaspot.repositories;

import gr.unipi.javaspot.enums.SkillLevel;
import gr.unipi.javaspot.models.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "SELECT q FROM Question q WHERE q.chapter.id = :chapterId ORDER BY RANDOM()")
    Page<Question> getRandomQuestions(@Param("chapterId") int chapterId, Pageable pageable);

    @Query(value = "SELECT q FROM Question q WHERE q.chapter.skillLevel = :skillLevel ORDER BY RANDOM() LIMIT 1")
    Optional<Question> findQuestionByChapter_SkillLevel(SkillLevel skillLevel);

}
