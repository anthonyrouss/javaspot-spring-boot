package gr.unipi.javaspot.repositories;

import gr.unipi.javaspot.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
    List<Section> findAllByChapterId(int chapterId);
}
