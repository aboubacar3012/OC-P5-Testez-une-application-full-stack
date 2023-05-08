package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    @Mock
    private SessionMapper sessionMapper;
    @Mock
    private SessionService sessionService;

    private SessionController sessionController;

    private SessionDto sessionDto;

    private Session session;

    @BeforeEach
    public void setUp(){
        sessionController = new SessionController(sessionService, sessionMapper);

    }

    @AfterEach
    public void cleanUp(){
        sessionController = null;
    }

    @Nested
    @DisplayName("Test Find Session By ID Suites")
    public class TestFindSessionByID {
        @Test
        public void testFindById_ReturnsSession_WhenSessionExist(){
            // GIVEN
            Long id = 5L;
            Session session = new Session();
            session.setId(id);
            session.setName("Test Session");

            when(sessionService.getById(id)).thenReturn(session);

            SessionDto sessionDto = new SessionDto();
            when(sessionMapper.toDto(session)).thenReturn(sessionDto);

            // WHEN
            ResponseEntity<?> responseEntity = sessionController.findById(String.valueOf(id));

            // THEN
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(responseEntity.getBody()).isEqualTo(sessionDto);
            verify(sessionService, times(1)).getById(id);
            verify(sessionMapper, times(1)).toDto(session);
        }

        @Test
        public void testFindById_ReturnNotFound_WhenSessionDoesNotExist(){
            // GIVEN
            when(sessionService.getById(anyLong())).thenReturn(null);

            // WHEN
            ResponseEntity<?> responseEntity = sessionController.findById("5");

            // THEN
            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
            verify(sessionService, times(1)).getById(5L);
        }

        @Test
        public void testFindById_ReturnsBadRequest_WhenIdIsNotValid(){
            // GIVEN
            // WHEN
            ResponseEntity<?> responseEntity = sessionController.findById("abc");

            // THEN
            assertThat(responseEntity).isNotNull();
            assertThat(responseEntity.getStatusCodeValue()).isEqualTo(400);
        }
    }

    @Test
    public void testFinAll(){
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

        when(sessionService.findAll()).thenReturn(sessions);

        // WHEN
        List<Session> result = sessionService.findAll();

        // THEN
        assertThat(result.size()).isEqualTo(sessions.size());
        assertThat(result).isEqualTo(sessions);
        verify(sessionService, times(1)).findAll();
    }


}
