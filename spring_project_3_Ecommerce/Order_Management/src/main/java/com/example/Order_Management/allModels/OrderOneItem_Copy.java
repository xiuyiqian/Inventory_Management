package com.example.Order_Management.allModels;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderOneItem_Copy {
    private long id;
    private String skuCode;
    private BigDecimal price;
    private int quantity;
}
