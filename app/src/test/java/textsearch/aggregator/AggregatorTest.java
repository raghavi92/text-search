package textsearch.aggregator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.junit.jupiter.api.Test;

import textsearch.matcher.TextLineMatch;

public class AggregatorTest {
    @Test
    void testAggregate() {
        Aggregator aggregator = new Aggregator();

        aggregator.aggregate(Map.of("tom", List.of(new TextLineMatch(0, 6), new TextLineMatch(1, 12)),
                "hi", List.of(new TextLineMatch(1, 25))));

        aggregator.aggregate(Map.of("hi", List.of(new TextLineMatch(500, 25))));

        aggregator.aggregate(Map.of("James", List.of(new TextLineMatch(501, 25))));

        ConcurrentMap<String, List<TextLineMatch>> res = aggregator.getAggregatedResult();

        assertThat(res.keySet(), hasSize(3));

        assertThat(res.get("hi"), containsInAnyOrder(new TextLineMatch(500, 25), new TextLineMatch(1, 25)));
        assertThat(res.get("James"), containsInAnyOrder(new TextLineMatch(501, 25)));
        assertThat(res.get("tom"), containsInAnyOrder(new TextLineMatch(0, 6), new TextLineMatch(1, 12)));

    }
}
