package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtUtilsTest {

    @Test
    public void generateJwtToken_ReturnsValidJwtToken() {
        // Given
        JwtUtils jwtUtils = new JwtUtils();
        jwtUtils.setJwtSecret("testJwtSecret");
        jwtUtils.setJwtExpirationMs(3600000);

        Authentication authentication = mock(Authentication.class);
        UserDetailsImpl userDetails = new UserDetailsImpl(3L, "testPassword", "Test", "Test", true, "password");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // When
        String jwtToken = jwtUtils.generateJwtToken(authentication);

        // Then
        assertThat(jwtToken).isNotEmpty();
    }

    @Test
    public void validateJwtToken_InvalidToken_ReturnsFalse() {
        // Given
        JwtUtils jwtUtils = new JwtUtils();
        jwtUtils.setJwtSecret("testJwtSecret");

        String jwtToken = "invalidJwtToken";

        // When
        boolean isValid = jwtUtils.validateJwtToken(jwtToken);

        // Then
        assertThat(isValid).isFalse();
    }
}
