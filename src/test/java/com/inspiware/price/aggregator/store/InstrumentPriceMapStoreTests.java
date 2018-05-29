package com.inspiware.price.aggregator.store;

import com.inspiware.price.aggregator.domain.InstrumentPrice;
import com.inspiware.price.aggregator.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class InstrumentPriceMapStoreTests {

    private InstrumentPriceMapStore mapStore;

    @Before
    public void setup() {
        mapStore = new InstrumentPriceMapStore("test-store");
    }

    @Test
    public void test_the_name() {
        assertEquals("test-store", mapStore.getName());
    }

    @Test
    public void test_empty_set() {
        assertTrue(mapStore.getAll().isEmpty());
    }

    @Test
    public void test_get_all() {
        mapStore.addOrUpdatePrice(TestUtils.testPrice1.getKey(), TestUtils.testPrice1);
        mapStore.addOrUpdatePrice(TestUtils.testPrice2.getKey(), TestUtils.testPrice2);
        mapStore.addOrUpdatePrice(TestUtils.testPrice3.getKey(), TestUtils.testPrice3);
        Set<InstrumentPrice> prices = mapStore.getAll();
        assertFalse(prices.isEmpty());
        assertEquals(3, prices.size());
    }

    @Test
    public void test_get() {
        mapStore.addOrUpdatePrice(TestUtils.testPrice1.getKey(), TestUtils.testPrice1);
        Set<InstrumentPrice> prices = mapStore.getAll();
        assertFalse(prices.isEmpty());

        InstrumentPrice price = (InstrumentPrice) mapStore.get(TestUtils.testPrice1.getKey());
        assertTrue(price.equals(TestUtils.testPrice1));
    }

    @Test
    public void test_add_prices() {
        mapStore.addOrUpdatePrice(TestUtils.testPrice1.getKey(), TestUtils.testPrice1);
        Set<InstrumentPrice> prices = mapStore.getAll();
        assertFalse(prices.isEmpty());
        assertEquals(1, prices.size());
    }

    @Test
    public void test_delete() {
        mapStore.addOrUpdatePrice(TestUtils.testPrice1.getKey(), TestUtils.testPrice1);
        Set<InstrumentPrice> prices = mapStore.getAll();
        assertFalse(prices.isEmpty());

        mapStore.delete(TestUtils.testPrice1.getKey());
        assertTrue(mapStore.getAll().isEmpty());
    }
}
