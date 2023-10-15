package com.ecommerce.paymentservice.model;

import java.util.Random;

public enum PaymentStatus {
	PENDING, COMPLETED, FAILED;

	private static final Random random = new Random();

	public static PaymentStatus randomPayment() {
		PaymentStatus[] payments = values();
		return payments[random.nextInt(payments.length)];
	}
}
