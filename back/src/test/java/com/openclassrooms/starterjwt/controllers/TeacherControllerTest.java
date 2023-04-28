package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherControllerTest {

    @Mock
    private TeacherMapper teacherMapper;
    @Mock
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    private TeacherController teacherController;

    @BeforeEach
    public void setUp(){
        teacherController = new TeacherController(teacherService, teacherMapper);
    }

    @AfterEach
    public void undefUserControllerTest(){
        teacherController = null;
    }

    @Nested
    @DisplayName("Test find by Id suites")
    public class TestFindById{
        @Test
        public void findById_shouldReturnNotFound_whenTeacherNotFound() {
            // Given
            when(teacherService.findById(anyLong())).thenReturn(null);

            // When
            ResponseEntity<?> responseEntity = teacherController.findById("1");

            // Then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            verify(teacherService, times(1)).findById(1L);
        }

        @Test
        public void findById_shouldReturnBadRequest_whenIdIsNotValid(){
            // When
            ResponseEntity<?> responseEntity = teacherController.findById("invalid-id");

            // Then
            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        }

        @Test
        public void findById_shouldReturnTeacher_whenFound(){
            // GIVEN
            Long teacherId = 6L;
            Teacher teacher = new Teacher();
            teacher.setId(teacherId);
            teacher.setFirstName("John");
            teacher.setLastName("Doe");

            when(teacherService.findById(anyLong())).thenReturn(teacher);

            // WHEN
            ResponseEntity<?> responseEntity = teacherController.findById("6");

            System.out.println(responseEntity);
            // THEN
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            verify(teacherService, times(1)).findById(6L);
//            assertEquals(teacher.getId(), ((Teacher) responseEntity.getBody()).getId());
//            assertEquals(teacher.getFirstName(), ((Teacher) responseEntity.getBody()).getFirstName());
//            assertEquals(teacher.getLastName(), ((Teacher) responseEntity.getBody()).getLastName());
        }
    }

    @Test
    public void testFindAll(){
        // GIVEN
        teacherService = new TeacherService(teacherRepository);

        List<Teacher> teachers = new ArrayList<>();
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("John");
        teacher1.setLastName("Doe");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Doe");

        teachers.add(teacher1);
        teachers.add(teacher2);

        when(teacherRepository.findAll()).thenReturn(teachers);

        // WHEN
        List<Teacher> result = teacherService.findAll();

        // THEN
        assertThat(result.size()).isEqualTo(teachers.size());
        assertThat(result).isEqualTo(teachers);
        verify(teacherRepository, times(1)).findAll();
        for (int i = 0; i < teachers.size(); i++) {
            assertEquals(teachers.get(i).getId(), result.get(i).getId());
            assertEquals(teachers.get(i).getFirstName(), result.get(i).getFirstName());
            assertEquals(teachers.get(i).getLastName(), result.get(i).getLastName());
        }

    }


}
