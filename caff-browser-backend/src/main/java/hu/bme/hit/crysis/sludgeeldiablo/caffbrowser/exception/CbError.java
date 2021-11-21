package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CbError {

    private final String description;
    private final String error;
    private final String comment;

    public CbError(String error, String description) {
        this.error = error;
        this.description = description;
        this.comment = null;
    }

    public CbError(String error, String description, String comment) {
        this.error = error;
        this.description = description;
        this.comment = comment;
    }
}
