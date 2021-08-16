package com.project.ImageUploader.dto;

public class WsMessageDTO {
    private String message;

    public WsMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
