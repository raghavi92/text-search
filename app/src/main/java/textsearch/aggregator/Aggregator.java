package textsearch.aggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import textsearch.matcher.TextLineMatch;

public class Aggregator {
    private ConcurrentMap<String, List<TextLineMatch>> aggregatedResult;

    public Aggregator() {
        this.aggregatedResult = new ConcurrentHashMap<String, List<TextLineMatch>>();
    }

    public void aggregate(Map<String, List<TextLineMatch>> partialMap) {
        partialMap.entrySet().forEach(e -> {
            if (e.getValue().isEmpty()) {
                return;
            }
            aggregatedResult.compute(e.getKey(), (k, v) -> {
                if (v == null) {
                    return e.getValue();
                } else {
                    var r = new ArrayList<TextLineMatch>();
                    r.addAll(v);
                    r.addAll(e.getValue());
                    return r;
                }
            });
        });
    }

    public ConcurrentMap<String, List<TextLineMatch>> getAggregatedResult() {
        return this.aggregatedResult;
    }

}
