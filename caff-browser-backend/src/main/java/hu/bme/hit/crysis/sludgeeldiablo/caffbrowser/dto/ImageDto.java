package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

import java.util.List;

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
}
