package net.jtownson;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.java.lang.StringGenerator;
import com.pholser.junit.quickcheck.generator.java.math.BigDecimalGenerator;
import com.pholser.junit.quickcheck.internal.generator.EnumGenerator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import javaslang.collection.Queue;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitQuickcheck.class)
public class OrderBoardTest {

    @Property
    public void ordersRegisteredWillBeAvailable(List<@From(OrderGenerator.class) Order> orders) {
        // when
        OrderBoard orderBoard = new OrderBoard().register(orders);

        // then
        assertThat(orderBoard.orders().containsAll(orders), is(true));
    }

    @Property
    public void ordersCancelledWillNotBeAvailable(List<@From(OrderGenerator.class) Order> orders) {
        // given
        Order cancelledOrder = randomEntry(orders);
        Queue<Order> expectedOrders = Queue.ofAll(orders).remove(cancelledOrder);

        // when
        OrderBoard orderBoard = new OrderBoard()
                                            .register(orders)
                                            .cancel(cancelledOrder);

        Queue<Order> actualOrders = orderBoard.orders();

        // then
        assertThat(actualOrders.contains(cancelledOrder), is(false));
        assertThat(actualOrders.containsAll(expectedOrders), is(true));
    }

    @Property
    public void youCannotCancelAnOrderBeforePlacingIt(List<@From(OrderGenerator.class) Order> orders) {
        // given
        Order cancelledOrder = randomEntry(orders);

        // when
        OrderBoard orderBoard = new OrderBoard()
                .cancel(cancelledOrder)
                .register(orders);

        Queue<Order> actualOrders = orderBoard.orders();

        // then
        assertThat(actualOrders.containsAll(orders), is(true));
    }

    @Property
    public void ordersWillBeProcessedInTheOrderInWhichTheyArePlaced(@From(OrderGenerator.class) Order o0,
                                                                    @From(OrderGenerator.class) Order o1,
                                                                    @From(OrderGenerator.class) Order o2) {
        // when
        OrderBoard orderBoard = new OrderBoard().register(o0).register(o1).register(o2);
        Queue<Order> orders = orderBoard.orders();

        // then
        assertThat(orders.get(0), is(o0));
        assertThat(orders.get(1), is(o1));
        assertThat(orders.get(2), is(o2));
    }

    private static <T> T randomEntry(List<T> l) {
        return l.size() == 0 ? null : l.get(new Random().nextInt(l.size()));
    }

    public static class OrderGenerator extends Generator<Order> {

        static BigDecimalGenerator decimalGenerator = new BigDecimalGenerator();
        static StringGenerator stringGenerator = new StringGenerator();
        static EnumGenerator enumGenerator = new EnumGenerator(OrderType.class);

        public OrderGenerator() {
            super(Order.class);
        }

        @Override
        public Order generate(SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {
            return new Order(
                    stringGenerator.generate(sourceOfRandomness, generationStatus),
                    decimalGenerator.generate(sourceOfRandomness, generationStatus),
                    decimalGenerator.generate(sourceOfRandomness, generationStatus),
                    (OrderType)enumGenerator.generate(sourceOfRandomness, generationStatus));
        }
    }
}
