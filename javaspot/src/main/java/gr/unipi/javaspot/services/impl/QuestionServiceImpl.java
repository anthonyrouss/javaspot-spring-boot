package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.dtos.SkillLevelQuestion;
import gr.unipi.javaspot.enums.SkillLevel;
import gr.unipi.javaspot.exceptions.ResourceNotFoundException;
import gr.unipi.javaspot.models.Question;
import gr.unipi.javaspot.repositories.QuestionRepository;
import gr.unipi.javaspot.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public SkillLevelQuestion[] getSkillLevelAssignmentQuestions() {
        Optional<Question> beginnerQuestionOptional =
                questionRepository.findQuestionByChapter_SkillLevel(SkillLevel.BEGINNER);
        Optional<Question> intermediateQuestionOptional =
                questionRepository.findQuestionByChapter_SkillLevel(SkillLevel.INTERMEDIATE);

        if (beginnerQuestionOptional.isEmpty() || intermediateQuestionOptional.isEmpty()) {
            throw new ResourceNotFoundException("Questions not found.");
        }

        return new SkillLevelQuestion[]{
                beginnerQuestionOptional.get().toSkillLevelQuestion(),
                intermediateQuestionOptional.get().toSkillLevelQuestion()
        };
    }

}
