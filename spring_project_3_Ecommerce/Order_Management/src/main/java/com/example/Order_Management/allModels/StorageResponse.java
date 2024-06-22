package com.example.Order_Management.allModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StorageResponse {
    private String skuCode;
    private boolean inStock;

    @Override
    public String toString() {
        return "StorageResponse{" +
                "skuCode='" + skuCode + '\'' +
                ", inStock=" + inStock +
                '}';
    }
}
