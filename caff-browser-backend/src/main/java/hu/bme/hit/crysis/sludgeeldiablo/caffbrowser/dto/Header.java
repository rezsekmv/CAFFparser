package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

@Data
public class Header {

    Long id;
    Long length;
    String magic;
    Long header_size;
    Long num_anim;
}
