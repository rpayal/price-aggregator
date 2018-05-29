package com.inspiware.price.aggregator.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class InstrumentPrice implements Entity {
    private final Instrument instrument;
    private final Vendor vendor;
    private final BigDecimal bid;
    private final BigDecimal ask;
    private final Date created;

    @JsonIgnore
    private final InstrumentPriceKey key;

    @JsonCreator
    public InstrumentPrice(@JsonProperty("instrument") final Instrument instrument,
                           @JsonProperty("vendor") final Vendor vendor,
                           @JsonProperty("bid") final BigDecimal bid,
                           @JsonProperty("ask") final BigDecimal ask,
                           @JsonProperty("created") final Date created) {
        this.instrument = instrument;
        this.vendor = vendor;
        this.bid = bid;
        this.ask = ask;
        this.created = created;
        this.key = new InstrumentPriceKey(instrument.getId(), vendor.getId(), created);
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public Date getCreated() {
        return created;
    }

    public InstrumentPriceKey getKey() {
        return key;
    }

    @Override
    public boolean equals(Entity o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentPrice that = (InstrumentPrice) o;
        return Objects.equals(instrument.getId(), that.instrument.getId()) &&
                Objects.equals(vendor.getId(), that.vendor.getId()) &&
                Objects.equals(bid, that.bid) &&
                Objects.equals(ask, that.ask) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrument.getId(), vendor.getId(), bid, ask, created);
    }

    public static class InstrumentPriceKey implements Entity {
        private final Long instrumentId;
        private final Long vendorId;
        private final Date priceDate;

        InstrumentPriceKey(Long instrumentId, Long vendorId, Date priceDate) {
            this.instrumentId = instrumentId;
            this.vendorId = vendorId;
            this.priceDate = priceDate;
        }

        public Long getInstrumentId() {
            return instrumentId;
        }

        public Long getVendorId() {
            return vendorId;
        }

        public Date getPriceDate() {
            return priceDate;
        }

        @Override
        public boolean equals(Entity o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            InstrumentPriceKey that = (InstrumentPriceKey) o;
            return Objects.equals(instrumentId, that.instrumentId) &&
                    Objects.equals(vendorId, that.vendorId) &&
                    Objects.equals(priceDate, that.priceDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(instrumentId, vendorId, priceDate);
        }
    }
}
