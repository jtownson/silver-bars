package net.jtownson;

import com.google.auto.service.AutoService;
import com.pholser.junit.quickcheck.generator.ComponentizedGenerator;
import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.generator.Size;
import com.pholser.junit.quickcheck.internal.Ranges;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import javaslang.collection.List;

/**
 * Generates a javaslang list for property-based tests.
 */
@AutoService(Generator.class)
public class ListGenerator extends ComponentizedGenerator<List> {

    private Size sizeRange;

    public ListGenerator() {
        super(List.class);
    }

    public void configure(Size size) {
        this.sizeRange = size;
        Ranges.checkRange(Ranges.Type.INTEGRAL, size.min(), size.max());
    }

    @Override
    public List generate(
            SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {
        int sampleSize = this.size(sourceOfRandomness, generationStatus);
        return doGenerate(javaslang.collection.List.empty(), sampleSize, sourceOfRandomness, generationStatus);
    }

    private int size(SourceOfRandomness random, GenerationStatus status) {
        return this.sizeRange != null?random.nextInt(this.sizeRange.min(), this.sizeRange.max()):status.size();
    }

    @Override
    public int numberOfNeededComponents() {
        return 1;
    }

    private List doGenerate(
            javaslang.collection.List l, int sampleSize,
            SourceOfRandomness sourceOfRandomness, GenerationStatus generationStatus) {

        while (l.size() < sampleSize) {
            Object generated = componentGenerators().get(0).generate(sourceOfRandomness, generationStatus);
            l = l.prepend(generated);
        }
        return l;
    }
}