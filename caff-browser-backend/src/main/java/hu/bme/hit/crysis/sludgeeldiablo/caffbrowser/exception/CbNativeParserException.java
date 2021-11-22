package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception;

import lombok.Getter;

@Getter
public class CbNativeParserException extends RuntimeException {

    private final String message;

    public CbNativeParserException(String message) {
        this.message = message;
    }
}
