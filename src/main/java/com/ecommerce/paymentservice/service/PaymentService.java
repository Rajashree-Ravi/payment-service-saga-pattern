package com.ecommerce.paymentservice.service;

import java.util.List;

import com.ecommerce.paymentservice.model.PaymentDto;

public interface PaymentService {

	List<PaymentDto> getAllPayments();

	PaymentDto getPaymentById(long id);

	PaymentDto createPayment(PaymentDto payment);

	PaymentDto updatePayment(long id, PaymentDto payment);

	void deletePayment(long id);
}
