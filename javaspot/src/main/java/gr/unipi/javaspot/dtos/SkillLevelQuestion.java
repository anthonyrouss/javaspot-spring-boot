package gr.unipi.javaspot.dtos;

import gr.unipi.javaspot.enums.SkillLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillLevelQuestion {

    private SkillLevel skillLevel;
    private String text;

    @NotBlank(message = "Answer cannot be empty.")
    private String answer;

}
