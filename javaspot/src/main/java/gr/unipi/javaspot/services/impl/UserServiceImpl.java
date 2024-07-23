package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.dtos.SkillLevelQuestion;
import gr.unipi.javaspot.dtos.UserDto;
import gr.unipi.javaspot.enums.ExamStatus;
import gr.unipi.javaspot.enums.SkillLevel;
import gr.unipi.javaspot.enums.UserRole;
import gr.unipi.javaspot.mappers.UserDtoMapper;
import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.models.Exam;
import gr.unipi.javaspot.models.Role;
import gr.unipi.javaspot.models.User;
import gr.unipi.javaspot.repositories.ChapterRepository;
import gr.unipi.javaspot.repositories.ExamRepository;
import gr.unipi.javaspot.repositories.UserRepository;
import gr.unipi.javaspot.services.ExaminerService;
import gr.unipi.javaspot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static gr.unipi.javaspot.security.SecurityConfig.getUserDetails;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final ChapterRepository chapterRepository;
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
    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }

        return userOptional.get();
    }

    @Override
    public void unlockChapters(String username, List<Chapter> chapters) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty())
            throw new UsernameNotFoundException("User with username " + username + " not found.");

        User user = userOptional.get();

        chapters.forEach(chapter -> {
            if (!userRepository.existsByUnlockedChaptersContainsAndId(chapter, user.getId())) {
                user.addUnlockedChapters(List.of(chapter));

                // Create an exam for the unlocked chapter
                examRepository.save(new Exam(user, chapter, ExamStatus.NOT_STARTED));
            }
        });

        userRepository.save(user);
    }

    public void updateSkillLevel(SkillLevelQuestion[] questions) {
        Optional<User> userOptional = userRepository.findByUsername(getUserDetails().getUsername());

        if (userOptional.isEmpty())
            throw new UsernameNotFoundException("User with username " + getUserDetails().getUsername() + " not found.");

        User user = userOptional.get();

        // Set skill level based on the answers to the questions
        if (examinerService.analyzeAnswer(questions[1].getText(), questions[1].getAnswer()).isCorrect()) {
            user.setSkillLevel(SkillLevel.INTERMEDIATE);
        } else if (examinerService.analyzeAnswer(questions[0].getText(), questions[0].getAnswer()).isCorrect()) {
            user.setSkillLevel(SkillLevel.BEGINNER);
        } else {
            user.setSkillLevel(SkillLevel.INTRO);
        }
        userRepository.save(user);

        // Unlock the chapters based on the skill level
        List<Chapter> chapters = chapterRepository.findChaptersBySkillLevelLessThanEqual(user.getSkillLevel());
        unlockChapters(user.getUsername(), chapters);

        // Update UserDetails with the new skill level
        getUserDetails().setSkillLevel(user.getSkillLevel());
    }

}
