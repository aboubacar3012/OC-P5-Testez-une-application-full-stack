package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class TeacherTest {

    @Test
    void teacherConstructor_ShouldCreateTeacherWithGivenValues() {
        // Arrange
        Long id = 1L;
        String lastName = "Doe";
        String firstName = "John";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // Act
        Teacher teacher = new Teacher(id, lastName, firstName, createdAt, updatedAt);

        // Assert
        assertThat(teacher.getId()).isEqualTo(id);
        assertThat(teacher.getLastName()).isEqualTo(lastName);
        assertThat(teacher.getFirstName()).isEqualTo(firstName);
        assertThat(teacher.getCreatedAt()).isEqualTo(createdAt);
        assertThat(teacher.getUpdatedAt()).isEqualTo(updatedAt);
    }
}

