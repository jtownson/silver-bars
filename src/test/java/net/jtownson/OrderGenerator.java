package net.jtownson;

import com.google.auto.service.AutoService;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.java.lang.StringGenerator;
import com.pholser.junit.quickcheck.generator.java.math.BigDecimalGenerator;
import com.pholser.junit.quickcheck.internal.generator.EnumGenerator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

/**
 * Generates sample orders for property-based tests.
 */
@AutoService(Generator.class)
public class OrderGenerator extends Generator<Order> {

    private static BigDecimalGenerator decimalGenerator = new BigDecimalGenerator();
    private static StringGenerator stringGenerator = new StringGenerator();
    private static EnumGenerator enumGenerator = new EnumGenerator(OrderType.class);

    public OrderGenerator() {
        super(Order.class);
    }

    @Override
    public Order generate(SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {
        return new Order(
                stringGenerator.generate(sourceOfRandomness, generationStatus),
                decimalGenerator.generate(sourceOfRandomness, generationStatus),
                decimalGenerator.generate(sourceOfRandomness, generationStatus),
                (OrderType) enumGenerator.generate(sourceOfRandomness, generationStatus));
    }
}
