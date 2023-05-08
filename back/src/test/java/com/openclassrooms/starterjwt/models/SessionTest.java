package com.openclassrooms.starterjwt.models;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;

public class SessionTest {

    @Test
    public void testSession_toString_hashCode_equals() {
        // Création de deux instances de la classe Session
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        Session session1 = new Session()
                .setId(1L)
                .setName("Mathematics")
                .setDate(new Date())
                .setDescription("This is a mathematics course")
                .setTeacher(teacher)
                .setUsers(new ArrayList<>())
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());
        Session session2 = new Session()
                .setId(2L)
                .setName("French")
                .setDate(new Date())
                .setDescription("This is a French course")
                .setTeacher(teacher)
                .setUsers(new ArrayList<>())
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        // Vérification de la méthode toString()
        System.out.println(session1.toString());
        System.out.println(session2.toString());

        // Vérification de la méthode hashCode()
        assertNotEquals(session1.hashCode(), session2.hashCode());

        // Vérification de la méthode equals()
        assertNotEquals(session1, session2);
        assertEquals(session1, session1);
        assertNotEquals(session1, null);
        assertNotEquals(session1, new Object());
    }

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

    @Test
    public void testSessionBuilder() {
        Teacher teacher = mock(Teacher.class);
        List<User> users = new ArrayList<>();

        Session session = Session.builder()
                .id(1L)
                .name("Java Programming")
                .date(new Date())
                .description("Learn Java Programming")
                .teacher(teacher)
                .users(users)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertThat(session.getId()).isEqualTo(1L);
        assertThat(session.getName()).isEqualTo("Java Programming");
        assertThat(session.getDate()).isInstanceOf(Date.class);
        assertThat(session.getDescription()).isEqualTo("Learn Java Programming");
        assertThat(session.getTeacher()).isEqualTo(teacher);
        assertThat(session.getUsers()).isEqualTo(users);
        assertThat(session.getCreatedAt()).isInstanceOf(LocalDateTime.class);
        assertThat(session.getUpdatedAt()).isInstanceOf(LocalDateTime.class);
    }


}
