package hu.bme.hit.crysis.sludgeeldiablo.caffbrowser.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(CbNotFoundException.class)
    public ResponseEntity<CbError> handleNotFoundException() {
        String description = "Not found";
        String message = messageSource.getMessage("error.notFound", null, null);

        log.error("{}: {}", description, message);
        return new ResponseEntity<>(new CbError(message, description), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CbForbiddenException.class)
    public ResponseEntity<CbError> handleCfmForbiddenException(CbForbiddenException e) {
        String description = "Forbidden";
        String message = messageSource.getMessage(e.getMessageKey(), null, null);

        log.error("{}: {}", description, message);
        return new ResponseEntity<>(new CbError(message, description), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CbException.class)
    public ResponseEntity<CbError> handleCfmException(CbException e) {
        String description = "Application error";
        String message = messageSource.getMessage(e.getMessageKey(), null, null);

        log.error("{}: {}", description, message);
        return new ResponseEntity<>(new CbError(message, description), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CbTokenException.class)
    public ResponseEntity<CbError> handleTokenException(CbTokenException e) {
        String description = "Token error";
        String message = e.getMessage();
        String comment = "Your refresh token is probably expired or invalid, try getting a new one";

        log.error("{}: {}", description, message);
        return new ResponseEntity<>(new CbError(message, description, comment), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CbError> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String description = "Invalid message";
        String message = e.getMessage();
        String comment = "Check the JSON message, you might have a typo in it";

        log.error("{}: {}", description, message);
        return new ResponseEntity<>(new CbError(message, description, comment), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CbError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String description = "Method not allowed";
        String message = e.getMessage();
        String comment = "Make sure you are using an existing path";

        log.error("{}: {}", description, message);
        return new ResponseEntity<>(new CbError(message, description, comment), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<CbError> handleException(Throwable t) {
        String description = "Unexpected error";
        String message = t.getMessage();

        log.error("{}: {}", description, message);
        return new ResponseEntity<>(new CbError(message, description), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
