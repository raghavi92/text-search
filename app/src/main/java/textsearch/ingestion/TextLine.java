package textsearch.ingestion;

public final class TextLine {
    private final long lineIndex;

    private final String content;

    public TextLine(long lineIndex, String content) {
        this.lineIndex = lineIndex;
        this.content = content;
    }

    public long getLineIndex() {
        return lineIndex;
    }

    public String getContent() {
        return content;
    }
}
