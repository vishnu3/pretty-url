package com.stylight.prettyurl.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorModel implements Serializable {
    private static final long serialVersionUID = 3159769210171285469L;

    private int errorCode;

    private String errorMessage;

    private HttpStatus httpStatus;

    public static ResponseEntity<ErrorModel> genericError() {
        ErrorModel err = new ErrorModel(ValidationError.GENERIC_ERROR.errorCode,
                ValidationError.GENERIC_ERROR.errorMessage, ValidationError.GENERIC_ERROR.httpStatus);
        return ResponseEntity.internalServerError().body(err);
    }

    ;

    public static ErrorModel of(int errorCode, String errorMessage, HttpStatus httpStatus) {
        return new ErrorModel(errorCode, errorMessage, httpStatus);
    }

    @AllArgsConstructor
    @Getter
    public enum ValidationError {
        GENERIC_ERROR(100, "Something went wrong with the application", HttpStatus.INTERNAL_SERVER_ERROR),
        VALIDATION_ERROR(101, "url not valid", HttpStatus.BAD_REQUEST);

        int errorCode;
        String errorMessage;
        HttpStatus httpStatus;
    }
}
