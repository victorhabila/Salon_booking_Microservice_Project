package com.projsalony.controller;

import com.razorpay.RazorpayException;
import com.salonyapp.domain.PaymentMethod;
import com.salonyapp.dto.BookingDTO;
import com.salonyapp.dto.UserDTO;
import com.salonyapp.model.PaymentOrder;
import com.salonyapp.payload.response.PaymentLinkResponse;
import com.salonyapp.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@RequestBody BookingDTO booking, @RequestParam PaymentMethod paymentMethod) throws StripeException, RazorpayException {
        UserDTO user = new UserDTO();
        user.setFullName("Ashok");
        user.setEmail("vic@gmail.com");
        user.setId(1L);
        PaymentLinkResponse res = paymentService.createOrder(user, booking, paymentMethod);
        return  ResponseEntity.ok(res);
    }
    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(@PathVariable Long paymentOrderId) throws Exception {

        PaymentOrder res = paymentService.getPaymentOrderById(paymentOrderId);
        return  ResponseEntity.ok(res);

    }
    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId) throws Exception {

        PaymentOrder paymentOrder = paymentService.
                getPaymentOrderByPaymentId(paymentLinkId);
        Boolean success = paymentService.proceedPayment(
                paymentOrder,
                paymentId, paymentLinkId);
        return ResponseEntity.ok(success);

    }
}
