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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (lineOffset ^ (lineOffset >>> 32));
        result = prime * result + charOffset;
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
        if (charOffset != other.charOffset)
            return false;
        return true;
    }
}
