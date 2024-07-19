package gr.unipi.javaspot.dtos;

public record ExamProgress(
        int correctAnswers,
        int totalQuestions
) {
}
