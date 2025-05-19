package com.salonyapp.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.salonyapp.domain.PaymentMethod;
import com.salonyapp.dto.BookingDTO;
import com.salonyapp.dto.UserDTO;
import com.salonyapp.model.PaymentOrder;
import com.salonyapp.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws RazorpayException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;

    PaymentLink createRazorpayPaymentLink(UserDTO user, Long amount, Long orderId) throws RazorpayException;

    String createStripePaymentLink(UserDTO user, Long amount, Long orderId) throws StripeException;

}
