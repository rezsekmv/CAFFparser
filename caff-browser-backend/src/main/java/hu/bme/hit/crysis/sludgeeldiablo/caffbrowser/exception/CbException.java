package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception;

import lombok.Getter;

@Getter
public class CbException extends RuntimeException {

    private final String messageKey;

    public CbException(String messageKey) {
        this.messageKey = messageKey;
    }
}
