package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.dtos.AnswerRequest;
import gr.unipi.javaspot.dtos.ExamProgress;
import gr.unipi.javaspot.dtos.ExamStats;
import gr.unipi.javaspot.enums.ExamStatus;
import gr.unipi.javaspot.dtos.AnswerEvaluation;
import gr.unipi.javaspot.exceptions.InvalidAction;
import gr.unipi.javaspot.exceptions.ResourceNotFoundException;
import gr.unipi.javaspot.models.*;
import gr.unipi.javaspot.repositories.ExamQuestionRepository;
import gr.unipi.javaspot.repositories.ExamRepository;
import gr.unipi.javaspot.repositories.QuestionRepository;
import gr.unipi.javaspot.services.ChapterService;
import gr.unipi.javaspot.services.ExamService;
import gr.unipi.javaspot.services.ExaminerService;
import gr.unipi.javaspot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final ExaminerService examinerService;
    private final ExamQuestionRepository examQuestionRepository;
    private final UserService userService;
    private final ChapterService chapterService;

    @Override
    public Exam findAvailableExam(
            String username,
            int chapterId
    ) {
        // Fetch the exam
        Optional<Exam> examOptional = examRepository.findByUser_UsernameAndChapter_Id(username, chapterId);
        // If exam entry doesn't exist in the db, throw an exception
        if (examOptional.isEmpty()) throw new ResourceNotFoundException("Exam not found");

        Exam exam = examOptional.get();
        // If exam is completed, throw an exception
        if (exam.getStatus().equals(ExamStatus.COMPLETED)) throw new InvalidAction("Exam is already completed");

        return exam;
    }

    @Override
    public Question getQuestion(int examId) {
        // Fetch the exam
        Optional<Exam> examOptional = examRepository.findById(examId);
        if (examOptional.isEmpty()) throw new ResourceNotFoundException("Exam not found");

        Exam exam = examOptional.get();
        // Check if the exam is in progress
        if (!exam.getStatus().equals(ExamStatus.IN_PROGRESS)) throw new InvalidAction("Exam is not in progress");

        Optional<Question> questionOptional = exam.getExamQuestions().stream()
                .filter(examQuestion -> !examQuestion.isFoundAnswer())
                .findFirst()
                .map(ExamQuestion::getQuestion);

        if (questionOptional.isEmpty()) throw new InvalidAction("No more available questions");

        return questionOptional.get();
    }

    @Override
    public AnswerEvaluation analyzeAnswer(AnswerRequest answerRequest) {
        // Fetch the exam
        Optional<Exam> examOptional = examRepository.findById(answerRequest.getExamId());
        if (examOptional.isEmpty()) throw new ResourceNotFoundException("Exam not found");

        Exam exam = examOptional.get();
        Optional<ExamQuestion> currentQuestionOptional = exam.getExamQuestions().stream().filter(q -> !q.isFoundAnswer()).findFirst();
        if (currentQuestionOptional.isEmpty()) throw new RuntimeException("Available question not found");

        ExamQuestion examQuestion = currentQuestionOptional.get();

        AnswerEvaluation examinersEvaluation = examinerService.analyzeAnswer(examQuestion.getQuestion().getText(), answerRequest.getAnswer());

        // Increment tries
        examQuestion.setTries(examQuestion.getTries() + 1);

        boolean examFinished = false;
        if (examinersEvaluation.isCorrect()) {
            examQuestion.setFoundAnswer(true);

            // Check for exam completion
            ExamProgress progress = getProgress(exam.getId());
            if (progress.correctAnswers() >= progress.totalQuestions()) examFinished = true;
        }

        // Save exam question
        examQuestionRepository.save(examQuestion);

        if (examFinished) {
            exam.setStatus(ExamStatus.COMPLETED);
            examRepository.save(exam);

            List<Chapter> nextChapters = chapterService.getNextChapters(exam.getChapter().getId());

            // Unlock next chapters
            userService.unlockChapters(
                    answerRequest.getUsername(),
                    nextChapters
            );
        }

        return examinersEvaluation;
    }

    @Override
    public void startExam(
            int examId
    ) {
        // Fetch the exam
        Optional<Exam> examOptional = examRepository.findById(examId);

        if (examOptional.isEmpty()) throw new ResourceNotFoundException("Exam not found");

        Exam exam = examOptional.get();

        if (!exam.getStatus().equals(ExamStatus.NOT_STARTED)) throw new InvalidAction("Exam cannot be started");

        initializeQuestions(exam);

        exam.setStatus(ExamStatus.IN_PROGRESS);
        examRepository.save(exam);
    }

    @Override
    public ExamProgress getProgress(int examId) {
        // Fetch the exam
        Optional<Exam> examOptional = examRepository.findById(examId);
        if (examOptional.isEmpty()) throw new ResourceNotFoundException("Exam not found");

        List<ExamQuestion> examQuestions = examOptional.get().getExamQuestions();
        if (examQuestions.isEmpty()) throw new InvalidAction("There are no exam questions");

        int correctAnswers = (int) examQuestions
                .stream()
                .filter(ExamQuestion::isFoundAnswer)
                .count();

        return new ExamProgress(correctAnswers, examQuestions.size());
    }

    @Override
    public List<ExamStats> getExamStatsByUsername(String username) {
        // Fetch the completed exams of the user
        Optional<List<Exam>> optionalExamList = examRepository.findAllByUser_UsernameAndStatus(username, ExamStatus.COMPLETED);

        if (optionalExamList.isEmpty()) return List.of();

        List<Exam> examList = optionalExamList.get();

        return examList.stream().map(Exam::toExamStats).toList();
    }

    private void initializeQuestions(Exam exam) {
        // Fetch 3 random questions
        List<Question> questionList = questionRepository.getRandomQuestions(
                exam.getChapter().getId(),
                PageRequest.of(0, 3)
        ).getContent();

        exam.addQuestions(questionList);
    }

}
