package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    private TeacherService teacherService;

    @BeforeEach
    public void setUp(){
        teacherService = new TeacherService(teacherRepository);
    }

    @AfterEach
    public void undefTeacherService(){
        teacherService = null;
    }

    @Test
    @DisplayName("Test find all teachers")
    public void testFindAll(){
        // GIVEN
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
        verify(teacherRepository, times(1)).findAll();
        assertThat(result).isNotNull().hasSize(2).containsExactly(teacher1, teacher2);
    }

    @Test
    @DisplayName("Test find teacher by ID")
    public void testFindById(){
        // GIVEN
        Long id = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));

        // WHEN
        Teacher result = teacherService.findById(id);

        // THEN
        verify(teacherRepository, times(1)).findById(id);
        assertThat(result).isNotNull().isEqualTo(teacher);
    }

}
