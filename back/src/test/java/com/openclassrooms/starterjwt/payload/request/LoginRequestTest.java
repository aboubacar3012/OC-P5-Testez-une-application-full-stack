package com.openclassrooms.starterjwt.payload.request;
import com.openclassrooms.starterjwt.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginRequestTest {

    @Test
    void loginRequestConstructor_WithValidData_ShouldCreateLoginRequest() {
        // Arrange
        String email = "test@example.com";
        String password = "password";

        // Act
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);

        // Assert
        assertThat(loginRequest.getEmail()).isEqualTo(email);
        assertThat(loginRequest.getPassword()).isEqualTo(password);
    }

}
