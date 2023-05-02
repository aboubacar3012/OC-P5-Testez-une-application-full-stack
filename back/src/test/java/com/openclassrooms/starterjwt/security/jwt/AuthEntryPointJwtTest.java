package com.openclassrooms.starterjwt.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthEntryPointJwtTest {

    private final AuthEntryPointJwt authEntryPointJwt = new AuthEntryPointJwt();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void commence_shouldReturnUnauthorizedError() throws Exception {
        // Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AuthenticationException authException = new AuthenticationException("Unauthorized error") {};

        // When
        authEntryPointJwt.commence(request, response, authException);

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

        String responseBody = response.getContentAsString();
        Map<String, Object> responseJson = objectMapper.readValue(responseBody, Map.class);

        assertThat(responseJson).containsOnlyKeys("status", "error", "message", "path");
        assertThat(responseJson.get("status")).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(responseJson.get("error")).isEqualTo("Unauthorized");
        assertThat(responseJson.get("message")).isEqualTo("Unauthorized error");
        assertThat(responseJson.get("path")).isEqualTo(request.getServletPath());
    }
}
