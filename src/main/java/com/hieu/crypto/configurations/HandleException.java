package com.hieu.crypto.configurations;

import com.hieu.crypto.model.ErrorResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ControllerAdvice
@RestController
public class HandleException {


    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponseEntity().builder().message(e.getMessage()).code(500).printStackTrace(e.getCause().getMessage()).build());
    }
}
