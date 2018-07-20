package editor;

public class Cursor {
    private int linePos, charPos, lineHeight;

    public Cursor() {

    }

    public Cursor(int lineP, int charP, int lineH) {
        linePos = lineP;
        charPos = charP;
        lineHeight = lineH;
    }

    public int getLinePos() {
        return linePos;
    }

    public void setLinePos(int linePos) {
        this.linePos = linePos;
    }

    public int getCharPos() {
        return charPos;
    }

    public void setCharPos(int charPos) {
        this.charPos = charPos;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }
}
