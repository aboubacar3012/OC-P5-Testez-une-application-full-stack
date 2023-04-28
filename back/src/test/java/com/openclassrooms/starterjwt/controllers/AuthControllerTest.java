package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    private AuthController authController;

    @BeforeEach
    public void SetUp(){
        authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils, userRepository);
    }

    @AfterEach
    public void cleanUp(){
        authController = null;
    }

    @Test
    public void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@example.com");
        loginRequest.setPassword("password");

        AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        JwtUtils jwtUtils = Mockito.mock(JwtUtils.class);
        UserDetailsImpl userDetails = Mockito.mock(UserDetailsImpl.class);

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(userDetails);
        Mockito.when(userDetails.getUsername()).thenReturn(loginRequest.getEmail());
        Mockito.when(jwtUtils.generateJwtToken(authentication)).thenReturn("jwtToken");

        AuthController authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils, userRepository);

        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("jwtToken", ((JwtResponse) response.getBody()).getToken());
//        System.out.println(((JwtResponse)response.getBody()).getToken());
    }


    @Test
    public void testRegisterUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("john.doe@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("passwordencoded");

        User user = new User(signupRequest.getEmail(), signupRequest.getLastName(),
                signupRequest.getFirstName(), passwordEncoder.encode(signupRequest.getPassword()), false);

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals("User registered successfully!", ((MessageResponse) responseEntity.getBody()).getMessage());

        verify(userRepository, times(1)).existsByEmail(signupRequest.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
