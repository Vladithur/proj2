package editor;

import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Vladithur on 18/07/2017.
 */
public class Word {
    private double startX;
    private double endX;
    private LinkedList<Text> text;

    public Word(Text... texts) {
        text = new LinkedList<>();
        if (texts.length >= 1) {
            startX = texts[0].getX();
            endX = texts[texts.length - 1].getX() + texts[texts.length - 1].getWrappingWidth();
        }
        text.addAll(Arrays.asList(texts));
    }

    public void addLetter(int i, Text letter) {
        text.add(i, letter);
        synchronize();
    }

    public void removeLetter(int i) {
        if (text.size() > 0) {
            text.remove(i);
            if (text.size() > 0) synchronize();
        }
    }

    public int size() {
        return text.size();
    }

    private void synchronize() {
        startX = text.get(0).getX();
        endX = text.getLast().getX() + text.getLast().getWrappingWidth();
    }

    public LinkedList<Text> getText() {
        return text;
    }

    @Override
    public String toString() {
        String s = "";
        for (Text t : text) {
            s += "Text with \" " + t.getText() + " \"\n";
        }
        return s;
    }
}
