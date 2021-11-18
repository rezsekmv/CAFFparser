package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.controller;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        authService.refreshToken(request, response);
        return ResponseEntity.ok().build();
    }
}