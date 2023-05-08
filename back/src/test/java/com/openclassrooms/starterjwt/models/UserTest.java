package com.openclassrooms.starterjwt.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName("Doe")
                .setFirstName("John")
                .setPassword("secret")
                .setAdmin(true)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());
    }

    @Test
    public void testGetters() {
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getPassword()).isEqualTo("secret");
        assertThat(user.isAdmin()).isTrue();
        assertThat(user.getCreatedAt()).isNotNull();
        assertThat(user.getUpdatedAt()).isNotNull();
    }

    @Test
    public void testSetters() {
        user.setId(2L);
        assertThat(user.getId()).isEqualTo(2L);

        user.setEmail("jane.doe@example.com");
        assertThat(user.getEmail()).isEqualTo("jane.doe@example.com");

        user.setLastName("Doe2");
        assertThat(user.getLastName()).isEqualTo("Doe2");

        user.setFirstName("Jane");
        assertThat(user.getFirstName()).isEqualTo("Jane");

        user.setPassword("new_secret");
        assertThat(user.getPassword()).isEqualTo("new_secret");

        user.setAdmin(false);
        assertThat(user.isAdmin()).isFalse();

        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        assertThat(user.getCreatedAt()).isEqualTo(now);

        user.setUpdatedAt(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName("Doe")
                .setFirstName("John")
                .setPassword("secret")
                .setAdmin(true)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        User user2 = new User()
                .setId(1L)
                .setEmail("john.doe@example.com")
                .setLastName("Doe")
                .setFirstName("John")
                .setPassword("secret")
                .setAdmin(true)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        assertThat(user1).isEqualTo(user2);
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
    }


    @Test
    public void testBuildUserWithRequiredFields() {
        // Given
        String email = "john.doe@example.com";
        String lastName = "Doe";
        String firstName = "John";
        String password = "secret";
        boolean admin = false;

        // When
        User user = User.builder()
                .email(email)
                .lastName(lastName)
                .firstName(firstName)
                .password(password)
                .admin(admin)
                .build();

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.isAdmin()).isEqualTo(admin);
        assertThat(user.getCreatedAt()).isNull();
        assertThat(user.getUpdatedAt()).isNull();
    }

    @Test
    public void testBuildUserWithAllFields() {
        // Given
        Long id = 1L;
        String email = "jane.doe@example.com";
        String lastName = "Doe";
        String firstName = "Jane";
        String password = "top_secret";
        boolean admin = true;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(1);
        LocalDateTime updatedAt = LocalDateTime.now();

        // When
        User user = User.builder()
                .id(id)
                .email(email)
                .lastName(lastName)
                .firstName(firstName)
                .password(password)
                .admin(admin)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // Then
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getLastName()).isEqualTo(lastName);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.isAdmin()).isEqualTo(admin);
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
        assertThat(user.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    public void testToString() {
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("password")
                .admin(true)
                .build();

        String expectedString = "User(id=1, email=test@example.com, lastName=Doe, firstName=John, password=password, admin=true, createdAt=null, updatedAt=null)";
        assertEquals(expectedString, user.toString());
    }
}
