package textsearch.matcher;

public class TextLineMatch {
    private final long lineOffset;
    private final int charOffset;

    public TextLineMatch(long lineOffset, int charOffset) {
        this.lineOffset = lineOffset;
        this.charOffset = charOffset;
    }

    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + ", charOffset=" + charOffset + "]";
    }
}
