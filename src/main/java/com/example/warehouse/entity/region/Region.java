package com.example.warehouse.entity.region;

import com.example.warehouse.entity.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Region extends Auditable {

    private String name;

    private Double latitude;

    private Double longitude;

}

