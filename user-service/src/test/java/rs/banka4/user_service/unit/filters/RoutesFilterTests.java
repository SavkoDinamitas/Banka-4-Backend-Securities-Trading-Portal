package rs.banka4.user_service.unit.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import jakarta.servlet.FilterChain;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import rs.banka4.user_service.config.filters.InvalidRouteFilter;
import rs.banka4.user_service.config.filters.JwtAuthenticationFilter;
import rs.banka4.user_service.domain.user.client.db.Client;
import rs.banka4.user_service.exceptions.RouteNotFound;
import rs.banka4.user_service.exceptions.jwt.NoJwtProvided;
import rs.banka4.user_service.repositories.ClientRepository;
import rs.banka4.user_service.repositories.EmployeeRepository;
import rs.banka4.user_service.utils.JwtUtil;

public class RoutesFilterTests {

    @Mock
    private FilterChain filterChain;

    @Mock
    private HandlerMapping handlerMapping;

    @Mock
    private JwtUtil jwtUtil;

    /* TODO(arsen): part of the kludge in JwtAuthenticationFilter. */
    @Mock
    private ClientRepository clientRepository;

    /* TODO(arsen): part of the kludge in JwtAuthenticationFilter. */
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private InvalidRouteFilter invalidRouteFilter;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @Test
    public void testValidRouteWithoutAuthHeader() throws Exception {
        // Arrange
        request.setRequestURI("/account/");
        HandlerExecutionChain handlerExecutionChain = new HandlerExecutionChain(new Object());
        when(handlerMapping.getHandler(request)).thenReturn(handlerExecutionChain);

        // Act & Assert
        assertThrows(NoJwtProvided.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    public void testValidRouteWithJwt() throws Exception {
        // Arrange
        request.setRequestURI("/account/");
        String token = "token";
        request.addHeader("Authorization", "Bearer " + token);
        HandlerExecutionChain handlerExecutionChain = new HandlerExecutionChain(new Object());
        when(handlerMapping.getHandler(request)).thenReturn(handlerExecutionChain);
        when(jwtUtil.extractUsername(token)).thenReturn("username");
        when(jwtUtil.extractRole(token)).thenReturn("client");
        when(jwtUtil.validateToken(token, "username")).thenReturn(true);
        when(clientRepository.findById(any())).thenReturn(
            Optional.of(
                Client.builder()
                    .build()
            )
        );

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testInvalidRoute() throws Exception {
        // Arrange
        request.setRequestURI("/invalid-route");
        when(handlerMapping.getHandler(request)).thenReturn(null);

        // Act & Assert
        assertThrows(RouteNotFound.class, () -> {
            invalidRouteFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    public void testInvalidRouteThenJwtFilter() throws Exception {
        // Arrange
        request.setRequestURI("/invalid-route");
        String token = "token";
        request.addHeader("Authorization", "Bearer " + token);
        when(handlerMapping.getHandler(request)).thenReturn(null);

        // Act & Assert
        assertThrows(RouteNotFound.class, () -> {
            invalidRouteFilter.doFilterInternal(request, response, filterChain);
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    public void testValidRouteWithInvalidJwt() throws Exception {
        // Arrange
        request.setRequestURI("/account/");
        HandlerExecutionChain handlerExecutionChain = new HandlerExecutionChain(new Object());
        when(handlerMapping.getHandler(request)).thenReturn(handlerExecutionChain);

        // Act & Assert
        assertThrows(NoJwtProvided.class, () -> {
            invalidRouteFilter.doFilterInternal(request, response, filterChain);
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    public void testInvalidRouteWithMissingJwt() throws Exception {
        // Arrange
        request.setRequestURI("/invalid-route");
        when(handlerMapping.getHandler(request)).thenReturn(null);

        // Act & Assert
        assertThrows(RouteNotFound.class, () -> {
            invalidRouteFilter.doFilterInternal(request, response, filterChain);
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });
    }
}
