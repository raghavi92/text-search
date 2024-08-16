package textsearch.matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import textsearch.ingestion.TextLine;

public class MatcherTest {
        Matcher matcher = new Matcher(
                        List.of(new TextLine(0, "hello tom"), new TextLine(1, "how are you tom?"),
                                        new TextLine(2, "how are things?")),
                        List.of("tom", "how"));

        @Test
        void testMatch() {

                Map<String, List<TextLineMatch>> m = matcher.compute();

                Assertions.assertEquals(2, m.size());

                m.get("tom");

                assertEquals(m.get("tom").size(), 2);
                assertEquals(m.get("how").size(), 2);

                assertThat(m.get("tom"), containsInAnyOrder(
                                new TextLineMatch(0, 6),
                                new TextLineMatch(1, 12)));

                assertThat(m.get("how"), containsInAnyOrder(
                                new TextLineMatch(1, 0),
                                new TextLineMatch(2, 0)));

        }
}
