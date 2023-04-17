package com.hieu.crypto.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseEntity {
    private String message;
    private int code;
    private String printStackTrace;
}
