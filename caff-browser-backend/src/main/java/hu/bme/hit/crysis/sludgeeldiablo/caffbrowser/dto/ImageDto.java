package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ImageDto {

    private Long id;
    private String userDisplayName;
    private String gifPath;
    private String caffPath;
    private List<CommentDto> comments;
    private Integer commentsSize;
    private Boolean commentable;
    private Boolean modifiable;

    // CAFF meta data
    private LocalDateTime date;
    private String credit;
    private Set<String> captions;
    private Set<String> tags;
    private Long height;
    private Long width;
}
