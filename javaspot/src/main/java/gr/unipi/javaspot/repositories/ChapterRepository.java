package gr.unipi.javaspot.repositories;

import gr.unipi.javaspot.enums.SkillLevel;
import gr.unipi.javaspot.models.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Integer> {

    List<Chapter> findByPrerequisiteId(int prerequisiteId);

    List<Chapter> findChaptersBySkillLevelLessThanEqual(SkillLevel skillLevel);

}
