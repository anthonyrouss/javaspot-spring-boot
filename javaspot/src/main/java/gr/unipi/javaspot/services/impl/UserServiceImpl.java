package gr.unipi.javaspot.services.impl;

import gr.unipi.javaspot.dtos.UserDto;
import gr.unipi.javaspot.enums.UserRole;
import gr.unipi.javaspot.mappers.UserDtoMapper;
import gr.unipi.javaspot.models.Role;
import gr.unipi.javaspot.models.User;
import gr.unipi.javaspot.repositories.UserRepository;
import gr.unipi.javaspot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

}
