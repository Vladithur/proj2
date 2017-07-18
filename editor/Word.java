package editor;

import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by Vladithur on 18/07/2017.
 */
public class Word {
    public LinkedList<Text> text;

    public Word(Text... texts) {
        text = new LinkedList<>();
        text.addAll(Arrays.asList(texts));
    }

    public void addLetter(int i, Text letter) {
        text.add(i, letter);
    }

    public void remove(int i) {
        text.remove(i);
    }

    public int size() {
        return text.size();
    }
}
