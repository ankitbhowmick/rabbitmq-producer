package com.anky.rabbitmqproducer.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Log4j2
public class ImageService {

    private final RabbitTemplate rabbitTemplate;

    private final AtomicInteger jpegAtomicInteger = new AtomicInteger(1);

    private final AtomicInteger pngAtomicInteger = new AtomicInteger(1);

    private final Exchange imageExchange;

    private final Binding jpgbinding;

    private final Binding pngbinding;

    @Autowired
    public ImageService(RabbitTemplate rabbitTemplate, Exchange imageExchange, @Qualifier("jpegBinding") Binding jpgbinding, @Qualifier("pngBinding") Binding pngbinding) {
        this.rabbitTemplate = rabbitTemplate;
        this.imageExchange = imageExchange;
        this.jpgbinding = jpgbinding;
        this.pngbinding = pngbinding;
    }

    @Scheduled(fixedRate = 100)
    public void sendJpgMessageToQueue() {
        log.info("Sending JPG Image to {} with routingKey {} with imageNo : {}", imageExchange.getName(), jpgbinding.getRoutingKey(), jpegAtomicInteger.get());
        rabbitTemplate.convertAndSend(imageExchange.getName(), jpgbinding.getRoutingKey(), "JPG Image : " + jpegAtomicInteger.getAndIncrement());
    }

    @Scheduled(fixedRate = 150)
    public void sendPngMessageToQueue() {
        log.info("Sending PNG Image to {} with routingKey {} with imageNo : {}", imageExchange.getName(), pngbinding.getRoutingKey(), pngAtomicInteger.get());
        rabbitTemplate.convertAndSend(imageExchange.getName(), pngbinding.getRoutingKey(), "PNG Image : " + pngAtomicInteger.getAndIncrement());
    }
}
