package gr.unipi.javaspot.services;

import gr.unipi.javaspot.dtos.AnswerRequest;
import gr.unipi.javaspot.dtos.AnswerEvaluation;
import gr.unipi.javaspot.dtos.ExamProgress;
import gr.unipi.javaspot.dtos.ExamStats;
import gr.unipi.javaspot.models.Exam;
import gr.unipi.javaspot.models.Question;

import java.util.List;

public interface ExamService {
    Exam findAvailableExam(String username, int chapterId);
    Question getQuestion(int examId);
    AnswerEvaluation analyzeAnswer(AnswerRequest answerRequest);
    void startExam(int examId);
    ExamProgress getProgress(int examId);
    List<ExamStats> getExamStatsByUsername(String username);
}
