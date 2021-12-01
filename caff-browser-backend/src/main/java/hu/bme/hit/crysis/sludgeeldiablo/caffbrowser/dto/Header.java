package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

@Data
public class Header {

    long id;
    long length;
    String magic;
    long header_size;
    long num_anim;
}
