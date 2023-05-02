package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtResponseTest {

    @Test
    void jwtResponseConstructor_WithValidData_ShouldCreateJwtResponse() {
        // Arrange
        String accessToken = "access_token";
        Long id = 1L;
        String username = "user123";
        String firstName = "John";
        String lastName = "Doe";
        Boolean admin = true;

        // Act
        JwtResponse jwtResponse = new JwtResponse(accessToken, id, username, firstName, lastName, admin);

        // Assert
        assertThat(jwtResponse.getToken()).isEqualTo(accessToken);
        assertThat(jwtResponse.getType()).isEqualTo("Bearer");
        assertThat(jwtResponse.getId()).isEqualTo(id);
        assertThat(jwtResponse.getUsername()).isEqualTo(username);
        assertThat(jwtResponse.getFirstName()).isEqualTo(firstName);
        assertThat(jwtResponse.getLastName()).isEqualTo(lastName);
        assertThat(jwtResponse.getAdmin()).isEqualTo(admin);
    }

    @Test
    void jwtResponseConstructor_WithNullAccessToken_ShouldCreateJwtResponseWithNullToken() {
        // Arrange
        Long id = 1L;
        String username = "user123";
        String firstName = "John";
        String lastName = "Doe";
        Boolean admin = true;

        // Act
        JwtResponse jwtResponse = new JwtResponse(null, id, username, firstName, lastName, admin);

        // Assert
        assertThat(jwtResponse.getToken()).isNull();
        assertThat(jwtResponse.getType()).isEqualTo("Bearer");
        assertThat(jwtResponse.getId()).isEqualTo(id);
        assertThat(jwtResponse.getUsername()).isEqualTo(username);
        assertThat(jwtResponse.getFirstName()).isEqualTo(firstName);
        assertThat(jwtResponse.getLastName()).isEqualTo(lastName);
        assertThat(jwtResponse.getAdmin()).isEqualTo(admin);
    }
}
