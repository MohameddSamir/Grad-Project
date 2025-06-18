package com.favtour.travel.core.exception;

import com.favtour.travel.booking.exception.CancelBookingException;
import com.favtour.travel.core.payload.ApiResponse;
import com.favtour.travel.shared.FileStorageException;
import com.favtour.travel.user.exception.DuplicateEmailException;
import com.favtour.travel.shared.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateEmailException(DuplicateEmailException exc){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse<>(false, exc.getMessage(), null));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFoundException(EntityNotFoundException exc){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(false, exc.getMessage(), null));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleValidationExceptions(MethodArgumentNotValidException exc){
        Map<String, String> errors=new HashMap<>();
        exc.getBindingResult().getFieldErrors().forEach(error->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, "Validation Failed", errors));
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, "Invalid Email or Password", null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException exc){
        return ResponseEntity.badRequest()
                .body(new ApiResponse<>(false, exc.getMessage(), null));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ApiResponse<Void>> handleIOException(FileStorageException exc){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, exc.getMessage(), null));
    }

    @ExceptionHandler(CancelBookingException.class)
    public ResponseEntity<ApiResponse<Void>> handleCancelBookingException(CancelBookingException exc){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(false, exc.getMessage(), null));
    }
}
