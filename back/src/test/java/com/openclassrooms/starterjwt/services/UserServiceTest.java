package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp(){
        userService = new UserService(userRepository);
    }

    @AfterEach
    public void undefUserService(){
        userService = null;
    }

    @Test
    @DisplayName("Test delete user by ID")
    public void testDeleteUser(){
        // GIVEN
        Long id = 5L;

        // WHEN
        userService.delete(id);

        // THEN
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Test find user by ID")
    public void testFindById(){
        // GIVEN
        Long id = 4L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // WHEN
        User result = userService.findById(id);

        // THEN
        verify(userRepository, times(1)).findById(id);
        assertThat(result).isEqualTo(user);

    }

}
