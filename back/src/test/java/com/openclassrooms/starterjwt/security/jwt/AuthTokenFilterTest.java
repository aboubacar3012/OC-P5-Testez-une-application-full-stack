package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AuthTokenFilterTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @InjectMocks
    private AuthTokenFilter authTokenFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void doFilterInternal_ValidJwtToken_ShouldSetAuthenticationInSecurityContextHolder() throws ServletException, IOException {
        // Given
        String jwtToken = "validJwtToken";
        String username = "testUser";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + jwtToken);

        MockHttpServletResponse response = new MockHttpServletResponse();

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        when(jwtUtils.validateJwtToken(jwtToken)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(jwtToken)).thenReturn(username);

        FilterChain filterChain = mock(FilterChain.class);

        // When
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isInstanceOf(UsernamePasswordAuthenticationToken.class);

        verify(userDetailsService).loadUserByUsername(username);
        verify(jwtUtils).validateJwtToken(jwtToken);
        verify(jwtUtils).getUserNameFromJwtToken(jwtToken);

        verify(filterChain).doFilter(request, response);
    }
}