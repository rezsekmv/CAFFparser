package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.enums.RoleName;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private Set<RoleName> roles;
}
