package com.example.warehouse.exception.handler;


import com.example.warehouse.dto.data.DataDto;
import com.example.warehouse.dto.error.AppErrorDto;
import com.example.warehouse.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@RestController
@ControllerAdvice("com.example.warehouse")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException exception, WebRequest webRequest, HttpServletResponse response) {
        response.setStatus(404);
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.NOT_FOUND,
                                        exception.getMessage(),
                                        exception.getDeveloperMessage(),
                                        webRequest))
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<?> handleObjectNotFoundException(ObjectNotFoundException exception, WebRequest webRequest, HttpServletResponse response) {
        response.setStatus(404);
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.NOT_FOUND,
                                        exception.getMessage(),
                                        exception.getDeveloperMessage(),
                                        webRequest))
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PermissionDenied.class})
    public ResponseEntity<?> handlePermissionDenied(PermissionDenied exception, WebRequest webRequest) {
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.FORBIDDEN,
                                        exception.getMessage(),
                                        exception.getDeveloperMessage(),
                                        webRequest))
                        .build(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {NotSavedException.class})
    public ResponseEntity<?> handleNotSavedException(NotSavedException exception, WebRequest webRequest) {
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.INTERNAL_SERVER_ERROR,
                                        exception.getMessage(),
                                        exception.getDeveloperMessage(),
                                        webRequest))
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {FileNotFoundException.class})
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException exception, WebRequest webRequest, HttpServletResponse response) {
        System.out.print(Arrays.toString(exception.getStackTrace()));
        System.out.print(Arrays.toString(exception.getStackTrace()));
        System.out.print(Arrays.toString(exception.getStackTrace()));
        response.setStatus(400);
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.BAD_REQUEST,
                                        exception.getMessage(),
                                        Arrays.toString(exception.getStackTrace()),
                                        webRequest))
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BlockException.class})
    public ResponseEntity<?> handleFileNotFoundException(BlockException exception, WebRequest webRequest, HttpServletResponse response) {
        response.setStatus(421);
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.DESTINATION_LOCKED,
                                        exception.getMessage(),
                                        Arrays.toString(exception.getStackTrace()),
                                        webRequest))
                        .build(), HttpStatus.DESTINATION_LOCKED);
    }

    @ExceptionHandler(value = {AlreadyExitsException.class})
    public ResponseEntity<?> handleFileNotFoundException(AlreadyExitsException exception, WebRequest webRequest, HttpServletResponse response) {
        response.setStatus(400);
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.BAD_REQUEST,
                                        exception.getMessage(),
                                        Arrays.toString(exception.getStackTrace()),
                                        webRequest))
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AlreadyTakenException.class})
    public ResponseEntity<?> handleFileNotFoundException(AlreadyTakenException exception, WebRequest webRequest, HttpServletResponse response) {
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.BAD_REQUEST,
                                        exception.getMessage(),
                                        Arrays.toString(exception.getStackTrace()),
                                        webRequest))
                        .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<?> handleBadRequestException(BadRequestException exception, WebRequest webRequest, HttpServletResponse response) {
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.BAD_REQUEST,
                                        exception.getMessage(),
                                        Arrays.toString(exception.getStackTrace()),
                                        webRequest))
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpClientErrorException.class})
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException exception, WebRequest webRequest, HttpServletResponse response) {
        return new ResponseEntity<>(
                DataDto.builder()
                        .success(false)
                        .error(
                                new AppErrorDto(HttpStatus.NOT_FOUND,
                                        "you are blocked",
                                        Arrays.toString(exception.getStackTrace()),
                                        webRequest))
                        .build(), HttpStatus.NOT_FOUND);
    }

}
