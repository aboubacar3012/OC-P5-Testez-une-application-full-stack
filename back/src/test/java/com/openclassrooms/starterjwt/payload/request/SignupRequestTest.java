package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SignupRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void signupRequest_WithValidData_ShouldBeValid() {
        // Arrange
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password123";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);
        signupRequest.setPassword(password);

        // Act
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Assert
        assertThat(violations).isEmpty();
    }

    @Test
    void signupRequest_WithInvalidEmail_ShouldBeInvalid() {
        // Arrange
        String email = "invalid-email";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password123";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);
        signupRequest.setPassword(password);

        // Act
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Assert
        assertThat(violations).hasSize(1);
        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("email");
    }

    @Test
    void signupRequest_WithInvalidFirstName_ShouldBeInvalid() {
        // Arrange
        String email = "test@example.com";
        String firstName = "J";
        String lastName = "Doe";
        String password = "password123";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);
        signupRequest.setPassword(password);

        // Act
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Assert
        assertThat(violations).hasSize(1);
        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
    }

    @Test
    void signupRequest_WithInvalidLastName_ShouldBeInvalid() {
        // Arrange
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "D";
        String password = "password123";

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(email);
        signupRequest.setFirstName(firstName);
        signupRequest.setLastName(lastName);
        signupRequest.setPassword(password);

        // Act
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Assert
        assertThat(violations).hasSize(1);
        ConstraintViolation<SignupRequest> violation = violations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("lastName");
    }

}