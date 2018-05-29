package com.inspiware.price.aggregator.controller;

import com.inspiware.price.aggregator.PriceAggregatorApp;
import com.inspiware.price.aggregator.service.PriceAggregatorService;
import com.inspiware.price.aggregator.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PriceAggregatorApp.class)
public class PriceAggregatorControllerIT {

    private MockMvc restMockMvc;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private PriceAggregatorService service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cacheManager.getCache(PriceAggregatorService.PRICES_BY_VENDOR_CACHE).clear();
        cacheManager.getCache(PriceAggregatorService.PRICES_BY_INSTRUMENT_CACHE).clear();

        PriceAggregatorController controller = new PriceAggregatorController(service);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    @Before
    public void initTests() {
        TestUtils.getAllPrices().forEach(p -> service.addOrUpdate(p));
    }

    @Test
    public void getPricesFromVendorTest() throws Exception {

        restMockMvc.perform(get("/api/prices/vendor/{vendorId}", TestUtils.vendor1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.[0].vendor.id").value(TestUtils.vendor1.getId()));

        assertThat(cacheManager.getCache(PriceAggregatorService.PRICES_BY_VENDOR_CACHE).get(TestUtils.vendor1.getId())).isNotNull();
    }


    @Test
    public void getPricesForInstrumentTest() throws Exception {
        TestUtils.getAllPrices().forEach(p -> service.addOrUpdate(p));

        restMockMvc.perform(get("/api/prices/instrument/{instrumentId}", TestUtils.testInstrument1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(jsonPath("$.[0].instrument.id").value(TestUtils.testInstrument1.getId()));

        assertThat(cacheManager.getCache(PriceAggregatorService.PRICES_BY_VENDOR_CACHE).get(TestUtils.testInstrument1.getId())).isNotNull();
    }
}
