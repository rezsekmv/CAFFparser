package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.config;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.PasswordDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto.UserDto;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model.User;
import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.service.declaration.UserService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestConfiguration
public class TestConfig {

    // TODO: ContextUtilt ki kell mockolni (de az nem Bean, vigyázni!), az alábbi függvényt overrideoljuk

    private static User createUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("virtual@riot.hu");
        user.setName("Virtual Riot");
        user.setRoles(Collections.emptySet());
        user.setUsername("virtualriot");
        return user;
    }
}
