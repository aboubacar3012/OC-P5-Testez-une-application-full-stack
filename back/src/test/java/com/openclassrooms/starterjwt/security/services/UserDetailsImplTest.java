package com.openclassrooms.starterjwt.security.services;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    @Test
    void testEquals() {
        UserDetailsImpl user1 = UserDetailsImpl.builder()
                .id(1L)
                .username("user1")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("password1")
                .build();

        UserDetailsImpl user2 = UserDetailsImpl.builder()
                .id(1L)
                .username("user2")
                .firstName("Jane")
                .lastName("Doe")
                .admin(true)
                .password("password2")
                .build();

        UserDetailsImpl user3 = UserDetailsImpl.builder()
                .id(2L)
                .username("user3")
                .firstName("Alice")
                .lastName("Smith")
                .admin(false)
                .password("password3")
                .build();

        assertTrue(user1.equals(user2));
        assertFalse(user1.equals(user3));
    }

    @Test
    void toString_shouldReturnExpectedString() {
        UserDetailsImpl userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("user")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("password")
                .build();
        String expectedString = "UserDetailsImpl(id=1, username=user, firstName=John, lastName=Doe, admin=false, password=password)";
        assertEquals(expectedString, userDetails.toString());
    }


}
