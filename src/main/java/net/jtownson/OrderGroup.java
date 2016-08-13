package net.jtownson;

import lombok.Value;

import java.math.BigDecimal;

@Value
class OrderGroup {
    BigDecimal pricePerKg;
    BigDecimal quantity;
}
