package com.anky.rabbitmqproducer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.util.Objects;

@Configuration
public class DepartmentConfig extends RabbitMQConfig {

    private final Environment environment;

    @Autowired
    public DepartmentConfig(Environment environment) {
        Objects.requireNonNull(environment.getProperty("department.exchange.name"), "Department Exchange Name cannot be empty. Please check application.yml");
        Objects.requireNonNull(environment.getProperty("department.exchange.type"), "Department Exchange Type cannot be empty. Please check application.yml");
        Objects.requireNonNull(environment.getProperty("department.queue.hr.name"), "Hr Queue Name cannot be null.Please check the application.yml");
        Objects.requireNonNull(environment.getProperty("department.queue.marketing.name"), "Marketing Queue Name cannot be null.Please check the application.yml");

        this.environment = environment;
    }

    @Bean
    public Queue hrQueue() {
        return createQueue(environment.getProperty("department.queue.hr.name"));
    }

    @Bean
    public Queue marketingQueue() {
        return createQueue(environment.getProperty("department.queue.marketing.name"));
    }

    @Bean
    public Exchange departmentExchange() {
        return createExchange(environment.getProperty("department.exchange.name"), environment.getProperty("department.exchange.type"));
    }

    @Bean
    public Binding hrBinding(Exchange departmentExchange, Queue hrQueue) {
        Assert.isTrue(departmentExchange.getType().equalsIgnoreCase("Fanout"), "Department Exchange should be Fanout");

        return createBindingWithoutRoutingKey((FanoutExchange) departmentExchange, hrQueue);
    }

    @Bean
    public Binding marketingBinding(Exchange departmentExchange, Queue marketingQueue) {
        Assert.isTrue(departmentExchange.getType().equalsIgnoreCase("Fanout"), "Department Exchange should be Fanout");

        return createBindingWithoutRoutingKey((FanoutExchange) departmentExchange, marketingQueue);
    }
}
