package com.tus.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private String apiPath;
    private String errorCode;
    private String errorMessage;
    private String errorTime;
}
