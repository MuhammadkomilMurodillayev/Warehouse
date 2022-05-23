package com.example.warehouse.exception.handler;


import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.error.AppErrorDto;
import com.example.warehouse.exception.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice("com.example.warehouse")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<DataDto> handleTestException(BadCredentialsException exception, WebRequest webRequest) {
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.BAD_REQUEST,
                                        exception.getMessage(),
                                        exception.getDeveloperMessage(),
                                        webRequest))
                        .build(), HttpStatus.OK);
    }
}
