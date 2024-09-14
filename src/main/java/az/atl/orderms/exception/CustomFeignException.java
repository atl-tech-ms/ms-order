package az.atl.orderms.exception;

import lombok.Getter;

@Getter
public class CustomFeignException extends RuntimeException {

    private final Integer status;

    public CustomFeignException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}