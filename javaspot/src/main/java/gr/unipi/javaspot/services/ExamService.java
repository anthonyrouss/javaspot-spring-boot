package gr.unipi.javaspot.services;

import gr.unipi.javaspot.dtos.AnswerRequest;
import gr.unipi.javaspot.dtos.AnswerEvaluation;
import gr.unipi.javaspot.dtos.ExamProgress;
import gr.unipi.javaspot.models.Exam;
import gr.unipi.javaspot.models.Question;

public interface ExamService {
    Exam findAvailableExam(String username, int sectionId);
    Question getQuestion(int examId);
    AnswerEvaluation analyzeAnswer(AnswerRequest answerRequest);
    void startExam(int examId);
    ExamProgress getProgress(int examId);
}
