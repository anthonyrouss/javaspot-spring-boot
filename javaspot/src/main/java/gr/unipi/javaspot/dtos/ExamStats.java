package gr.unipi.javaspot.dtos;

import java.util.List;

public record ExamStats(
        ChapterDto chapter,
        List<ExamQuestionDto> questions
) {
}
