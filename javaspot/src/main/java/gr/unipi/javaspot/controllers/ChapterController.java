package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.dtos.ChapterAvailability;
import gr.unipi.javaspot.enums.ExamStatus;
import gr.unipi.javaspot.exceptions.ChapterNotFoundException;
import gr.unipi.javaspot.exceptions.InvalidAction;
import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.models.Exam;
import gr.unipi.javaspot.models.User;
import gr.unipi.javaspot.services.ChapterService;
import gr.unipi.javaspot.services.ExamService;
import gr.unipi.javaspot.services.SectionService;
import gr.unipi.javaspot.enums.ControllerConstants;
import gr.unipi.javaspot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;
    private final SectionService sectionService;
    private final ExamService examService;
    private final UserService userService;

    @GetMapping({"/", "/index", "/chapters"})
    public String getChapters(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Chapter> chapters = chapterService.findAll();

        List<ChapterAvailability> chapterAvailabilityList = new ArrayList<>();

        chapters.forEach(chapter -> {
            ChapterAvailability chapterAvailability = new ChapterAvailability(
                    chapter.toDto(),
                    user.getUnlockedChapters().contains(chapter)
            );
            chapterAvailabilityList.add(chapterAvailability);
        });

        model.addAttribute("chapterAvailabilityList", chapterAvailabilityList);
        return ControllerConstants.CHAPTERS.getHtmlFilePath();
    }

    @GetMapping("/chapters/{id}/sections")
    public String getChapterSections(Model model, @PathVariable int id, Principal principal) {
        Chapter selectedChapter = chapterService.getById(id).orElseThrow(() -> new ChapterNotFoundException(id));
        User user = userService.findByUsername(principal.getName());

        boolean userHasAccess = user.getUnlockedChapters().stream()
                .map(Chapter::getId)
                .anyMatch(chapterId -> chapterId.equals(selectedChapter.getId()));

        if (!userHasAccess) throw new InvalidAction("The chapter is locked");

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
