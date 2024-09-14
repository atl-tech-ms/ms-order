package az.atl.orderms.exception;

import az.atl.orderms.model.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
}
