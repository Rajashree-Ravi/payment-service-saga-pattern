package com.ecommerce.paymentservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.paymentservice.model.OrderDto;
import com.ecommerce.paymentservice.model.PaymentDto;
import com.ecommerce.paymentservice.model.PaymentStatus;
import com.ecommerce.paymentservice.service.OrderManagementService;
import com.ecommerce.paymentservice.service.PaymentService;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderManagementServiceImpl.class);

	@Autowired
	private PaymentService paymentService;

	/**
	 * process payment
	 * 
	 * @param order
	 * @return if payment was processed successfully, set payment status to
	 *         COMPLETED, else FAILED
	 */
	@Override
	public PaymentDto newPayment(OrderDto order) {
		// Try to make payment using some payment gateway
		PaymentStatus paymentStatus = PaymentStatus.randomPayment();

		PaymentDto payment = paymentService.createPayment(new PaymentDto(paymentStatus, order.getId()));
		LOGGER.debug("Payment {}", payment.toString());
		return payment;
	}

	/**
	 * process rollback
	 * 
	 * @param order
	 * @return if payment was processed successfully, set payment status to
	 *         COMPLETED, else FAILED
	 */
	@Override
	public PaymentDto rollBackPayment(OrderDto order) {				
		PaymentDto payment = new PaymentDto();
		LOGGER.debug("Payment refunded {}", payment.toString());
		
		// Add logic to refund

		return payment;
	}

}
