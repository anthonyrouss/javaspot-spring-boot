package gr.unipi.javaspot.exceptions;

public class ChapterNotFoundException extends ResourceNotFoundException {

    public ChapterNotFoundException(int id) {
        super(String.format("Chapter with id %d not found.", id));
    }

}
