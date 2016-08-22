package net.jtownson;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import javaslang.collection.List;
import javaslang.collection.Queue;
import javaslang.collection.Seq;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitQuickcheck.class)
public class OrderBoardTest {

    @Property
    public void ordersRegisteredWillBeAvailable(List<Order> orders) {
        // when
        OrderBoard orderBoard = new OrderBoard().register(orders);

        // then
        assertThat(orderBoard.orders().containsAll(orders), is(true));
    }

    @Property
    public void ordersCancelledWillNotBeAvailable(List<Order> orders) {
        // given
        Order cancelledOrder = randomEntry(orders);
        Queue<Order> expectedOrders = Queue.ofAll(orders).remove(cancelledOrder);

        // when
        OrderBoard orderBoard = new OrderBoard()
                                            .register(orders)
                                            .cancel(cancelledOrder);

        Seq<Order> actualOrders = orderBoard.orders();

        // then
        assertThat(actualOrders.contains(cancelledOrder), is(false));
        assertThat(actualOrders.containsAll(expectedOrders), is(true));
    }

    @Property
    public void youCannotCancelAnOrderBeforePlacingIt(List<Order> orders) {
        // given
        Order cancelledOrder = randomEntry(orders);

        // when
        OrderBoard orderBoard = new OrderBoard()
                .cancel(cancelledOrder)
                .register(orders);

        Seq<Order> actualOrders = orderBoard.orders();

        // then
        assertThat(actualOrders.containsAll(orders), is(true));
    }

    @Property
    public void ordersWillBeProcessedInTheOrderInWhichTheyArePlaced(Order o0,
                                                                    Order o1,
                                                                    Order o2) {
        // when
        OrderBoard orderBoard = new OrderBoard().register(o0).register(o1).register(o2);
        Seq<Order> orders = orderBoard.orders();

        // then
        assertThat(orders.get(0), is(o0));
        assertThat(orders.get(1), is(o1));
        assertThat(orders.get(2), is(o2));
    }

    private static <T> T randomEntry(List<T> l) {
        return l.size() == 0 ? null : l.get(new Random().nextInt(l.size()));
    }
}
