package com.inspiware.price.aggregator.service;

import com.inspiware.price.aggregator.consumer.PriceServiceJMSConsumer;
import com.inspiware.price.aggregator.domain.InstrumentPrice;
import com.inspiware.price.aggregator.store.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PriceAggregatorService {
    public static final String PRICES_BY_VENDOR_CACHE = "pricesByVendor";
    public static final String PRICES_BY_INSTRUMENT_CACHE = "pricesByInstrument";
    private static final Logger log = LoggerFactory.getLogger(PriceAggregatorService.class);
    private final Store<InstrumentPrice.InstrumentPriceKey, InstrumentPrice> priceStore;

    private final CacheManager cacheManager;

    private final PriceServiceJMSConsumer priceServiceJMSConsumer;

    public PriceAggregatorService(Store priceStore, CacheManager cacheManager, PriceServiceJMSConsumer priceServiceJMSConsumer) {
        this.priceStore = priceStore;
        this.cacheManager = cacheManager;
        this.priceServiceJMSConsumer = priceServiceJMSConsumer;
    }

    @Cacheable(cacheNames = {PRICES_BY_VENDOR_CACHE})
    public Set<InstrumentPrice> findAllByVendorId(final Long vendorId) {
        return new HashSet<>(priceStore.getAll()
                .stream()
                .filter(v -> v.getVendor().getId().equals(vendorId))
                .collect(Collectors.toList()));
    }

    @Cacheable(cacheNames = {PRICES_BY_INSTRUMENT_CACHE})
    public Set<InstrumentPrice> findAllByInstrumentId(final Long instrumentId) {
        return new HashSet<>(priceStore.getAll()
                .stream()
                .filter(v -> v.getInstrument().getId().equals(instrumentId))
                .collect(Collectors.toList()));
    }

    public void addOrUpdate(final InstrumentPrice instrumentPrice) {
        // Enter the price to the local cache
        updateVendorCache(instrumentPrice);
        updateInstrumentCache(instrumentPrice);

        // Store the price
        priceStore.addOrUpdatePrice(instrumentPrice.getKey(), instrumentPrice);

        // Publish the price for downstream consumers
        publish(instrumentPrice);
    }

    public void publish(final InstrumentPrice instrumentPrice) {
        priceServiceJMSConsumer.consume(instrumentPrice);
    }

    // PRIVATE METHODS

    private void updateVendorCache(InstrumentPrice instrumentPrice) {
        Cache.ValueWrapper vendorCache = cacheManager.getCache(PRICES_BY_VENDOR_CACHE).get(instrumentPrice.getVendor().getId());
        if (vendorCache != null && vendorCache.get() != null) {
            HashSet<InstrumentPrice> set = ((HashSet) vendorCache.get());
            set.add(instrumentPrice);
            cacheManager.getCache(PRICES_BY_VENDOR_CACHE).put(instrumentPrice.getVendor().getId(), set);
        } else {
            cacheManager.getCache(PRICES_BY_VENDOR_CACHE).put(instrumentPrice.getVendor().getId(), new HashSet(Arrays.asList(instrumentPrice)));
        }
    }


    private void updateInstrumentCache(InstrumentPrice instrumentPrice) {
        Cache.ValueWrapper instrumentCache = cacheManager.getCache(PRICES_BY_INSTRUMENT_CACHE).get(instrumentPrice.getInstrument().getId());
        if (instrumentCache != null && instrumentCache.get() != null) {
            HashSet set = ((HashSet) instrumentCache.get());
            set.add(instrumentPrice);
            cacheManager.getCache(PRICES_BY_INSTRUMENT_CACHE).put(instrumentPrice.getInstrument().getId(), set);
        } else {
            cacheManager.getCache(PRICES_BY_INSTRUMENT_CACHE).put(instrumentPrice.getInstrument().getId(), new HashSet(Arrays.asList(instrumentPrice)));
        }
    }
}
