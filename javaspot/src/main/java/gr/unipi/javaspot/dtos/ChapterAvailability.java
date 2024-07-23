package gr.unipi.javaspot.dtos;

public record ChapterAvailability(
        ChapterDto chapter,
        boolean isAvailable
) {
}
