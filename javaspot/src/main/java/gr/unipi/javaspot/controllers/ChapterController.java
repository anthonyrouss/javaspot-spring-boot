package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.enums.ControllerConstants;
import gr.unipi.javaspot.enums.ExamStatus;
import gr.unipi.javaspot.exceptions.ChapterNotFoundException;
import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.models.Exam;
import gr.unipi.javaspot.services.ChapterService;
import gr.unipi.javaspot.services.ExamService;
import gr.unipi.javaspot.services.SectionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;
    private final SectionService sectionService;
    private final ExamService examService;

    @GetMapping({"/", "/index", "/chapters"})
    public String getChapters(Model model) {
        model.addAttribute("chapters", chapterService.findAll());
        return ControllerConstants.CHAPTERS.getHtmlFilePath();
    }

    @GetMapping("/chapters/{id}/sections")
    public String getChapterSections(Model model, @PathVariable int id) {
        Chapter selectedChapter = chapterService.getById(id).orElseThrow(() -> new ChapterNotFoundException(id));
        model.addAttribute("selectedChapterTitle", selectedChapter.getTitle());
        model.addAttribute("sections", sectionService.getAllByChapterId(id));
        return ControllerConstants.SECTIONS.getHtmlFilePath();
    }

    @GetMapping("/chapters/{id}/exams")
    public String getChapterExams(Model model, @PathVariable int id, Principal principal) {
        Exam exam = examService.findAvailableExam(principal.getName(), id);

        model.addAttribute("examId", exam.getId());
        model.addAttribute("examChapterId", exam.getChapter().getId());
        model.addAttribute("examChapterTitle", exam.getChapter().getTitle());
        model.addAttribute("examStarted", exam.getStatus().equals(ExamStatus.IN_PROGRESS));
        return "exams";
    }

}
