package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    private UserController userController;

    @BeforeEach
    public void setUp(){
        userController = new UserController(userService, userMapper);
    }

    @AfterEach
    public void undefUserControllerTest(){
        userController = null;
    }

    @Nested
    @DisplayName("Test Find User By ID Suites")
    public class TestFindUserByID {
        @Test
        public void testFindById_ReturnsUser_WhenUserExist(){
            // GIVEN
            Long id = 5L;
            User user = new User();
            user.setId(1L);
            user.setEmail("test@test.com");

            when(userService.findById(id)).thenReturn(user);

            // WHEN
            ResponseEntity<?> responseEntity = userController.findById("5");

            // THEN
            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
            assertThat(responseEntity.getBody()).isEqualTo(userMapper.toDto(user));
        }

        @Test
        public void testFindById_ReturnNotFound_WhenUserDoesNotExist(){
            // GIVEN
            when(userService.findById(anyLong())).thenReturn(null);

            // WHEN
            ResponseEntity<?> responseEntity = userController.findById("5");

            // THEN
            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
        }

        @Test
        public void testFindById_ReturnsBadRequest_WhenIdIsNotValid(){
            // GIVEN
            // WHEN
            ResponseEntity<?> responseEntity = userController.findById("abc");

            // THEN
            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
        }
    }

    @Nested
    @DisplayName("Test save user suites")
    public class TestSaveUser {

        @Test
        @DisplayName("Test save with valid user id")
        public void testSaveWithValidId(){
            // GIVEN
            Long userId = 3L;
            User user = new User();
            user.setId(userId);
            user.setEmail("test@test.com");

            UserDetails userDetails = Mockito.mock(UserDetails.class);
            when(userDetails.getUsername()).thenReturn("test@test.com");

            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null);

            SecurityContextHolder.getContext().setAuthentication(auth);

            when(userService.findById(userId)).thenReturn(user);

            // WHEN
            ResponseEntity<?> responseEntity = userController.save("3");

            // THEN
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            verify(userService, times(1)).delete(userId);
        }

//        @Test
//        public void testSaveWithInvalidId(){
//            // GIVEN
//            Long userId = 5L;
//            when(userService.findById(userId)).thenReturn(null);
//
//            // WHEN
//            ResponseEntity<?> responseEntity = userController.save("5");
//
//            // THEN
//            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.NOT_FOUND);
//            verify(userService, never()).delete(anyLong());
//        }
    }



}
