package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_USERNAME")
    private String username;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "userId")
    private Set<Role> roles = new HashSet<>();

    public String getDisplayName() {
        return getName() != null ? getName() : getUsername();
    }
}
