package com.inspiware.price.aggregator.config;

import com.inspiware.price.aggregator.service.PriceAggregatorService;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashSet;

import static java.time.temporal.ChronoUnit.DAYS;

@Configuration
@EnableCaching
@AutoConfigureBefore(value = {DataStoreConfiguration.class})
public class CacheConfiguration {
    private final javax.cache.configuration.Configuration<Object, HashSet> jcacheConfiguration;

    public CacheConfiguration(ApplicationProperties applicationProperties) {
        ApplicationProperties.Cache.Ehcache ehcache = applicationProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, HashSet.class,
                        ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveDays(), DAYS)))
                        .build());

    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(PriceAggregatorService.PRICES_BY_INSTRUMENT_CACHE, jcacheConfiguration);
            cm.createCache(PriceAggregatorService.PRICES_BY_VENDOR_CACHE, jcacheConfiguration);
        };
    }
}
