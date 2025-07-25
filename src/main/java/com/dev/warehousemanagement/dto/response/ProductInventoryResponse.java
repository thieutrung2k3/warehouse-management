package com.dev.warehousemanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryResponse {
    private String categoryName;
    private String productName;
    private BigDecimal quantityOnHand;
    private BigDecimal totalValue;
}
