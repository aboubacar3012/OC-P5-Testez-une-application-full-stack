package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    private SessionService sessionService;

    @BeforeEach
    public void setup(){
        sessionService = new SessionService(sessionRepository,userRepository);
    }

    @AfterEach
    public void undefSessionService(){
        sessionService = null ;
    }

    @Test
    @DisplayName("Test create session")
    public void testCreateSession(){
        // GIVEN
        Session session = new Session();
        session.setId(1L);
        session.setName("Test session");

        when(sessionService.create(session)).thenReturn(session);

        // WHEN
        Session savedSession = sessionService.create(session);

        // THEN
        verify(sessionRepository, times(1)).save(savedSession);
        assertThat(savedSession).isEqualTo(savedSession);
    }

    @Test
    @DisplayName("Test delete session by ID")
    public void testDeleteSession(){
        // GIVEN
        Long id = 6L;

        doNothing().when(sessionRepository).deleteById(id);

        // WHEN
        sessionService.delete(id);

        // THEN
        verify(sessionRepository,times(1)).deleteById(id);

    }

    @Test
    @DisplayName("Test get all sessions")
    public void testFindAllSessions(){
        // GIVEN
        List<Session> sessions = new ArrayList<>();

        Session session1 = new Session();
        session1.setId(1L);
        session1.setName("Test session 1");

        Session session2 = new Session();
        session2.setId(2L);
        session2.setName("Test session 2");

        sessions.add(session1);
        sessions.add(session2);

        when(sessionRepository.findAll()).thenReturn(sessions);

        // WHEN
        List<Session> foundSessions = sessionService.findAll();

        // THEN
        verify(sessionRepository, times(1)).findAll();
        assertThat(foundSessions).isEqualTo(sessions);
    }

    @Test
    @DisplayName("Test get session by ID")
    public void testGetSessionById(){
        // GIVEN
        Long sessionId = 2L;

        Session session = new Session();
        session.setId(sessionId);
        session.setName("Test session");

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // WHEN
        Session foundSession = sessionService.getById(sessionId);

        // THEN
        verify(sessionRepository, times(1)).findById(sessionId);
        assertThat(foundSession).isEqualTo(session);
    }

    @Test
    @DisplayName("Test update session by ID")
    public void testUpdateSession(){
        // GIVEN
        Long sessionId = 2L;

        Session session = new Session();
        session.setId(sessionId);
        session.setName("Test session");

        Session updatedSession = new Session();
        updatedSession.setId(sessionId);
        updatedSession.setName("Test session update");

        when(sessionRepository.save(session)).thenReturn(updatedSession);

        // WHEN
        Session updated = sessionService.update(sessionId, updatedSession);

        // WHEN
        verify(sessionRepository, times(1)).save(session);
        assertThat(updated).isEqualTo(updatedSession);
    }


    @Test
    @DisplayName("Test Participate")
    public void testParticipate(){
        // GIVEN
        Long sessionId = 1L;
        Long userId = 2L;

        Session session = new Session();
        session.setUsers(new ArrayList<>());

        User user = new User();
        user.setId(userId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // WHEN
        sessionService.participate(sessionId, userId);

        // THEN
        verify(sessionRepository, times(1)).findById(sessionId);
        verify(userRepository, times(1)).findById(userId);
        verify(sessionRepository, times(1)).save(session);

        assertThat(session.getUsers()).containsExactly(user);
    }


    @Test
    public void noLongerParticipate(){
        // GIVEN
        Long sessionId = 1L;
        Long userId = 2L;

        Session session = new Session();
        session.setId(sessionId);

        User user = new User();
        user.setId(userId);

        List<User> users = new ArrayList<>();
        users.add(user);

        session.setUsers(users);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // WHEN
        sessionService.noLongerParticipate(sessionId, userId);


        // THEN
        assertThat(session.getUsers()).isEmpty();
        verify(sessionRepository).save(session);

    }


}
