package com.example.Order_Management.controller;

import com.example.Order_Management.allModels.OrderRequest;
import com.example.Order_Management.service.orderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v2/order")
public class orderController {

    private final orderService orderservice;

    @Autowired
    public orderController(orderService orderservice) {
        this.orderservice = orderservice;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderservice.placeOrder(orderRequest);
        return "Order Place Successfully";
    }

}
