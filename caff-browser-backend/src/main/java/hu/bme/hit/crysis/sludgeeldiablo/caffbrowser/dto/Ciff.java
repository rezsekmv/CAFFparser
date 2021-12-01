package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

import java.util.List;

@Data
public class Ciff {

    String magic;
    long headerSize;
    long contentSize;
    long width;
    long height;
    String caption;
    List<String> tags;
}
