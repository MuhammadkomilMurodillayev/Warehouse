package com.example.warehouse.dto.data;

import com.example.warehouse.dto.error.AppErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataDto<T> implements Serializable {

    protected T data;

    protected AppErrorDto error;

    protected boolean success;

    private Integer totalCount;

    private Long allDataCount;

    public DataDto(T data) {
        this.data = data;
        this.success = true;
    }

    public DataDto(AppErrorDto error) {
        this.error = error;
        this.success = false;
    }

    public DataDto(T data, Integer totalCount) {
        this(data);
        this.totalCount = totalCount;
    }

    public DataDto(T data, Integer totalCount, Long allDataCount) {
        this(data, totalCount);
        this.allDataCount = allDataCount;
    }
}
