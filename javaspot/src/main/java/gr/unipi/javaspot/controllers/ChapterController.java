package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.exceptions.ChapterNotFoundException;
import gr.unipi.javaspot.exceptions.SectionNotFoundException;
import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.models.Section;
import gr.unipi.javaspot.services.ChapterService;
import gr.unipi.javaspot.services.SectionService;
import gr.unipi.javaspot.utils.ControllerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chapters")
public class ChapterController {

    private final ChapterService chapterService;
    private final SectionService sectionService;

    @Autowired
    public ChapterController(ChapterService chapterService, SectionService sectionService) {
        this.chapterService = chapterService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public String getChapters(Model model) {
        model.addAttribute("chapters", chapterService.findAll());
        return ControllerConstants.CHAPTERS.getHtmlFilePath();
    }

    @GetMapping("/{id}/sections")
    public String getChapterSections(Model model, @PathVariable int id) {
        Chapter selectedChapter = chapterService.getById(id).orElseThrow(() -> new ChapterNotFoundException(id));
        model.addAttribute("selectedChapterTitle", selectedChapter.getTitle());
        model.addAttribute("sections", sectionService.getAllByChapterId(id));
        return ControllerConstants.SECTIONS.getHtmlFilePath();
    }

}
