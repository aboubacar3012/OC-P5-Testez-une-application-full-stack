package com.openclassrooms.starterjwt.mapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class SessionMapperTest {

    private SessionMapper sessionMapper;

    @Mock
    private TeacherService teacherService;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.initMocks(this);
        sessionMapper = new SessionMapperImpl();
    }

    @Test
    @Disabled
    public void testToEntity() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Test session");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Collections.singletonList(2L));

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherService.findById(1L)).thenReturn(teacher);

        User user = new User();
        user.setId(2L);
        when(userService.findById(2L)).thenReturn(user);

        Session session = sessionMapper.toEntity(sessionDto);

        assertThat(session.getDescription()).isEqualTo(sessionDto.getDescription());
        assertThat(session.getTeacher()).isEqualTo(teacher);
        assertThat(session.getUsers()).containsOnly(user);
    }

    @Test
    public void testToDto() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user = new User();
        user.setId(2L);

        Session session = new Session();
        session.setDescription("Test session");
        session.setTeacher(teacher);
        session.setUsers(Collections.singletonList(user));

        SessionDto sessionDto = sessionMapper.toDto(session);

        assertThat(sessionDto.getDescription()).isEqualTo(session.getDescription());
        assertThat(sessionDto.getTeacher_id()).isEqualTo(session.getTeacher().getId());
        assertThat(sessionDto.getUsers()).containsOnly(user.getId());
    }

    @Test
    public void testToDto_withCompleteSession() {
        // Given
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        Session session = new Session();
        session.setDescription("Test session");
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user1, user2));

        // When
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Then
        assertNotNull(sessionDto);
        assertEquals("Test session", sessionDto.getDescription());
        assertEquals(1L, sessionDto.getTeacher_id().longValue());
        assertNotNull(sessionDto.getUsers());
        assertEquals(2, sessionDto.getUsers().size());
        assertTrue(sessionDto.getUsers().contains(1L));
        assertTrue(sessionDto.getUsers().contains(2L));
    }


//    @Test
//    @Disabled
//    public void toEntity_withSessionDto_shouldMapToSessionEntity() {
//        // Arrange
//        SessionDto sessionDto = new SessionDto()
//                .setDescription("Test session description")
//                .setTeacher_id(1L)
//                .setUsers(Arrays.asList(1L, 2L));
//        when(teacherService.findById(sessionDto.getTeacher_id())).thenReturn(new Teacher().setId(1L));
//        when(userService.findById(1L)).thenReturn(new User().setId(1L));
//        when(userService.findById(2L)).thenReturn(new User().setId(2L));
//
//        // Act
//        Session session = sessionMapper.toEntity(sessionDto);
//
//        // Assert
//        assertThat(session.getDescription()).isEqualTo("Test session description");
//        assertThat(session.getTeacher()).isNotNull();
//        assertThat(session.getTeacher().getId()).isEqualTo(1L);
//        assertThat(session.getUsers()).hasSize(2);
//        assertThat(session.getUsers().get(0).getId()).isEqualTo(1L);
//        assertThat(session.getUsers().get(1).getId()).isEqualTo(2L);
//    }
}
