package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.model;

import hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.enums.RoleName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "COMMENT")
@Getter
@Setter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column(name = "COMMENT_USER_ID")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_USER_ID", insertable = false, updatable = false)
    private User user;

    @Column(name = "COMMENT_IMAGE_ID")
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMMENT_IMAGE_ID", insertable = false, updatable = false)
    private Image image;

    @Column(name = "COMMENT_CONTENT")
    private String content;
}
