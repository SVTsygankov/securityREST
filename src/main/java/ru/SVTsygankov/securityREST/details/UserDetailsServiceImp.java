package ru.SVTsygankov.securityREST.details;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.SVTsygankov.securityREST.service.UserServiceImp;
import ru.SVTsygankov.securityREST.model.User;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    private UserServiceImp userService;

    public UserDetailsServiceImp(UserServiceImp userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' не найден", username));
        }
        return user;
    }
}
