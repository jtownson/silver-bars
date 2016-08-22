package net.jtownson;

import javaslang.collection.List;
import javaslang.collection.Seq;

import static java.util.function.Predicate.isEqual;
import static javaslang.collection.List.empty;

/**
 * Requirements 1. and 2.
 * 1. Register an order
 * 2. Cancel an order
 * Implemented as a persistent data structure
 * @see <a href="https://en.wikipedia.org/wiki/Persistent_data_structure"/>
 */
class OrderBoard {

    private final List<Order> orders;

    OrderBoard() {
        this(empty());
    }

    private OrderBoard(List<Order> orders) {
        this.orders = orders;
    }

    OrderBoard register(Order order) {
        return new OrderBoard(orders.prepend(order));
    }

    OrderBoard register(List<Order> orders) {
        return orders.foldLeft(this, OrderBoard::register);
    }

    OrderBoard cancel(Order order) {
        return new OrderBoard(orders.filter(isEqual(order).negate()));
    }

    Seq<Order> orders() {
        return orders.reverse();
    }
}
