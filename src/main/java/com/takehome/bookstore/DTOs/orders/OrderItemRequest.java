package com.takehome.bookstore.DTOs.orders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

    @Valid
    @NotBlank
    @NotNull(message = "Book ID is required")
    private Integer bookId;

    @Valid
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
