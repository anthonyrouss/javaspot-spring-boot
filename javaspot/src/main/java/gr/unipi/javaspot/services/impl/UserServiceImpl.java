package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.dtos.SkillLevelQuestion;
import gr.unipi.javaspot.dtos.UserDto;
import gr.unipi.javaspot.enums.SkillLevel;
import gr.unipi.javaspot.enums.UserRole;
import gr.unipi.javaspot.mappers.UserDtoMapper;
import gr.unipi.javaspot.models.Role;
import gr.unipi.javaspot.models.User;
import gr.unipi.javaspot.repositories.UserRepository;
import gr.unipi.javaspot.services.ExaminerService;
import gr.unipi.javaspot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static gr.unipi.javaspot.security.SecurityConfig.getUserDetails;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExaminerService examinerService;

    @Override
    public void signUpAsStudent(UserDto userDto) {
        Role role = new Role(UserRole.STUDENT.getId(), UserRole.STUDENT.getRole());
        User user = UserDtoMapper.mapToUser(userDto, role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void updateSkillLevel(SkillLevelQuestion[] questions) {
        User user = userRepository.findByUsername(getUserDetails().getUsername());

        // Set skill level based on the answers to the questions
        if (examinerService.analyzeAnswer(questions[1].getText(), questions[1].getAnswer()).isCorrect()) {
            user.setSkillLevel(SkillLevel.INTERMEDIATE);
        } else if (examinerService.analyzeAnswer(questions[0].getText(), questions[0].getAnswer()).isCorrect()) {
            user.setSkillLevel(SkillLevel.BEGINNER);
        } else {
            user.setSkillLevel(SkillLevel.INTRO);
        }
        userRepository.save(user);

        // Update UserDetails with the new skill level
        getUserDetails().setSkillLevel(user.getSkillLevel());
    }

}
