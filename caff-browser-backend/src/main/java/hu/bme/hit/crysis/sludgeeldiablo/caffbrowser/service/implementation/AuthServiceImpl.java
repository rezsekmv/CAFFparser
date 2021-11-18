package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.User;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.AuthService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.security.SecurityVariables.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${security.token-secret}")
    private String tokenSecret;

    @Value("${security.access-token-expiration}")
    private Integer accessTokenExpiration;

    private final UserService userService;

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = request.getHeader(AUTHORIZATION);
        if (refreshToken == null) {
            throw new RuntimeException("Refresh token can not be null!");
        } else {
            try {
                try {
                    Algorithm algorithm = Algorithm.HMAC256(tokenSecret.getBytes());
                    DecodedJWT decodedJwt = JWT.require(algorithm).build().verify(refreshToken);
                    String username = decodedJwt.getSubject();
                    User user = userService.findByUsername(username);

                    Map<String, String> map = createTokenMap(request, user, algorithm);
                    valueIntoResponse(response, map);
                } catch (Exception e) {
                    log.error("Error at refreshing token: {}", e.getMessage());
                    response.setHeader(ERROR_PARAMETER, e.getMessage());
                    response.sendError(FORBIDDEN.value());
                }
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    private void valueIntoResponse(HttpServletResponse response, Map<String, String> map) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), map);
    }

    private HashMap<String, String> createTokenMap(HttpServletRequest request, User user, Algorithm algorithm) {
        return new HashMap<String, String>() {{
            put(ACCESS_TOKEN_PARAMETER, createAccessToken(request, user, algorithm));
        }};
    }

    private String createAccessToken(HttpServletRequest request, User user, Algorithm algorithm) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(ROLES_PARAMETER, user.getRoles().stream().map(r -> r.getName().toString()).collect(Collectors.toList()))
                .sign(algorithm);
    }
}