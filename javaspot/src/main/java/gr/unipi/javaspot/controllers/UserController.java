package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.dtos.ExamStats;
import gr.unipi.javaspot.services.ExamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/me")
@AllArgsConstructor
public class UserController {

    private final ExamService examService;

    @GetMapping("/progress")
    public String getProgress(Model model, Principal principal) {
        // Get the Exam Stats of the user
        List<ExamStats> examStatsList = examService.getExamStatsByUsername(principal.getName());

        model.addAttribute("examStatsList", examStatsList);
        return "progress";
    }

}
