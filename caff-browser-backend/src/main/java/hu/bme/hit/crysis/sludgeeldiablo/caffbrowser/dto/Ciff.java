package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

import java.util.List;

@Data
public class Ciff {

    String magic;
    long header_size;
    long content_size;
    long width;
    long height;
    String caption;
    List<String> tags;
}
