package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.dtos.ExamStats;
import gr.unipi.javaspot.dtos.SkillLevelQuestion;
import gr.unipi.javaspot.dtos.SkillLevelQuestionsWrapper;
import gr.unipi.javaspot.services.ExamService;
import gr.unipi.javaspot.services.QuestionService;
import gr.unipi.javaspot.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gr.unipi.javaspot.security.SecurityConfig.getUserDetails;

@Controller
@RequestMapping("/me")
@RequiredArgsConstructor
public class UserController {

    private final ExamService examService;
    private final QuestionService questionService;
    private final UserService userService;
    private final Map<Integer, SkillLevelQuestion[]> skillLevelQuestions = new HashMap<>();

    @GetMapping("/skill-level")
    public String skillLevelAssignmentPage(Model model) {
        SkillLevelQuestion[] questions = questionService.getSkillLevelAssignmentQuestions();
        skillLevelQuestions.put(getUserDetails().getId(), questions);
        model.addAttribute("wrapperClass", new SkillLevelQuestionsWrapper(questions));
        return "skill-level-assignment";
    }

    @PostMapping("/skill-level")
    public String updateSkillLevel(
            @Valid @ModelAttribute("wrapperClass") SkillLevelQuestionsWrapper wrapper,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("wrapperClass", wrapper);
            return "skill-level-assignment";
        }

        SkillLevelQuestion[] clientSideQuestions = wrapper.getQuestions();
        SkillLevelQuestion[] serverSideQuestions = skillLevelQuestions.get(getUserDetails().getId());

        for (int i = 0; i < serverSideQuestions.length; i++) {
            serverSideQuestions[i].setAnswer(clientSideQuestions[i].getAnswer());
        }

        userService.updateSkillLevel(serverSideQuestions);
        return "redirect:/";
    }

    @GetMapping("/progress")
    public String getProgress(Model model, Principal principal) {
        // Get the Exam Stats of the user
        List<ExamStats> examStatsList = examService.getExamStatsByUsername(principal.getName());

        model.addAttribute("examStatsList", examStatsList);
        return "progress";
    }

}
