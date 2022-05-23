package com.example.warehouse.dto.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppErrorDto {
    private Timestamp timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private String developerMessage;

    @Builder
    public AppErrorDto(HttpStatus status, String message, String developerMessage, WebRequest request) {
        this.developerMessage = developerMessage;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        if (Objects.nonNull(request)) {
            this.path = ((ServletWebRequest) request).getRequest().getRequestURI();
        }
    }

    @Builder(builderMethodName = "secondBuilder")
    public AppErrorDto(HttpStatus status, String message, String path) {
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    @Builder(builderMethodName = "thirdBuilder")
    public AppErrorDto(HttpStatus status, String message) {
        this(status, message, "");
    }

}
