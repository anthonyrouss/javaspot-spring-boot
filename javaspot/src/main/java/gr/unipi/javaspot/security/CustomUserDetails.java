package gr.unipi.javaspot.security;

import gr.unipi.javaspot.enums.SkillLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final Integer id;

    @Setter
    private SkillLevel skillLevel;

    public CustomUserDetails(String username,
                             String password,
                             Collection<? extends GrantedAuthority> authorities,
                             Integer id,
                             SkillLevel skillLevel) {
        super(username, password, authorities);
        this.id = id;
        this.skillLevel = skillLevel;
    }
}
