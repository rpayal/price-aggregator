package com.inspiware.price.aggregator.config;

import com.inspiware.price.aggregator.domain.InstrumentPrice;
import com.inspiware.price.aggregator.exception.UnknownDatastoreTypeException;
import com.inspiware.price.aggregator.store.InstrumentPriceMapStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class DataStoreConfiguration {
    private final Logger log = LoggerFactory.getLogger(DataStoreConfiguration.class);

    private ApplicationProperties properties;

    public DataStoreConfiguration(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Bean(name = "instrumentPriceMapStore")
    public InstrumentPriceMapStore<InstrumentPrice.InstrumentPriceKey, InstrumentPrice> instrumentPriceMapStore() {
        if (properties.getDataStoreType() == null || properties.getDataStoreType().equals("MAP")) {
            return new InstrumentPriceMapStore<>("instrumentPriceMapStore");
        } else {
            throw new UnknownDatastoreTypeException("Unknown datastore type");
        }
    }
}
