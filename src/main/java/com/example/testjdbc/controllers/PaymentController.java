package com.example.testjdbc.controllers;

import java.util.List;

import com.example.testjdbc.entities.Payment;
import com.example.testjdbc.repositories.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api")
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @GetMapping(value="/payment", produces="application/json")
    public List<Payment> displayPayments() {
        return paymentRepository.getAllPayments();
    }

    @GetMapping(path="/booking/{id}/payments", produces="application/json")
    public List<Payment> displayPaymentById(@PathVariable("id") int bookingId){
        return paymentRepository.getPaymentById(bookingId);
    }
}