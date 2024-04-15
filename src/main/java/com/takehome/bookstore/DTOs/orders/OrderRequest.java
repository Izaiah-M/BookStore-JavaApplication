package com.takehome.bookstore.DTOs.orders;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    @Valid
    @NotBlank(message = "UserId is required")
    @NotNull(message = "UserId should not be null")
    private Integer userId;

    @Valid
    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItemRequest> orderItems;

}
