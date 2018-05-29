package com.inspiware.price.aggregator.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Instrument {
    private final Long id;
    private final String symbol;
    private final String name;
    private final String currency;

    @JsonCreator
    public Instrument(@JsonProperty("id") final Long id,
                      @JsonProperty("symbol") final String symbol,
                      @JsonProperty("name") final String name,
                      @JsonProperty("currency") final String currency) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

}
