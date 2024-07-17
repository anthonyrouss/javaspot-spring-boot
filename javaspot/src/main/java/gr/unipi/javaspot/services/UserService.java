package gr.unipi.javaspot.services;

import gr.unipi.javaspot.dtos.UserDto;

public interface UserService {

    void signUpAsStudent(UserDto userDto);

    boolean usernameExists(String username);

}
