package com.inspiware.price.aggregator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.price.aggregator", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String dataStoreType;
    private String feedTopics;
    private String consumerTopic;
    private Cache cache;

    public String getDataStoreType() {
        return dataStoreType;
    }

    public ApplicationProperties setDataStoreType(String dataStoreType) {
        this.dataStoreType = dataStoreType;
        return this;
    }

    public String getFeedTopics() {
        return feedTopics;
    }

    public ApplicationProperties setFeedTopics(String feedTopics) {
        this.feedTopics = feedTopics;
        return this;
    }

    public String getConsumerTopic() {
        return consumerTopic;
    }

    public ApplicationProperties setConsumerTopic(String consumerTopic) {
        this.consumerTopic = consumerTopic;
        return this;
    }

    public Cache getCache() {
        return cache;
    }

    public ApplicationProperties setCache(Cache cache) {
        this.cache = cache;
        return this;
    }

    public static class Cache {
        private Ehcache ehcache;

        public Ehcache getEhcache() {
            return ehcache;
        }

        public Cache setEhcache(Ehcache ehcache) {
            this.ehcache = ehcache;
            return this;
        }

        public static class Ehcache {
            private int timeToLiveDays = 30;
            private long maxEntries = 100000L;

            public Ehcache() {
            }

            public int getTimeToLiveDays() {
                return timeToLiveDays;
            }

            public Ehcache setTimeToLiveSeconds(int timeToLiveDays) {
                this.timeToLiveDays = timeToLiveDays;
                return this;
            }

            public long getMaxEntries() {
                return maxEntries;
            }

            public Ehcache setMaxEntries(long maxEntries) {
                this.maxEntries = maxEntries;
                return this;
            }
        }
    }
}