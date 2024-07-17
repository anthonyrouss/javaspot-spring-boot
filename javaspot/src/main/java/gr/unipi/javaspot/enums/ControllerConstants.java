package gr.unipi.javaspot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerConstants {

    CHAPTERS("chapters"),
    SECTIONS("sections"),
    LEARN("learn");

    private final String htmlFilePath;

}
