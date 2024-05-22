package gr.unipi.javaspot.utils;

import lombok.Getter;

@Getter
public enum ControllerConstants {

    CHAPTERS("chapters"),
    SECTIONS("sections"),
    LEARN("learn");

    private final String htmlFilePath;

    ControllerConstants(String htmlFilePath) {
        this.htmlFilePath = htmlFilePath;
    }
}
