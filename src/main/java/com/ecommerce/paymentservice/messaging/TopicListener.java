package com.ecommerce.paymentservice.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecommerce.paymentservice.model.OrderDto;
import com.ecommerce.paymentservice.model.OrderStatus;
import com.ecommerce.paymentservice.model.PaymentDto;
import com.ecommerce.paymentservice.service.OrderManagementService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

	private final TopicProducer topicProducer;

	@Value("${consumer.config.topic.name")
	private String topicName;

	@Autowired
	OrderManagementService orderManagementService;

	@KafkaListener(id = "${consumer.config.topic.name}", topics = "${consumer.config.topic.name}", groupId = "${consumer.config.group-id}")
	public void consume(ConsumerRecord<String, OrderDto> payload) {
		log.info("Topic : {}", topicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Order : {}", payload.value());

		OrderDto order = payload.value();

		if (order.getStatus().equals(OrderStatus.PLACED)) {
			PaymentDto savedPayment = orderManagementService.newPayment(order);
			topicProducer.send(savedPayment);
		} else if (order.getStatus().equals(OrderStatus.CANCELLED)) {
			PaymentDto savedPayment = orderManagementService.rollBackPayment(order);
			topicProducer.send(savedPayment);
		}

	}

}