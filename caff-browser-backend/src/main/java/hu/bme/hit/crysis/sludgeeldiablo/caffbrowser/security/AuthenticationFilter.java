package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.util.ObjectMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security.SecurityVariables.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Value("${security.token-secret}")
    private String tokenSecret;

    @Value("${security.access-token-expiration}")
    private Integer accessTokenExpiration;

    @Value("${security.refresh-token-expiration}")
    private Integer refreshTokenExpiration;

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = ObjectMapperFactory.createObjectMapper();

    public AuthenticationFilter(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        return authenticationManager.authenticate(getToken(request));
    }

    private UsernamePasswordAuthenticationToken getToken(HttpServletRequest request) {
        return new UsernamePasswordAuthenticationToken(request.getParameter(USERNAME_PARAMETER), request.getParameter(PASSWORD_PARAMETER));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(tokenSecret.getBytes());
        Map<String, String> map = createTokenMap(request, user, algorithm);

        valueIntoResponse(response, map);
    }

    private void valueIntoResponse(HttpServletResponse response, Map<String, String> map) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), map);
    }

    private HashMap<String, String> createTokenMap(HttpServletRequest request, User user, Algorithm algorithm) {
        HashMap<String, String> map = new HashMap<>();
        map.put(ACCESS_TOKEN_PARAMETER, createAccessToken(request, user, algorithm));
        map.put(REFRESH_TOKEN_PARAMETER, createRefreshToken(request, user, algorithm));
        return map;
    }

    private String createAccessToken(HttpServletRequest request, User user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(ROLES_PARAMETER, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    private String createRefreshToken(HttpServletRequest request, User user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }
}
