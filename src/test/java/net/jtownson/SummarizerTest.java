package net.jtownson;

import javaslang.collection.Seq;
import javaslang.collection.Stream;
import org.junit.Test;

import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static net.jtownson.OrderType.BUY;
import static net.jtownson.OrderType.SELL;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SummarizerTest {

    @Test
    public void exerciseDescriptionTestCase() {
        Seq<OrderGroup> expectedSummary = Stream.of(
                new OrderGroup(new BigDecimal("306"), new BigDecimal("5.5")),
                new OrderGroup(new BigDecimal("307"), new BigDecimal("1.5")),
                new OrderGroup(new BigDecimal("310"), new BigDecimal("1.2")));

        summaryTestCase(SELL, expectedSummary);

        summaryTestCase(BUY, expectedSummary.reverse());
    }

    private void summaryTestCase(OrderType orderType, Seq<OrderGroup> expectedSummary) {
        Order a = new Order("user1", new BigDecimal("3.5"), new BigDecimal("306"), orderType);
        Order b = new Order("user2", new BigDecimal("1.2"), new BigDecimal("310"), orderType);
        Order c = new Order("user3", new BigDecimal("1.5"), new BigDecimal("307"), orderType);
        Order d = new Order("user4", new BigDecimal("2.0"), new BigDecimal("306"), orderType);

        OrderBoard orderBoard = new OrderBoard().register(asList(a, b, c, d));

        Seq<OrderGroup> summary = Summarizer.summarize(orderBoard, orderType);

        assertThat(summary, is(expectedSummary));
    }
}