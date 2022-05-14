package com.example.warehouse.entity.organization;

import com.example.warehouse.entity.Auditable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Organization extends Auditable {

    private String name;

    private String description;

    private String logoImagePath;

    public Organization() {
        super();
    }

}
