package gr.unipi.javaspot.dtos;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SkillLevelQuestionsWrapper {

    @Valid
    private SkillLevelQuestion[] questions;

}
