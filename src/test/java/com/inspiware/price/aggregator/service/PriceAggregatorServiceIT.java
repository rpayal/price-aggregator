package com.inspiware.price.aggregator.service;

import com.inspiware.price.aggregator.PriceAggregatorApp;
import com.inspiware.price.aggregator.domain.InstrumentPrice;
import com.inspiware.price.aggregator.store.Store;
import com.inspiware.price.aggregator.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PriceAggregatorApp.class)
public class PriceAggregatorServiceIT {

    @Autowired
    private Store store;

    @Autowired
    private PriceAggregatorService service;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void test_find_by_vendor_id() {
        TestUtils.getAllPrices().forEach(p -> store.addOrUpdatePrice(p.getKey(), p));

        Set<InstrumentPrice> prices = service.findAllByVendorId(TestUtils.vendor1.getId());
        assertFalse(prices.isEmpty());
        assertEquals(8, prices.size());
    }

    @Test
    public void test_find_by_invalid_vendor_id() {
        TestUtils.getAllPrices().forEach(p -> store.addOrUpdatePrice(p.getKey(), p));

        Set<InstrumentPrice> prices = service.findAllByVendorId(1L);
        assertTrue(prices.isEmpty());
    }

    @Test
    public void test_find_by_instrument_id() {
        TestUtils.getAllPrices().forEach(p -> store.addOrUpdatePrice(p.getKey(), p));

        Set<InstrumentPrice> prices = service.findAllByInstrumentId(TestUtils.testInstrument1.getId());
        assertFalse(prices.isEmpty());
        assertEquals(12, prices.size());
    }

    @Test
    public void test_find_by_invalid_instrument_id() {
        TestUtils.getAllPrices().forEach(p -> store.addOrUpdatePrice(p.getKey(), p));

        Set<InstrumentPrice> prices = service.findAllByInstrumentId(1L);
        assertTrue(prices.isEmpty());
    }

    @Test
    public void test_publish() {
        service.publish(TestUtils.testPrice1);
        this.jmsTemplate.setReceiveTimeout(10_000);
        InstrumentPrice price = (InstrumentPrice) this.jmsTemplate.receiveAndConvert("pub-sub-topic");
        assertTrue(TestUtils.testPrice1.equals(price));
    }

    @Test
    public void test_cache_availability() {
        TestUtils.getAllPrices().forEach(p -> store.addOrUpdatePrice(p.getKey(), p));

        Set<InstrumentPrice> prices = service.findAllByVendorId(TestUtils.vendor1.getId());

        Cache.ValueWrapper value = cacheManager.getCache(PriceAggregatorService.PRICES_BY_VENDOR_CACHE).get(TestUtils.vendor1.getId());
        assertNotNull(value.get());
    }


    @Test
    public void test_cache_availability_1() {
        TestUtils.getAllPrices().forEach(p -> service.addOrUpdate(p));
        Cache.ValueWrapper value = cacheManager.getCache(PriceAggregatorService.PRICES_BY_VENDOR_CACHE).get(TestUtils.vendor1.getId());
        assertNotNull(value.get());
        service.findAllByVendorId(TestUtils.vendor1.getId());
    }
}
