package textsearch.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import textsearch.ingestion.TextLine;

public class Matcher extends RecursiveTask<Map<String, List<TextLineMatch>>> {

    private List<TextLine> inputText;
    private List<String> matchText;
    private int start, end;

    public Matcher(List<TextLine> inputText, List<String> matchText, int start, int end) {
        this.inputText = inputText;
        this.matchText = matchText;
        this.start = start;
        this.end = end;
    }

    public Matcher(List<TextLine> inputText, List<String> matchText) {
        this.inputText = inputText;
        this.matchText = matchText;
        this.start = 0;
        this.end = inputText.size();
    }

    private Map<String, List<TextLineMatch>> match(TextLine inputLine) {
        Map<String, List<TextLineMatch>> res = new HashMap<>();
        matchText.forEach(t -> {
            int index = inputLine.getContent().indexOf(t);
            var l = new ArrayList<TextLineMatch>();

            while (index >= 0) {
                l.add(new TextLineMatch(inputLine.getLineIndex(), index, inputLine ));
                index = inputLine.getContent().indexOf(t, index + 1);
            }

            res.put(t, l);
        });

        return res;
    }

    @Override
    protected Map<String, List<TextLineMatch>> compute() {

        if (end - start <= 50) {
            var resList = new ArrayList<Map<String, List<TextLineMatch>>>();
            for (int i = start; i < end; i++) {
                resList.add(match(inputText.get(i)));
            }

            return mergeMaps(resList);
        }

        int mid = (start + end) / 2;

        Matcher m = new Matcher(inputText, matchText, start, mid);

        Matcher m2 = new Matcher(inputText, matchText, mid, end);
        m.fork();
        m2.fork();

        var mOutput = m.join();

        var m2Output = m2.join();

        return mergeMaps(List.of(mOutput, m2Output));

    }

    private Map<String, List<TextLineMatch>> mergeMaps(List<Map<String, List<TextLineMatch>>> m1) {
        var res = new HashMap<String, List<TextLineMatch>>();

        for (Map<String, List<TextLineMatch>> map : m1) {
            map.forEach((key, value) -> res.merge(key, new ArrayList<>(value), (existingList, newList) -> {
                existingList.addAll(newList);
                return existingList;
            }));
        }

        return res;

    }
}
