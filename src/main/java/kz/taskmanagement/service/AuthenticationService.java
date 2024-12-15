package kz.taskmanagement.service;

import kz.taskmanagement.dto.JwtAuthenticationResponse;
import kz.taskmanagement.dto.SecurityUser;
import kz.taskmanagement.dto.SignInRequest;
import kz.taskmanagement.dto.SignUpRequest;
import kz.taskmanagement.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationResponse signUp(SignUpRequest signUpRequest) {
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(signUpRequest.getPassword())
                .build();

        userService.save(user);

        String token = jwtTokenService.generateToken(new SecurityUser(user));
        return new JwtAuthenticationResponse(token);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(),
                signInRequest.getPassword()
        ));

        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(signInRequest.getEmail());
        String token = jwtTokenService.generateToken(securityUser);

        return new JwtAuthenticationResponse(token);
    }
}
