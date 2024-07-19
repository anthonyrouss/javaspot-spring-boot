package gr.unipi.javaspot.services;

import gr.unipi.javaspot.dtos.ChapterDTO;
import gr.unipi.javaspot.models.Chapter;

import java.util.List;
import java.util.Optional;

public interface ChapterService extends GeneralService<Chapter> {
    List<ChapterDTO> getNextChapters(int prerequisiteId);
}
