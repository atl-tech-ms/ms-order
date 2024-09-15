package az.atl.orderms.exception;

import az.atl.orderms.model.response.ErrorResponse;
import az.atl.orderms.model.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<ErrorResponse> handle(HttpServletRequest request,
                                                CustomFeignException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(ErrorResponse.builder()
                .message(exception.getMessage())
                .status(BAD_REQUEST.value())
                .path(request.getServletPath())
                .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(NotFoundException ex, HttpServletRequest request) {
        log.error("NotFoundException : " + ex);
        return ResponseEntity.status(NOT_FOUND)
                .body(ExceptionResponse.builder()
                        .status(NOT_FOUND.value())
                        .error(NOT_FOUND.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .timestamp(LocalDateTime.now())
                        .build());

    }
}
