package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception;

import lombok.Getter;

@Getter
public class CbForbiddenException extends CbException {

    public CbForbiddenException(String messageKey) {
        super(messageKey);
    }
}
