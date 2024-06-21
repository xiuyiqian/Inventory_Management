package com.example.Order_Management.repository;

import com.example.Order_Management.allModels.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface orderRepository extends JpaRepository<Order, Long> {

}
