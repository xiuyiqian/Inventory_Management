package com.example.Order_Management.service;

import com.example.Order_Management.allModels.*;
import com.example.Order_Management.repository.orderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class orderService {

    private final orderRepository orderRepository;
    private final WebClient webClient;

    @Autowired
    public orderService(orderRepository repository, WebClient webClient) {
        this.webClient = webClient;
        this.orderRepository = repository;
    }

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderOneItem> listOrders= orderRequest.getOrderLists_Copy()
                .stream()
                .map(this::mapToRealOrder)
                .toList();
        order.setOrderLists(listOrders);
        System.out.println("order has been set from request");
        List<String> skuCodeList = order.getOrderLists().stream().
                map(OrderOneItem::getSkuCode).toList();
        System.out.println("skuCode List: "+skuCodeList);
        //check storage availability
        StorageResponse[] storageResponsesList = webClient.get()
                .uri("http://localhost:8082/api/v2/storage",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build())
                .retrieve()
                .bodyToMono(StorageResponse[].class)
                .block();
        if (storageResponsesList.length != skuCodeList.size()){
            throw new IllegalArgumentException("Sorry, Product is not in stock");
        }
        //System.out.println("Storage Reponse List " + storageResponsesList[0].toString());

        boolean allAvailableItems = Arrays.stream(storageResponsesList).allMatch(
                StorageResponse::isInStock
        );

        if (Boolean.TRUE.equals(allAvailableItems)) {
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Sorry, Product is not in stock");
        }

    }

    private OrderOneItem mapToRealOrder(OrderOneItem_Copy orderOneItemCopy) {
        OrderOneItem orderOneItem = new OrderOneItem();
        orderOneItem.setPrice(orderOneItemCopy.getPrice());
        orderOneItem.setQuantity(orderOneItemCopy.getQuantity());
        orderOneItem.setSkuCode(orderOneItemCopy.getSkuCode());
        return orderOneItem;
    }

}
