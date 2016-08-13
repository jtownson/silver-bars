package net.jtownson;

import lombok.Value;

import java.math.BigDecimal;

@Value
class Order {
    String userId;
    BigDecimal quantity;
    BigDecimal pricePerKg;
    OrderType orderType;
}
