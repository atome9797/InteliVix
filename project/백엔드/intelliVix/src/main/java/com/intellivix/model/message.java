package com.intellivix.model;

import com.intellivix.enums.StatusEnum;
import lombok.Data;

@Data
public class message {
    private StatusEnum status;
    private String message;
    private Object data;

    public message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}