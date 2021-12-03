package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbError;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception.CbTokenException;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ObjectMapperFactory;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security.SecurityVariables.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class AuthorizationFilter extends OncePerRequestFilter {

    private static final String BASIC_MESSAGE_PATTERN = "{}: {}";

    @Value("${security.token-secret}")
    private String tokenSecret;

    @Value("${security.cors.enabled}")
    private Boolean corsEnabled;

    private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        if (isLogin(request) || isRefreshToken(request) || isPublic(request)) {
            corsOnPublic(response);
            filterChain.doFilter(request, response);
        } else {
            String header = request.getHeader(AUTHORIZATION);
            if (header == null) {
                filterChain.doFilter(request, response);
            } else {
                try {
                    String accessToken = getAccessToken(header);
                    DecodedJWT decodedJwt = getVerifier().verify(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(getAuthenticationToken(decodedJwt));
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    String description = "Access token error";
                    String message = e.getMessage();

                    log.error(BASIC_MESSAGE_PATTERN, description, message);
                    response.setStatus(UNAUTHORIZED.value());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    objectMapper.writeValue(response.getOutputStream(), new CbError(message, description));
                }
            }
        }
    }

    private void corsOnPublic(@NotNull HttpServletResponse response) {
        if (Boolean.TRUE.equals(corsEnabled)) {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }
    }

    private boolean isLogin(HttpServletRequest request) {
        return request.getServletPath().equals(LOGIN_URL);
    }

    private boolean isRefreshToken(HttpServletRequest request) {
        return request.getServletPath().equals(REFRESH_TOKEN_URL);
    }

    private boolean isPublic(HttpServletRequest request) {
        return request.getServletPath().startsWith(PUBLIC_URL.substring(0, PUBLIC_URL.length() - 3));
    }

    private String getAccessToken(String header) {
        validateIsBearerToken(header);
        return header.replaceFirst(BEARER_TOKEN_START, "");
    }

    private void validateIsBearerToken(String header) {
        if (!header.startsWith(BEARER_TOKEN_START)) {
            throw new CbTokenException("Authorization type must be Bearer token");
        }
    }

    private JWTVerifier getVerifier() {
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret.getBytes());
        return JWT.require(algorithm).build();
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(DecodedJWT decodedJwt) {
        String username = decodedJwt.getSubject();
        Set<SimpleGrantedAuthority> authorities = getAuthorities(decodedJwt);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private Set<SimpleGrantedAuthority> getAuthorities(DecodedJWT decodedJwt) {
        return getRolesList(decodedJwt).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private List<String> getRolesList(DecodedJWT decodedJwt) {
        return Arrays.asList(decodedJwt.getClaim(ROLES_PARAMETER).asArray(String.class));
    }
}
