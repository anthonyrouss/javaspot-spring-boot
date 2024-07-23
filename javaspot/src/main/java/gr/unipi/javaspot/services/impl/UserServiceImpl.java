package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.dtos.UserDto;
import gr.unipi.javaspot.enums.ExamStatus;
import gr.unipi.javaspot.enums.UserRole;
import gr.unipi.javaspot.mappers.UserDtoMapper;
import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.models.Exam;
import gr.unipi.javaspot.models.Role;
import gr.unipi.javaspot.models.User;
import gr.unipi.javaspot.repositories.ExamRepository;
import gr.unipi.javaspot.repositories.UserRepository;
import gr.unipi.javaspot.services.ExamService;
import gr.unipi.javaspot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExamRepository examRepository;

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
        if (userOptional.isEmpty()) throw new UsernameNotFoundException("User with username " + username + " not found.");

        User user = userOptional.get();

        user.addUnlockedChapters(chapters);
        userRepository.save(user);

        // Create an exam for each chapter
        chapters.forEach(chapter -> examRepository.save(new Exam(user, chapter, ExamStatus.NOT_STARTED)));
    }

}
