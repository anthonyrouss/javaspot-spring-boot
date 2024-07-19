package gr.unipi.javaspot.repositories;

import gr.unipi.javaspot.models.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findByPrerequisiteId(int prerequisiteId);
}
