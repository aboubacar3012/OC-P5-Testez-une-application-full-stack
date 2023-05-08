package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testTeacherBuilder() {
        // Given
        Long id = 1L;
        String lastName = "Doe";
        String firstName = "John";
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        // When
        Teacher teacher = Teacher.builder()
                .id(id)
                .lastName(lastName)
                .firstName(firstName)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // Then
        assertAll(
                () -> assertThat(teacher).isNotNull(),
                () -> assertThat(teacher.getId()).isEqualTo(id),
                () -> assertThat(teacher.getLastName()).isEqualTo(lastName),
                () -> assertThat(teacher.getFirstName()).isEqualTo(firstName),
                () -> assertThat(teacher.getCreatedAt()).isEqualTo(createdAt),
                () -> assertThat(teacher.getUpdatedAt()).isEqualTo(updatedAt)
        );
    }

    @Test
    public void testHashCode() {
        Teacher teacher1 = new Teacher().setId(1L).setFirstName("John").setLastName("Doe");
        Teacher teacher2 = new Teacher().setId(1L).setFirstName("John").setLastName("Doe");

        assertEquals(teacher1.hashCode(), teacher2.hashCode());
    }
}

