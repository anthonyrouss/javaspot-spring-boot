package gr.unipi.javaspot.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerRequest {
    private String username;
    private String answer;
    private int examId;
}
