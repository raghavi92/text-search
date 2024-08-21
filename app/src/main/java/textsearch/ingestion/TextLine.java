package textsearch.ingestion;

public final class TextLine {
    private final long lineIndex;

    private final String content;

    private final long cumulativeLengthInBatch;

    public long getCumulativeLengthInBatch() {
        return cumulativeLengthInBatch;
    }

    public TextLine(long lineIndex, String content, long cumulativeLengthInBatch) {
        this.lineIndex = lineIndex;
        this.content = content;
        this.cumulativeLengthInBatch = cumulativeLengthInBatch;
    }

    public long getLineIndex() {
        return lineIndex;
    }

    public String getContent() {
        return content;
    }
}
