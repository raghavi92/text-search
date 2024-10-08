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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import textsearch.aggregator.Aggregator;
import textsearch.ingestion.Injestor;
import textsearch.ingestion.TextLine;
import textsearch.matcher.Matcher;
import textsearch.matcher.TextLineMatch;

public class App {

    private Map<String, List<TextLineMatch>> processTextSearch(String fileURL, List<String> searchTexStrings) {
        try (ForkJoinPool p = new ForkJoinPool(10)) {

            Aggregator aggregator = new Aggregator();

            var matchFutures = new ArrayList<Future<Map<String, List<TextLineMatch>>>>();

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new URI(fileURL).toURL().openStream()))) {

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

                    Matcher m = new Matcher(result, searchTexStrings);
                    var future = p.submit(m);

                    matchFutures.add(future);

                    p.submit(() -> {
                        try {
                            aggregator.aggregate(future.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });

                }
                p.shutdown();

                p.awaitTermination(15, TimeUnit.MINUTES);

            }
            return aggregator.getAggregatedResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        App app = new App();

        var res = app.processTextSearch("https://norvig.com/big.txt", List.of(
                "James", "John", "Robert", "Michael", "William", "David", "Richard",
                "Charles", "Joseph", "Thomas",
                "Christopher", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth",
                "Steven", "Edward", "Brian",
                "Ronald", "Anthony", "Kevin", "Jason", "Matthew", "Gary", "Timothy", "Jose",
                "Larry", "Jeffrey",
                "Frank", "Scott", "Eric", "Stephen", "Andrew", "Raymond", "Gregory",
                "Joshua", "Jerry", "Dennis",
                "Walter", "Patrick", "Peter", "Harold", "Douglas", "Henry", "Carl", "Arthur",
                "Ryan", "Roger"));

        System.out.println(res);
    }

}
