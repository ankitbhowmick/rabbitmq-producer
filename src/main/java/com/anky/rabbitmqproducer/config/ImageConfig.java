package com.anky.rabbitmqproducer.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

import static com.anky.rabbitmqproducer.utility.RabbitMQUtility.*;

@Configuration
@Log4j2
public class ImageConfig {

    private final Environment environment;

    @Autowired
    public ImageConfig(Environment environment) {

        Objects.requireNonNull(environment.getProperty("image.exchange.name"), "Image Exchange Name cannot be empty. Please check application.yml");
        Objects.requireNonNull(environment.getProperty("image.exchange.type"), "Image Exchange Type cannot be empty. Please check application.yml");
        Objects.requireNonNull(environment.getProperty("image.queue.jpg.name"), "Jpg Queue Name cannot be null.Please check the application.yml");
        Objects.requireNonNull(environment.getProperty("image.queue.png.name"), "Ppg Queue Name cannot be null.Please check the application.yml");
        Objects.requireNonNull(environment.getProperty("image.queue.jpg.bindingKey"), "Jpg Routing Key cannot be null.Please check the application.yml");
        Objects.requireNonNull(environment.getProperty("image.queue.png.bindingKey"), "Png Routing Key cannot be null.Please check the application.yml");

        this.environment = environment;
    }

    @Bean
    public Exchange imageExchange() {
        return createExchange(environment.getProperty("image.exchange.name"), environment.getProperty("image.exchange.type"));
    }

    @Bean
    public Queue jpegQueue() {
        return createQueue(environment.getProperty("image.queue.jpg.name"));
    }

    @Bean
    public Queue pngQueue() {
        return createQueue(environment.getProperty("image.queue.png.name"));
    }

    @Bean
    public Binding jpegBinding(Exchange imageExchange, Queue jpegQueue) {
        return createBindingWithRoutingKey(imageExchange, jpegQueue, environment.getProperty("image.queue.jpg.bindingKey"));
    }

    @Bean
    public Binding pngBinding(Exchange imageExchange, Queue pngQueue) {
        return createBindingWithRoutingKey(imageExchange, pngQueue, environment.getProperty("image.queue.png.bindingKey"));
    }
}
