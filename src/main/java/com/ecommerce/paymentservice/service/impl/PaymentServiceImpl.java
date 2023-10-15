package com.ecommerce.paymentservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ecommerce.paymentservice.entity.Payment;
import com.ecommerce.paymentservice.exception.EcommerceException;
import com.ecommerce.paymentservice.model.PaymentDto;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import com.ecommerce.paymentservice.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private ModelMapper mapper;

	@Override
	public List<PaymentDto> getAllPayments() {
		List<PaymentDto> payments = new ArrayList<>();
		paymentRepository.findAll().forEach(payment -> {

			payments.add(mapper.map(payment, PaymentDto.class));
		});
		return payments;
	}

	@Override
	public PaymentDto getPaymentById(long id) {
		Optional<Payment> payment = paymentRepository.findById(id);
		return (payment.isPresent() ? mapper.map(payment.get(), PaymentDto.class) : null);
	}

	@Override
	public PaymentDto createPayment(PaymentDto paymentDto) {
		Payment payment = mapper.map(paymentDto, Payment.class);
		return mapper.map(paymentRepository.save(payment), PaymentDto.class);
	}

	@Override
	public PaymentDto updatePayment(long id, PaymentDto paymentDto) {

		Optional<Payment> updatedPayment = paymentRepository.findById(id).map(existingPayment -> {
			Payment payment = mapper.map(paymentDto, Payment.class);
			return paymentRepository.save(existingPayment.updateWith(payment));
		});

		return (updatedPayment.isPresent() ? mapper.map(updatedPayment.get(), PaymentDto.class) : null);
	}

	@Override
	public void deletePayment(long id) {

		PaymentDto paymentDto = getPaymentById(id);

		if (paymentDto == null || paymentDto.getId() == null)
			throw new EcommerceException("payment-not-found", String.format("Payment with id=%d not found", id),
					HttpStatus.NOT_FOUND);

		paymentRepository.deleteById(id);
		LOGGER.info("Payment deleted Successfully");
	}
}
