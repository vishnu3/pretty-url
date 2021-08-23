package com.stylight.prettyurl.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> genericError(Exception ex) {
        log.error("Error during processing ", ex);
        return ErrorModel.genericError();
    }

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<ErrorModel> validationError(InvalidUrlException ex) {
        return ResponseEntity.badRequest().body(ErrorModel.of(ErrorModel.ValidationError.VALIDATION_ERROR.errorCode,
                ErrorModel.ValidationError.VALIDATION_ERROR.errorMessage,
                ErrorModel.ValidationError.VALIDATION_ERROR.httpStatus));
    }
}
