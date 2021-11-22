package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.controller;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.AuthService;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/refresh-token")
    @Operation(summary = "Access token frissítése")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        log.trace("AuthController : refreshToken, request=[{}], response=[{}]", request, response);
        authService.refreshToken(request, response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/public/sign-up")
    @Operation(summary = "Új felhasználó létrehozása")
    public ResponseEntity<UserDto> signUp(@RequestBody UserDto userDto) {
        log.trace("AuthController : signUp, userDto=[{}]", userDto);
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }
}
