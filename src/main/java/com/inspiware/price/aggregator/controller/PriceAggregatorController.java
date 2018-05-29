package com.inspiware.price.aggregator.controller;

import com.inspiware.price.aggregator.domain.InstrumentPrice;
import com.inspiware.price.aggregator.service.PriceAggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RestController
@RequestMapping("/api/prices")
public class PriceAggregatorController {
    private static final Logger log = LoggerFactory.getLogger(PriceAggregatorController.class);

    private PriceAggregatorService priceAggregatorService;

    public PriceAggregatorController(PriceAggregatorService priceAggregatorService) {
        this.priceAggregatorService = priceAggregatorService;
    }

    @GetMapping("/vendor/{vendorId}")
    public Set<InstrumentPrice> getPricesFromVendor(@PathVariable("vendorId") Long vendorId) {
        log.info(String.format("Fetching prices for vendor %d", vendorId));
        Set<InstrumentPrice> prices;
        try {
            prices = priceAggregatorService.findAllByVendorId(vendorId);
        } catch (Exception ex) {
            log.error(String.format("Unable to fetch prices for vendor %d", vendorId), ex);
            prices = Collections.emptySet();
        }
        return prices;
    }

    @GetMapping("/instrument/{instrumentId}")
    public Set<InstrumentPrice> getPricesForInstrument(@PathVariable("instrumentId") Long instrumentId) {
        log.info(String.format("Fetching prices for instrument %d", instrumentId));
        Set<InstrumentPrice> prices;
        try {
            prices = priceAggregatorService.findAllByInstrumentId(instrumentId);
        } catch (Exception ex) {
            log.error(String.format("Unable to fetch prices for instrument %d", instrumentId), ex);
            prices = Collections.emptySet();
        }
        return prices;
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Set<InstrumentPrice> prices) {
        log.info("Adding " + prices.size() + " prices.");
        int created = 0;
        ResponseEntity<String> response = null;
        for (InstrumentPrice p : prices) {
            try {
                priceAggregatorService.addOrUpdate(p);
                ++created;
            } catch (Exception e) {
                log.error("Unable to create price", e);
            }
        }
        if (created == prices.size()) {
            String message = "All prices processed successfully";
            log.info(message);
            response = new ResponseEntity<String>(message, HttpStatus.OK);
        } else {
            String message = created + " out of " + prices.size() + " prices created.";
            log.warn(message);
            response = new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    /**
     * Handles <code>EmptyResultDataAccessException</code> object, returning
     * HTTP Status 404.
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Price for the instrument not found")
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void exceptionHandler() {
    }
}
