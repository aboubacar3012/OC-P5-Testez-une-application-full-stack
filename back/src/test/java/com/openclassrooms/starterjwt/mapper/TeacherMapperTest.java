package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TeacherMapperTest {

    private TeacherMapper teacherMapper;

    @BeforeEach
    public void setUp() {
        teacherMapper = Mappers.getMapper(TeacherMapper.class);
    }

    @Test
    public void testMapTeacherToTeacherDto() {
        // Given
        Teacher teacher = new Teacher()
                .setId(1L)
                .setLastName("Doe")
                .setFirstName("John")
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        // When
        TeacherDto teacherDto = teacherMapper.toDto(teacher);

        // Then
        assertThat(teacherDto).isNotNull();
        assertThat(teacherDto.getId()).isEqualTo(1L);
        assertThat(teacherDto.getLastName()).isEqualTo("Doe");
        assertThat(teacherDto.getFirstName()).isEqualTo("John");
        assertThat(teacherDto.getCreatedAt()).isEqualTo(teacher.getCreatedAt());
        assertThat(teacherDto.getUpdatedAt()).isEqualTo(teacher.getUpdatedAt());
    }
}
