package editor;

import javafx.scene.text.Text;

public class ListTextLine extends TextLine {
    int index;

    public ListTextLine(String txtStr, Text txtObj, int ind) {
        index = ind;
        textString = txtStr;
        textObject = txtObj;
        wrapStruct = new WordWrapStruct(-1, "", false);
    }
}
