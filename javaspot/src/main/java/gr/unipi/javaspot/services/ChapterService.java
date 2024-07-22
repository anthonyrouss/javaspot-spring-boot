package gr.unipi.javaspot.services;

import gr.unipi.javaspot.dtos.ChapterDto;
import gr.unipi.javaspot.models.Chapter;

import java.util.List;

public interface ChapterService extends GeneralService<Chapter> {
    List<ChapterDto> getNextChapters(int prerequisiteId);
}
