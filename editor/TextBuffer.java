package editor;

import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.LinkedList;

public class TextBuffer {
    private LinkedList<Text> text = new LinkedList<>();
    private int current_pos = 0;

    TextBuffer() {

    }

    void addLetter(Text t) {
        if (text.size() == 0) text.add(t);
        else {
            current_pos++;
            text.add(current_pos, t);
        }
    }

    void removeLetter() {
        if (text.size() == 0) System.out.println("No text!");
        else if (text.size() == 1) text.remove();
        else {
            text.remove(current_pos);
            current_pos--;
        }
    }

    Text getLetter() {
        return text.get(current_pos);
    }

    public Text getLetter(int i) {
        return text.get(i);
    }

    public LinkedList<Text> getBuffer() {
        return text;
    }

    public void setCurrentPos(int i) {
        current_pos = i;
    }

    int getCurrentPos() {
        return current_pos;
    }

    int size() {
        return text.size();
    }

    public String getText() {
        StringBuilder s = new StringBuilder();
        for (Text c : text) {
            s.append(c.getText());
        }
        return s.toString();
    }

    @Override
    public String toString() {
        return Arrays.toString(text.toArray());
    }
}
