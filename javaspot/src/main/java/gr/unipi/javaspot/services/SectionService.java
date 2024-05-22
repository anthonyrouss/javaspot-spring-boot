package gr.unipi.javaspot.services;

import gr.unipi.javaspot.models.Section;

import java.util.List;

public interface SectionService extends GeneralService<Section> {
    List<Section> getAllByChapterId(int chapterId);
}
