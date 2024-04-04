package com.tokioschool.flight.app.security;

import com.tokioschool.flight.app.dto.UserDTO;
import com.tokioschool.flight.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Pair<UserDTO,String> userAndPassword= userService
                .findUserAndPasswordByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User with username: %s not found".formatted(username)));
        return toUserDetails(userAndPassword.getLeft(), userAndPassword.getRight());
    }

    private UserDetails toUserDetails(UserDTO userDTO, String password){
        List<SimpleGrantedAuthority> authorities= userDTO.getRoles()
                .stream().map(SimpleGrantedAuthority::new).toList();
        return new User(userDTO.getEmail(), password, authorities);
    }
}
