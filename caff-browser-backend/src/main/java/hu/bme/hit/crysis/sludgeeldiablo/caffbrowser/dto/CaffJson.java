package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CaffJson {
    Header header;
    Credit credit;
    Animation animation;
}


@AllArgsConstructor
@Getter
@Setter
class Header {
    Long id;
    Long length;
    String magic;
    Long header_size;
    Long num_anim;
}

@AllArgsConstructor
@Getter
@Setter
class Credit {
    Long id;
    Long length;
    Long month;
    Long day;
    Long hour;
    Long minute;
    Long creator_length;
    String creator;
}

@AllArgsConstructor
@Getter
@Setter
class Animation {
    List<Long> ids;
    List<Long> lengths;
    List<Long> durations;
    List<CIFF> CIFFs;
}

@AllArgsConstructor
@Getter
@Setter
class CIFF {
    String magic;
    Long header_size;
    Long content_size;
    Long width;
    Long height;
    String caption;
    List<String> tags;
}


