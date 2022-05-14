package com.example.warehouse.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Auditable implements BaseEntity {

    private String id;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;

    private boolean deleted;

    private short status;// -1 => non active, 0 => active

    public Auditable() {
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.status = 0;
        this.createdAt = LocalDateTime.now();
    }

}
