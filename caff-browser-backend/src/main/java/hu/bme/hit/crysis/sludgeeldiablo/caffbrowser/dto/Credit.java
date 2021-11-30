package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

@Data
public class Credit {

    Long id;
    Long length;
    Long month;
    Long day;
    Long hour;
    Long minute;
    Long creator_length;
    String creator;
}
