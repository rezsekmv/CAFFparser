package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "hello")
public class HelloController {

    @GetMapping("/hello")
    @Operation(summary = "Tesztvégpont bejelentkezés ellenőrzésére")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("This is a test message that you should see only if you logged in.");
    }

    @GetMapping("/mod/hello")
    @Operation(summary = "Tesztvégpont moderátori jog ellenőrzésére")
    public ResponseEntity<String> helloMod() {
        return ResponseEntity.ok("This is a test message that you should see only if you are a moderator.");
    }

    @GetMapping("/admin/hello")
    @Operation(summary = "Tesztvégpont adminisztrátori jog ellenőrzésére")
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("This is a test message that you should see only if you are an administrator.");
    }

    @GetMapping("/public/hello")
    @Operation(summary = "Tesztvégpont publikus végpontok ellenőrzésére")
    public ResponseEntity<String> helloPublic() {
        return ResponseEntity.ok("This is a public endpoint, you should see this every time.");
    }
}
