package com.takehome.bookstore.DTOs.orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private String message;

    private Integer orderId;

    private Double orderAmount;

}
