package br.com.petfriends.transporte.app.response.dto;

import br.com.petfriends.transporte.core.exception.CoreException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ApiResponse<T> {
    private final boolean success;
    private final T result;
    private final ErrorData error;

    private ApiResponse(boolean success, T result, ErrorData error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    public ResponseEntity<ApiResponse<T>> createResponse(HttpStatus status) {
        return ResponseEntity.status(status).body(this);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public ErrorData getError() {
        return error;
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(true, result, null);
    }

    public static <T> ApiResponse<T> error(CoreException coreException) {
        ErrorData error = new ErrorData(coreException.getCode(), coreException.getMessage());
        return new ApiResponse<>(false, null, error);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        ErrorData error = new ErrorData(code, message);
        return new ApiResponse<>(false, null, error);
    }
}
