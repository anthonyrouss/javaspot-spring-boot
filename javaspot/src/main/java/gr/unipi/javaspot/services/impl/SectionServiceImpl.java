package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.models.Section;
import gr.unipi.javaspot.repositories.SectionRepository;
import gr.unipi.javaspot.services.SectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    @Override
    public List<Section> findAll() {
        return sectionRepository.findAll();
    }

    @Override
    public Optional<Section> getById(int id) {
        return sectionRepository.findById(id);
    }

    @Override
    public List<Section> getAllByChapterId(int chapterId) {
        return sectionRepository.findAllByChapterId(chapterId);
    }

}
