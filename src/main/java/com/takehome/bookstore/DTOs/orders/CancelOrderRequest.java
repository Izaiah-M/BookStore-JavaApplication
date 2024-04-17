package com.takehome.bookstore.DTOs.orders;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderRequest {

    @NotNull(message = "Order ID is required")
    private Integer orderId;

}
