package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

@Data
public class Header {

    long id;
    long length;
    String magic;
    long headerSize;
    long numAnim;
}
