package gr.unipi.javaspot.services;

import gr.unipi.javaspot.dtos.SkillLevelQuestion;
import gr.unipi.javaspot.dtos.UserDto;
import gr.unipi.javaspot.models.Chapter;
import gr.unipi.javaspot.models.User;

import java.util.List;

public interface UserService {

    void signUpAsStudent(UserDto userDto);

    boolean usernameExists(String username);

    User findByUsername(String username);

    void unlockChapters(String username, List<Chapter> chapters);

    void updateSkillLevel(SkillLevelQuestion[] questions);

}
