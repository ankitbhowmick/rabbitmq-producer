package com.anky.rabbitmqproducer.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;


@Configuration
@Log4j2
public class RabbitMQConfig {

    static Queue createQueue(final String queueName) {
        Queue queue = new Queue(queueName, true, false, true);
        log.info("Queue Properties: {}", queue.toString());
        return queue;
    }

    static Exchange createExchange(final String exchangeName, final String type) {
        Exchange exchange;

        if (type.equals("Direct"))
            exchange = new DirectExchange(exchangeName);
        else if (type.equals("Topic"))
            exchange = new TopicExchange(exchangeName);
        else
            exchange = new FanoutExchange(exchangeName);

        log.info("Exchange Properties : {}", exchange.toString());
        return exchange;
    }

    static Binding createBindingWithRoutingKey(Exchange exchange, Queue queue, String routingKey) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
        log.info("Binding Properties: {}", binding.toString());
        return binding;
    }

    static Binding createBindingWithoutRoutingKey(FanoutExchange exchange, Queue queue) {
        Binding binding = BindingBuilder.bind(queue).to(exchange);
        log.info("Binding Properties: {}", binding.toString());
        return binding;
    }
}
