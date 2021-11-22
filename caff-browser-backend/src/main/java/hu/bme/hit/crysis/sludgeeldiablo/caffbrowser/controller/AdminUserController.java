package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.controller;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
@Tag(name = "admin")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Összes felhasználó megtekintése")
    public ResponseEntity<List<UserDto>> getAllUser() {
        log.trace("UserController : getAllUser");
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Felhasználó megtekintése azonosító alapján")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        log.trace("UserController : getUser, id=[{}]", id);
        return ResponseEntity.ok(userService.get(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Felhasználó módosítása azonosító alapján")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        log.trace("UserController : getUser, id=[{}], userDto=[{}]", id, userDto);
        return ResponseEntity.ok(userService.update(id, userDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Felhasználó eltávolítása azonosító alapján")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.trace("UserController : getUser, id=[{}]", id);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
