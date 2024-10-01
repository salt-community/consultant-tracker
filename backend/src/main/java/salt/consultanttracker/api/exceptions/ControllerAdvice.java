package salt.consultanttracker.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.net.ssl.SSLException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({ConsultantNotFoundException.class})
    private ResponseEntity<ApiError> notFoundException (RuntimeException exception) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND.value(),HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler({ExternalAPIException.class, UnexpectedResponseException.class, SSLException.class})
    private ResponseEntity<ApiError> externalAPIException (ExternalAPIException exception) {
        ApiError apiError = new ApiError(
                CustomStatusCodes.EXTERNAL_API_ERROR.getCode(),
                CustomStatusCodes.EXTERNAL_API_ERROR.getReasonPhrase(),
                exception.getMessage()
        );
        return ResponseEntity.status(CustomStatusCodes.EXTERNAL_API_ERROR.getCode()).body(apiError);
    }
}
