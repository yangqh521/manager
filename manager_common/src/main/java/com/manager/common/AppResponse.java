package com.manager.common;

import lombok.Data;

@Data
public class AppResponse {
    private int code = 0;
    private Object data;
    private String message;
}
