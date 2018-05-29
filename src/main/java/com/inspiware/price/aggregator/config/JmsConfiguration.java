package com.inspiware.price.aggregator.config;

import com.inspiware.price.aggregator.domain.InstrumentPrice;
import com.inspiware.price.aggregator.service.PriceAggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.context.IntegrationContextUtils;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.MessagingException;

import javax.jms.ConnectionFactory;
import java.util.concurrent.CountDownLatch;

@EnableJms
@Configuration
@IntegrationComponentScan
public class JmsConfiguration {

    @Autowired
    private ConnectionFactory jmsConnectionFactory;

    @Autowired
    private PriceAggregatorService priceAggregatorService;

    @Bean
    public JmsListenerContainerFactory<?> containerFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public IntegrationFlow errorHandlingFlow() {
        return IntegrationFlows.from(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
                .handle(m -> {
                    MessagingException exception = (MessagingException) m.getPayload();
                    redeliveryLatch().countDown();
                    throw exception;
                })
                .get();
    }


    @Bean
    public CountDownLatch redeliveryLatch() {
        return new CountDownLatch(3);
    }

    @Bean()
    public IntegrationFlow vendor1IntegrationFlow(MessageConverter messageConverter) {

        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(this.jmsConnectionFactory)
                        .errorChannel(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
                        .jmsMessageConverter(messageConverter)
                        .destination("price-feed-vendor1"))
                .handle(m -> priceAggregatorService.addOrUpdate((InstrumentPrice) m.getPayload()))
                .get();
    }

    @Bean()
    public IntegrationFlow vendor2IntegrationFlow(MessageConverter messageConverter) {

        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(this.jmsConnectionFactory)
                        .errorChannel(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
                        .jmsMessageConverter(messageConverter)
                        .destination("price-feed-vendor2"))
                .handle(m -> priceAggregatorService.addOrUpdate((InstrumentPrice) m.getPayload()))
                .get();
    }

    @Bean()
    public IntegrationFlow vendor3IntegrationFlow(MessageConverter messageConverter) {

        return IntegrationFlows
                .from(Jms.messageDrivenChannelAdapter(this.jmsConnectionFactory)
                        .errorChannel(IntegrationContextUtils.ERROR_CHANNEL_BEAN_NAME)
                        .jmsMessageConverter(messageConverter)
                        .destination("price-feed-vendor3"))
                .handle(m -> priceAggregatorService.addOrUpdate((InstrumentPrice) m.getPayload()))
                .get();
    }
}
