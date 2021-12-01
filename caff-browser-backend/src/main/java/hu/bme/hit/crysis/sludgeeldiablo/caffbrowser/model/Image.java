package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.converter.SetConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // CAFF meta data

    @Column(name = "IMAGE_DATE")
    private LocalDateTime date;

    @Column(name = "IMAGE_CREDIT")
    private String credit;

    @Column(name = "IMAGE_CAPTIONS")
    @Convert(converter = SetConverter.class)
    private Set<String> captions = new HashSet<>();

    @Column(name = "IMAGE_TAGS")
    @Convert(converter = SetConverter.class)
    private Set<String> tags = new HashSet<>();

    @Column(name = "IMAGE_WIDTH")
    private Long width;

    @Column(name = "IMAGE_HEIGHT")
    private Long height;
}
