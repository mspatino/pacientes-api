package com.consultorio.pacientes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.NOT_FOUND.value());
        error.put("error", "Not Found");
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Error interno");
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

        // ALIDACIONES (ACÁ VA EL QUE PREGUNTASTE)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

@ExceptionHandler(BusinessException.class)
public ResponseEntity<Map<String, Object>> handleBusiness(BusinessException ex) {

    Map<String, Object> error = new HashMap<>();
    error.put("timestamp", LocalDateTime.now());
    error.put("status", HttpStatus.CONFLICT.value());
    error.put("error", "Conflict");
    error.put("message", ex.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
}


@ExceptionHandler(RuntimeException.class)
public ResponseEntity<?> handleRuntime(RuntimeException ex) {

    String message = switch (ex.getMessage()) {
        case "TOKEN_REUSE" -> "Sesión inválida. Por favor iniciá sesión nuevamente.";
        case "TOKEN_EXPIRED" -> "Tu sesión expiró. Por favor iniciá sesión nuevamente.";
        default -> "Error del servidor";
    };

    return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(Map.of("message", message));
}

}
