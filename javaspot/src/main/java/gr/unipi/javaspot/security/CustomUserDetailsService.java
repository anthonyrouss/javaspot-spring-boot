package gr.unipi.javaspot.security;

import gr.unipi.javaspot.models.Role;
import gr.unipi.javaspot.models.User;
import gr.unipi.javaspot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found.");
        }

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                mapRoleToAuthority(user.getRole()),
                user.getId(),
                user.getSkillLevel()
        );
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Role role) {
        return Stream.of(role).map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

}
