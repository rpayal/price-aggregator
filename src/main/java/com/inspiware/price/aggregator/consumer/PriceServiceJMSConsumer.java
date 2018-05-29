package com.inspiware.price.aggregator.consumer;

import com.inspiware.price.aggregator.config.ApplicationProperties;
import com.inspiware.price.aggregator.domain.InstrumentPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PriceServiceJMSConsumer {
    @Autowired
    private JmsTemplate jmsTemplate;

    private String consumerTopic;

    public PriceServiceJMSConsumer(ApplicationProperties applicationProperties) {
        this.consumerTopic = applicationProperties.getConsumerTopic();
    }

    public void consume(InstrumentPrice instrumentPrice) {
        jmsTemplate.convertAndSend(consumerTopic, instrumentPrice);
    }
}
