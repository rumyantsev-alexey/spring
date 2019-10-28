package ru.job4j.cars.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.job4j.cars.models.UsersEntity;
import ru.job4j.cars.services.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    @Autowired
    private UserService users;

    @Autowired
    private PasswordEncoder crypt;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        UsersEntity user = users.findByName(login);
        if (user == null) {
            throw new UsernameNotFoundException("Login not found");
        }
        String pass = authentication.getCredentials().toString();
        if (!crypt.matches(pass, user.getPassword())) {
            throw new BadCredentialsException("Password not correct");
        }
        List<GrantedAuthority> auths = new ArrayList<>();
        return new UsernamePasswordAuthenticationToken(user.getName(), null, auths);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
