package com.anky.rabbitmqproducer.utility;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RabbitMQUtility {

    public static Queue createQueue(final String queueName) {
        Queue queue = new Queue(queueName, true, false, true);
        log.info("Queue Properties: {}", queue.toString());
        return queue;
    }

    public static Exchange createExchange(final String exchangeName, final String type) {
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

    public static Binding createBindingWithRoutingKey(Exchange exchange, Queue queue, String routingKey) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey).noargs();
        log.info("Binding Properties: {}", binding.toString());
        return binding;
    }

    public static Binding createBindingWithoutRoutingKey(FanoutExchange exchange, Queue queue) {
        Binding binding = BindingBuilder.bind(queue).to(exchange);
        log.info("Binding Properties: {}", binding.toString());
        return binding;
    }
}
