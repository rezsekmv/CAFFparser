package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.dto;

import lombok.Data;

@Data
public class Credit {

    long id;
    long length;
    long year;
    long month;
    long day;
    long hour;
    long minute;
    long creator_length;
    String creator;
}
