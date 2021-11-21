package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception;

import lombok.Getter;

@Getter
public class CbTokenException extends RuntimeException {

    private final String message;

    public CbTokenException(String message) {
        this.message = message;
    }
}
