package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

import java.util.List;

@Data
public class CIFF {

    String magic;
    Long header_size;
    Long content_size;
    Long width;
    Long height;
    String caption;
    List<String> tags;
}
