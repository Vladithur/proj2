package editor;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;


public class Editor extends Application {
    public LinkedList<Word> words = new LinkedList<>();
    public int fontSize = 15;
    public int index_of_word = -1;
    public int index_in_word = 0;
    public boolean create_new_word = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Editor");
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);
        EventHandler<KeyEvent> keyEventHandler =
                new Editor.KeyEventHandler();
        Group group = new Group();
        for (Word word : words) {
            for (Text text : word.getText()) {
                group.getChildren().add(text);
            }
        }
        Scene scene = new Scene(group, 300, 400, Color.WHITE);
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void addLetter(String letter, Text text) {
        text.setText(letter);
        text.setStyle("-fx-font: " + fontSize + " arial;");
    }

    private class KeyEventHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // capitalization.
                String characterTyped = keyEvent.getCharacter();
                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                    // Ignore control keys, which have zero length, as well as the backspace
                    // key, which is represented as a character of value = 8 on Windows.
                    if (characterTyped.equals(" ")) {
                        Text t = new Text();
                        addLetter(" ", t);
                        index_of_word++;
                        words.add(new Word(t));
                        index_in_word = 0;
                        create_new_word = true;
                        keyEvent.consume();
                    } else if (create_new_word) {
                        Text t = new Text();
                        addLetter(characterTyped, t);
                        index_of_word++;
                        words.add(index_of_word, new Word(t));
                        index_in_word = 0;
                        create_new_word = false;
                        keyEvent.consume();
                    } else {
                        Text t = new Text();
                        addLetter(characterTyped, t);
                        System.out.println(index_of_word + ", " + index_in_word);
                        words.get(index_of_word).addLetter(index_in_word, t);
                        index_in_word++;
                        keyEvent.consume();
                    }
                    Text t = new Text();
                    keyEvent.consume();
                }

                System.out.println(words.toString());

            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.UP) {
                    fontSize += 5;
                } else if (code == KeyCode.DOWN) {
                    fontSize = Math.max(1, fontSize - 5);
                } else if (code == KeyCode.BACK_SPACE) {
                    words.get(index_of_word).removeLetter(index_in_word);
                    index_in_word--;
                }
            }
        }
    }
}