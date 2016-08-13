package net.jtownson;

import javaslang.collection.Queue;

import java.util.List;

import static java.util.function.Predicate.isEqual;
import static javaslang.collection.Queue.empty;

/**
 * Register an order
 * Cancel an order
 * Implemented as a persistent data structure
 * @see <a href="https://en.wikipedia.org/wiki/Persistent_data_structure"/>
 */
class OrderBoard {

    private final Queue<Order> orders;

    OrderBoard() {
        this(empty());
    }

    private OrderBoard(Queue<Order> orders) {
        this.orders = orders;
    }

    OrderBoard register(Order order) {
        return new OrderBoard(orders.append(order));
    }

    OrderBoard register(List<Order> orders) {
        return Queue.ofAll(orders).foldLeft(this, OrderBoard::register);
    }

    OrderBoard cancel(Order order) {
        return new OrderBoard(orders.filter(isEqual(order).negate()));
    }

    Queue<Order> orders() {
        return orders;
    }
}
