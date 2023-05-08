package com.openclassrooms.starterjwt.mapper;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    public void testMapUserToUserDto() {
        // Given
        User user = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName("Doe")
                .setFirstName("John")
                .setPassword("secret")
                .setAdmin(true)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        // When
        UserDto userDto = userMapper.toDto(user);

        // Then
        assertThat(userDto).isNotNull();
        assertThat(userDto.getId()).isEqualTo(1L);
        assertThat(userDto.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(userDto.getLastName()).isEqualTo("Doe");
        assertThat(userDto.getFirstName()).isEqualTo("John");
        assertThat(userDto.getPassword()).isEqualTo("secret");
        assertThat(userDto.isAdmin()).isTrue();
        assertThat(userDto.getCreatedAt()).isEqualTo(user.getCreatedAt());
        assertThat(userDto.getUpdatedAt()).isEqualTo(user.getUpdatedAt());
    }
}
