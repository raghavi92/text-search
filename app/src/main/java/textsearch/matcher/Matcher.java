package textsearch.matcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class Matcher extends RecursiveTask<Map<String, List<Integer>>> {

    private List<String> inputText;
    private List<String> matchText;
    private int start, end;

    public Matcher(List<String> inputText, List<String> matchText, int start, int end) {
        this.inputText = inputText;
        this.matchText = matchText;
        this.start = start;
        this.end = end;
    }

    public Matcher(List<String> inputText, List<String> matchText) {
        this.inputText = inputText;
        this.matchText = matchText;
        this.start = 0;
        this.end = inputText.size();
    }

    public Map<String, List<Integer>> match(String inputLine) {
        Map<String, List<Integer>> res = new HashMap<>();
        matchText.forEach(t -> {
            int index = inputLine.indexOf(t);
            List<Integer> l = new ArrayList<Integer>();

            while (index >= 0) {
                l.add(index);
                index = inputLine.indexOf(t, index + 1);
            }

            res.put(t, l);
        });

        return res;
    }

    @Override
    protected Map<String, List<Integer>> compute() {
        var res = new HashMap<String, List<Integer>>();

        System.out.println(end);
        System.out.println(start);

        if (end - start <= 1) {
            return match(inputText.get(start));
        }

        int mid = (start + end) / 2;

        Matcher m = new Matcher(inputText, matchText, start, mid);

        Matcher m2 = new Matcher(inputText, matchText, mid, end);
        m.fork();
        m2.fork();

        var mOutput = m.join();

        var m2Output = m2.join();

        mOutput.forEach((k, v) -> {
            res.merge(k, v, (i, j) -> {
                i.addAll(j);
                return i;
            });
        });

        m2Output.forEach((k, v) -> {
            res.merge(k, v, (i, j) -> {
                i.addAll(j);
                return i;
            });
        });

        return res;

    }
}
