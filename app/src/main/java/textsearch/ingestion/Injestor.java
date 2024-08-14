package textsearch.ingestion;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Injestor implements Callable<List<TextLine>> {

    private BufferedReader br;
    private int batchSize;
    private final long offset;

    public Injestor(BufferedReader br, int batchSize, long offset) {
        this.br = br;
        this.batchSize = batchSize;
        this.offset = offset;
    }

    @Override
    public List<TextLine> call() throws Exception {
        int count = 0;

        String line;
        List<TextLine> lineList = new ArrayList<>();

        while ((line = br.readLine()) != null && count <= batchSize) {
            // System.out.println(String.format("reading line offset %d and line number %d", offset, count));
            lineList.add(new TextLine(offset + count, line));
            count++;
        }
        return lineList;
    }
}
