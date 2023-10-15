package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.model.OrderDto;
import com.ecommerce.paymentservice.model.PaymentDto;

public interface OrderManagementService {

	PaymentDto newPayment(OrderDto order);

	PaymentDto rollBackPayment(OrderDto order);
}
