package com.intellivix.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {
    private String id;
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
