package gr.unipi.javaspot.exceptions;

public class SectionNotFoundException extends ResourceNotFoundException {

    public SectionNotFoundException(int id) {
        super(String.format("Section with id %d not found", id));
    }

}
