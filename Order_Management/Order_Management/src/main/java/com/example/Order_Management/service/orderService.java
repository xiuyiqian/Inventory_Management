package com.example.Order_Management.service;

import com.example.Order_Management.allModels.Order;
import com.example.Order_Management.allModels.OrderOneItem;
import com.example.Order_Management.allModels.OrderOneItem_Copy;
import com.example.Order_Management.allModels.OrderRequest;
import com.example.Order_Management.repository.orderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class orderService {

    private final orderRepository orderRepository;

    @Autowired
    public orderService(orderRepository repository) {
        this.orderRepository = repository;
    }

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderOneItem> listOrders= orderRequest.getOrderLists_Copy()
                .stream()
                .map(orderOneItemCopy -> mapToRealOrder(orderOneItemCopy))
                .toList();
        order.setOrderLists(listOrders);
        orderRepository.save(order);
    }

    private OrderOneItem mapToRealOrder(OrderOneItem_Copy orderOneItemCopy) {
        OrderOneItem orderOneItem = new OrderOneItem();
        orderOneItem.setPrice(orderOneItemCopy.getPrice());
        orderOneItem.setQuantity(orderOneItemCopy.getQuantity());
        orderOneItem.setSkuCode(orderOneItemCopy.getSkuCode());
        return orderOneItem;
    }
}
