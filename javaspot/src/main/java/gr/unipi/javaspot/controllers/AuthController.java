package gr.unipi.javaspot.controllers;

import gr.unipi.javaspot.dtos.UserDto;
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

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/signIn")
    public String signInPage() {
        return "sign-in";
    }

    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "sign-up";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "sign-up";
        }

        userService.signUpAsStudent(userDto);
        return "redirect:/auth/signUp?success";
    }

}
