package com.example.warehouse.properties.query;

import com.example.warehouse.properties.AbstractQueriesProperties;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "query.organization")
public class OrganizationQueryProperties extends AbstractQueriesProperties {
}
