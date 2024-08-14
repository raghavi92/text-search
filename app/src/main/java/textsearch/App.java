/*
 * This source file was generated by the Gradle 'init' task
 */
package textsearch;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import textsearch.matcher.Matcher;

public class App {

    public static void main(String[] args) {

        ForkJoinPool p = new ForkJoinPool(10);

        Matcher m = new Matcher(
                List.of("testing my concurrency", "concurrency model in java", "parallelism vs concurrency"),
                List.of("concurrency"));

        Map<String, List<Integer>> result = p.invoke(m);

        

        System.out.println(result);
    }
}
