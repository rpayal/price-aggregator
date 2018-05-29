package com.inspiware.price.aggregator.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Vendor {

    private final Long id;
    private final String name;

    @JsonCreator
    public Vendor(@JsonProperty("id") final Long id,
                  @JsonProperty("name") final String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
