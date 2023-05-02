package com.openclassrooms.starterjwt.models;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

public class SessionTest {

    @Test
    void sessionConstructor_ShouldCreateSessionWithGivenValues() {
        // Arrange
        Long id = 1L;
        String name = "Test Session";
        Date date = new Date();
        String description = "Test Description";
        Teacher teacher = new Teacher();
        List<User> users = new ArrayList<>();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // Act
        Session session = new Session();
        session.setId(id);
        session.setName(name);
        session.setDate(date);
        session.setDescription(description);
        session.setTeacher(teacher);
        session.setUsers(users);
        session.setCreatedAt(createdAt);
        session.setUpdatedAt(updatedAt);

        // Assert
        assertThat(session.getId()).isEqualTo(id);
        assertThat(session.getName()).isEqualTo(name);
        assertThat(session.getDate()).isEqualTo(date);
        assertThat(session.getDescription()).isEqualTo(description);
        assertThat(session.getTeacher()).isEqualTo(teacher);
        assertThat(session.getUsers()).isEqualTo(users);
        assertThat(session.getCreatedAt()).isEqualTo(createdAt);
        assertThat(session.getUpdatedAt()).isEqualTo(updatedAt);
    }
}
