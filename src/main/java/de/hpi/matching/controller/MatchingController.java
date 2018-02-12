package de.hpi.matching.controller;

import de.hpi.matching.queue.RabbitReceiver;
import de.hpi.matching.service.MatchingService;
import de.hpi.restclient.dto.ParsedOffer;
import de.hpi.restclient.pojo.MatchingResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MatchingController {
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private MatchingService service;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private RabbitReceiver receiver;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private RabbitTemplate template;
    @Getter(AccessLevel.PRIVATE) private static String queueName = "matchingDoneMessages";


    // initialization
    @Autowired
    public MatchingController(MatchingService service, RabbitReceiver receiver, RabbitTemplate template){
        setService(service);
        setTemplate(template);
        setReceiver(receiver);
    }

    // convenience
    @RequestMapping(value = "/matcher/match", method = RequestMethod.POST, produces = "application/json")
    public MatchingResponse startMatchingSingleOffer(@RequestBody ParsedOffer parsedOffer){
        return getService().matchSingleOffer(parsedOffer);
    }

    public void startMatchingOffersForShop(long shopId){
        getService().matchOffersForShop(shopId);
    }


    @Bean
    Queue queue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-overflow", "reject-publish");
        return new Queue(getQueueName(), true, false, false, args);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(getQueueName());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}