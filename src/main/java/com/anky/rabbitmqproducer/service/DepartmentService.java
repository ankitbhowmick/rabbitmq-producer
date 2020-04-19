package com.anky.rabbitmqproducer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
public class DepartmentService {

    private final RabbitTemplate rabbitTemplate;

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    private final Exchange departmentExchange;

    @Autowired
    public DepartmentService(RabbitTemplate rabbitTemplate, Exchange departmentExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.departmentExchange = departmentExchange;
    }

    @Scheduled(fixedRate = 200)
    public void sendMessageToDepartmentExchange() {
        log.info("Sending message to {}  with messageSequence : {}", departmentExchange.getName(), atomicInteger.get());
        rabbitTemplate.convertSendAndReceive(departmentExchange.getName(), "", "JPG Image : " + atomicInteger.getAndIncrement());
    }

}
