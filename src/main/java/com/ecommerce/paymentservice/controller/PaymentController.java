package com.ecommerce.paymentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.paymentservice.exception.EcommerceException;
import com.ecommerce.paymentservice.model.PaymentDto;
import com.ecommerce.paymentservice.service.PaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(produces = "application/json", value = "Operations pertaining to manage payments in e-commerce application")
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@GetMapping
	@ApiOperation(value = "View all payments", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all payments"),
			@ApiResponse(code = 204, message = "Payments list is empty"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<List<PaymentDto>> getAllPayments() {

		List<PaymentDto> payments = paymentService.getAllPayments();
		if (payments.isEmpty())
			throw new EcommerceException("no-content", "Payments list is empty", HttpStatus.NO_CONTENT);

		return new ResponseEntity<>(payments, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Retrieve specific payment with the specified payment id", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved payment with the payment id"),
			@ApiResponse(code = 404, message = "Payment with specified payment id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<PaymentDto> getPaymentById(@PathVariable("id") long id) {

		PaymentDto payment = paymentService.getPaymentById(id);
		if (payment != null)
			return new ResponseEntity<>(payment, HttpStatus.OK);
		else
			throw new EcommerceException("payment-not-found", String.format("Payment with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@PostMapping
	@ApiOperation(value = "Create a new payment", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created a payment"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto payment) {
		PaymentDto savedPayment = paymentService.createPayment(payment);
		return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Update a payment information", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated payment information"),
			@ApiResponse(code = 404, message = "Payment with specified payment id not found"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	public ResponseEntity<PaymentDto> updatePayment(@PathVariable("id") long id, @RequestBody PaymentDto payment) {

		PaymentDto updatedPayment = paymentService.updatePayment(id, payment);
		if (updatedPayment != null)
			return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
		else
			throw new EcommerceException("payment-not-found", String.format("Payment with id=%d not found", id),
					HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete a payment", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted payment information"),
			@ApiResponse(code = 500, message = "Application failed to process the request") })
	private ResponseEntity<String> deletePayment(@PathVariable("id") long id) {

		paymentService.deletePayment(id);
		return new ResponseEntity<>("Payment deleted successfully", HttpStatus.NO_CONTENT);
	}
}
