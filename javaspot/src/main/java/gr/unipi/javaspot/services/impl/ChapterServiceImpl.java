package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.repositories.ChapterRepository;
import gr.unipi.javaspot.services.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChapterServiceImpl implements ChapterService {

    private final ChapterRepository chapterRepository;

    @Autowired
    public ChapterServiceImpl(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Override
    public List<Chapter> findAll() {
        return chapterRepository.findAll();
    }

    @Override
    public Optional<Chapter> getById(int id) {
        return chapterRepository.findById(id);
    }
}
