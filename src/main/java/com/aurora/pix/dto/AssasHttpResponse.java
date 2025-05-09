package com.aurora.pix.dto;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import okhttp3.Response;

@Getter
public class AssasHttpResponse {

    private boolean success;
    private LinkedHashMap<String, Object> data;
    private final String message;
    private int code;

    public AssasHttpResponse(Response response) throws IOException {

        this.code = response.code();
        this.success = this.code == 200;

        if (!this.success) {
            this.message = switch (code) {
                case 400 -> "Invalid request to ASSAS: Please check the payload and required fields.";
                case 401, 403 -> "Authentication failed with ASSAS";
                case 422 -> "Validation error in ASSAS";
                default -> "Unexpected error in ASSAS";
            };
        } else {
            this.message = null;
            String bodyString = response.body() != null ? response.body().string() : "";
            LinkedHashMap<String, Object> bodyMap = new ObjectMapper().readValue(bodyString, LinkedHashMap.class);
            this.data = bodyMap;
        }
    }

}
