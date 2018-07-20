package editor;

import javafx.scene.text.Text;

public class TextLine {
    protected String textString;
    protected Text textObject;
    protected int maxWidth;
    protected WordWrapStruct wrapStruct;

    public TextLine() {
        textString = "";
        textObject = new Text();
        wrapStruct = new WordWrapStruct(-1, "", false);
    }

    public TextLine(String txtStr, Text txtObj) {
        textString = txtStr;
        textObject = txtObj;
        wrapStruct = new WordWrapStruct(-1, "", false);
    }

    public void removeAtPosition(int index) {
        textString = textString.substring(0, index - 1) + textString.substring(index + 1);
        sync();
    }

    public void removeAtPositions(int startIndex) {
        textString = textString.substring(0, startIndex - 1);
        sync();
    }

    public void removeAtPositions(int startIndex, int endIndex) {
        textString = textString.substring(0, startIndex - 1) + textString.substring(endIndex + 1);
        sync();
    }

    public void addAfterPostion(int index, String txt) {
        textString = textString.substring(0, index) + txt + textString.substring(index + 1);
        sync();
    }

    public void addAfterPostion(int index, char c) {
        textString = textString.substring(0, index) + c + textString.substring(index + 1);
        sync();
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    private void sync() {
        textObject.setText(textString);
        if (maxWidth < textObject.getLayoutBounds().getWidth()) {
            wrapStruct = generateWrapStruct();
        }
    }

    private WordWrapStruct generateWrapStruct() {
        int i = textString.length() - 1;
        StringBuilder textToWrap = new StringBuilder();
        Text temp = textObject;
        char[] tempTxt = temp.getText().toCharArray();

        while (temp.getLayoutBounds().getWidth() > getMaxWidth()) {
            textToWrap.insert(0, tempTxt[i]);
            i--;
        }

        tempTxt = textToWrap.toString().toCharArray();

        int j = 0;
        int k = 0;
        while (j < textToWrap.length()) {
            if (tempTxt[k] == ' ') {
                if (j == 0 || tempTxt[j - 1] == ' ') {
                    textToWrap.deleteCharAt(j);
                    j--;
                } else j++;
            }
            k++;
            j++;
        }

        return new WordWrapStruct(i, textToWrap.toString(), true);
    }

    public WordWrapStruct getWrapStruct() {
        return wrapStruct;
    }


    class WordWrapStruct {
        int indexInLine;
        String textToWrap;
        boolean needWrap;

        public WordWrapStruct(int index, String text, boolean needW) {
            indexInLine = index;
            textToWrap = text;
            needWrap = needW;
        }
    }
}
