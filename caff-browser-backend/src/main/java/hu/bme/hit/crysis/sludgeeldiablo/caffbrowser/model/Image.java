package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "IMAGE")
@Getter
@Setter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long id;

    @Column(name = "IMAGE_UUID")
    private String uuid;

    @Column(name = "IMAGE_CREATOR_ID")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IMAGE_CREATOR_ID", insertable = false, updatable = false)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "imageId")
    private List<Comment> comments = new ArrayList<>();
}
