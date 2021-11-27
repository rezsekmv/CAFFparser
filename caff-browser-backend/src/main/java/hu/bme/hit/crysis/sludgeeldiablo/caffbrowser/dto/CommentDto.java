package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private Long imageId;
    private String userDisplayName;
    private String content;
    private Boolean modifiable;
}
