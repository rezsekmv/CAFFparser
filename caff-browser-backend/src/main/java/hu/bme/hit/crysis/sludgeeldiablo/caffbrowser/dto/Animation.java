package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

import java.util.List;

@Data
public class Animation {

    List<Long> ids;
    List<Long> lengths;
    List<Long> durations;
    List<Ciff> ciffs;
}
