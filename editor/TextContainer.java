package editor;

import javafx.scene.Group;

import java.util.LinkedList;

public class TextContainer {
    private LinkedList<ListTextLine> textLines;
    private Group group;

    public TextContainer(Group g) {
        textLines = new LinkedList<>();
        group = g;
    }

    public TextContainer(LinkedList<ListTextLine> lines, Group g) {
        textLines = lines;
        group = g;
    }

    public TextLine getTextLine(int index) {
        return textLines.get(index);
    }

    public void addTextLine(int index, ListTextLine textLine) {
        textLines.add(index, textLine);
        group.getChildren().add(textLine.textObject);
    }

    public TextLine deleteTextLine(int index) {
        return textLines.remove(index);
    }
}
