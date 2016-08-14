package net.jtownson;

import javaslang.collection.Queue;
import javaslang.collection.Seq;

import java.math.BigDecimal;
import java.util.Comparator;

import static java.util.Comparator.comparing;
import static net.jtownson.OrderType.SELL;

/**
 * Requirement 3.
 * Summarize an order.
 */
class Summarizer {

    static Seq<OrderGroup> summarize(OrderBoard orderBoard, OrderType orderType) {
        return orderBoard.orders().
                groupBy(Order::getPricePerKg).
                map(keyValuePair -> new OrderGroup(keyValuePair._1, sumQuantity(keyValuePair._2))).
                sorted(accordingTo(orderType));
    }

    private static BigDecimal sumQuantity(Queue<Order> orders) {
        return orders.map(Order::getQuantity).foldLeft(new BigDecimal("0"), BigDecimal::add);
    }

    private static Comparator<OrderGroup> accordingTo(OrderType orderType) {
        return orderType == SELL ?
                comparing(OrderGroup::getPricePerKg) :
                comparing(OrderGroup::getPricePerKg).reversed();
    }
}
