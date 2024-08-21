package textsearch.matcher;

import textsearch.ingestion.TextLine;

public class TextLineMatch {
    private final long lineOffset;
    private final int charOffsetFromBeginningOfLine;
    private TextLine line;

    public TextLineMatch(long lineOffset, int charOffsetFromBeginningOfLine, TextLine line) {
        this.lineOffset = lineOffset;
        this.charOffsetFromBeginningOfLine = charOffsetFromBeginningOfLine;
        this.line = line;
    }

    @Override
    public String toString() {
        return "[lineOffset=" + lineOffset + ", charOffset=" + getCharOffsetFromBeginningOfTheBatch() + "]";
    }

    public long getCharOffsetFromBeginningOfTheBatch() {
        return line.getCumulativeLengthInBatch() + charOffsetFromBeginningOfLine;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (lineOffset ^ (lineOffset >>> 32));
        result = prime * result + charOffsetFromBeginningOfLine;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TextLineMatch other = (TextLineMatch) obj;
        if (lineOffset != other.lineOffset)
            return false;
        if (charOffsetFromBeginningOfLine != other.charOffsetFromBeginningOfLine)
            return false;
        return true;
    }

}
