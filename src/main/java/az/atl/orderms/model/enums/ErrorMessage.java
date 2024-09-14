package az.atl.customerms.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    CLIENT_ERROR("Exception from Client");

    private final String message;
}