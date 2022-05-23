package com.example.warehouse.entity.warehouse;

import com.example.warehouse.entity.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse extends Auditable {

    private String name;

    private String regionId;

    private String organizationId;

    private Double latitude;

    private Double longitude;


}
