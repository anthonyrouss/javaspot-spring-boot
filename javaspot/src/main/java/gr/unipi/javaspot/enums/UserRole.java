package gr.unipi.javaspot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

    STUDENT(1, "student");

    private final Integer id;
    private final String role;

}
