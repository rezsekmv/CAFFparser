package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ROLE")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long id;

    @Column(name = "ROLE_USER_ID")
    private Long userId;

    @Column(name = "ROLE_NAME")
    @Enumerated(value = EnumType.STRING)
    private RoleName name;
}
