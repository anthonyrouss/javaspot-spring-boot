package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.dtos.*;
import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.services.ChapterService;
import gr.unipi.javaspot.services.ExamService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class APIController {

    private final ChapterService chapterService;
    private final ExamService examService;

    public APIController(
            ChapterService chapterService,
            ExamService examService
    ) {
        this.chapterService = chapterService;
        this.examService = examService;
    }

    // TODO: Add aspect to check if the exam belongs to the caller (user)
    // TODO: Add userId to auth object

    @PutMapping("exams/{id}/start")
    @ResponseStatus(HttpStatus.OK)
    public void startExam(@PathVariable int id) {
        examService.startExam(id);
    }

    @GetMapping("exams/{id}/question")
    @ResponseStatus(HttpStatus.OK)
    public QuestionDto getQuestion(@PathVariable int id) {
        var question = examService.getQuestion(id);
        return question.toDto();
    }

    @PostMapping("exams/{id}/answer")
    @ResponseStatus(HttpStatus.OK)
    public AnswerEvaluation evaluateAnswer(
            @PathVariable int id,
            @RequestBody AnswerRequest answerRequest,
            Principal principal
    ) {
        answerRequest.setUsername(principal.getName());
        answerRequest.setExamId(id);
        return examService.analyzeAnswer(answerRequest);
    }

    @GetMapping("exams/{id}/progress")
    @ResponseStatus(HttpStatus.OK)
    public ExamProgress getProgress(@PathVariable int id) {
        return examService.getProgress(id);
    }

    @GetMapping("chapters/next")
    @ResponseStatus(HttpStatus.OK)
    public List<ChapterDto> getNextChapters(@RequestParam int prerequisiteChapterId) {
        return chapterService.getNextChapters(prerequisiteChapterId).stream().map(Chapter::toDto).toList();
    }

}