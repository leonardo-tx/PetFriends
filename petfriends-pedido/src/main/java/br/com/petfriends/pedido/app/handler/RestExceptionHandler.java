package br.com.petfriends.pedido.app.handler;

import br.com.petfriends.pedido.app.response.dto.ApiResponse;
import br.com.petfriends.pedido.core.exception.CoreException;
import br.com.petfriends.pedido.core.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ApiResponse<Object>> validationException(CoreException e) {
        return ApiResponse.error(e).createResponse(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> notFoundException(NotFoundException e) {
        return ApiResponse.error(e).createResponse(HttpStatus.NOT_FOUND);
    }
}
