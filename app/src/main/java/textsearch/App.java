/*
 * This source file was generated by the Gradle 'init' task
 */
package textsearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

import textsearch.aggregator.Aggregator;
import textsearch.ingestion.Injestor;
import textsearch.ingestion.TextLine;
import textsearch.matcher.Matcher;
import textsearch.matcher.TextLineMatch;

public class App {

    public static void main(String[] args) {

        try (ForkJoinPool p = new ForkJoinPool(10)) {

            Aggregator aggregator = new Aggregator();

            List<Future> matchFutures = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new URI("http://norvig.com/big.txt").toURL().openStream()))) {

                boolean moreToRead = true;
                int offset = 0;
                int batchSize = 100;

                while (moreToRead) {
                    Injestor i = new Injestor(br, batchSize, offset);
                    ForkJoinTask<List<TextLine>> futureResult = p.submit(i);

                    offset += batchSize;

                    List<TextLine> result = futureResult.get();

                    if (result.isEmpty()) {
                        moreToRead = false;
                        break;
                    }

                    Matcher m = new Matcher(result, List.of("concurrence", "firelight"));
                    ForkJoinTask<Map<String, List<TextLineMatch>>> future = p.submit(m);

                    matchFutures.add(future);

                    Map<String, List<TextLineMatch>> res = future.get();

                    aggregator.aggregate(res);

                    // System.out.println(res.get("concurrence"));

                }

                System.out.println(aggregator.getAggregatedResult());
                System.out.println(p.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
