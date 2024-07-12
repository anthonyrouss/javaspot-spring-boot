package gr.unipi.javaspot.mappers;

import gr.unipi.javaspot.dtos.UserDto;
import gr.unipi.javaspot.models.Role;
import gr.unipi.javaspot.models.User;

public class UserDtoMapper {

    public static User mapToUser(UserDto userDto, Role role) {
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(role)
                .enabled(true)
                .build();
    }

}
