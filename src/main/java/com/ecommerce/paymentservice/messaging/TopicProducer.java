package com.ecommerce.paymentservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ecommerce.paymentservice.model.PaymentDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

	@Value("${producer.config.topic.name}")
	private String topicName;

	private final KafkaTemplate<String, PaymentDto> kafkaTemplate;

	public void send(PaymentDto payment) {
		log.info("Payload : {}", payment.toString());
		kafkaTemplate.send(topicName, payment);
	}

}