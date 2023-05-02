package com.openclassrooms.starterjwt.models;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    @Test
    void userConstructor_ShouldCreateUserWithGivenValues() {
        // Arrange
        Long id = 1L;
        String email = "test@example.com";
        String lastName = "Doe";
        String firstName = "John";
        String password = "password123";
        boolean admin = true;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // Act
        User user = new User(id, email, lastName, firstName, password, admin, createdAt, updatedAt);

        // Assert
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.isAdmin()).isEqualTo(admin);
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
        assertThat(user.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void userConstructor_WithRequiredFields_ShouldCreateUserWithDefaultValues() {
        // Arrange
        String email = "test@example.com";
        String lastName = "Doe";
        String firstName = "John";
        String password = "password123";

        // Act
        User user = new User();
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPassword(password);

        // Assert
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.isAdmin()).isFalse();
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getUpdatedAt()).isNull();
    }

}
