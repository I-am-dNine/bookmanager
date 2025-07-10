package com.d9.bookmanager.dto;

public class ApiResponseDto<T> {
    private boolean success;
    private int code;
    private String message;
    private T data;

    // Constructors
    public ApiResponseDto() {}

    public ApiResponseDto(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Static methods
    public static <T> ApiResponseDto<T> success(String message, T data) {
        return new ApiResponseDto<>(true, 200, message, data);
    }

    public static <T> ApiResponseDto<T> error(int code, String message) {
        return new ApiResponseDto<>(false, code, message, null);
    }

    public static <T> ApiResponseDto<T> error(int code, String message, T data) {
        return new ApiResponseDto<>(false, code, message, data);
    }

    // Getters & Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}