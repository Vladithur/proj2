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


public class Editor extends Application {
    private Cursor cursor = new Cursor();
    private double currentX;
    private double currentY;
    private TextBuffer textBuffer;
    private Group group;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        currentX = 10;
        currentY = 20;
        textBuffer = new TextBuffer();
        primaryStage.setTitle("Editor");
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);
        EventHandler<KeyEvent> keyEventHandler =
                new Editor.KeyEventHandler();
        group = new Group();
        cursor.setHeightAndWidth(1, 10);
        group.getChildren().add(cursor.getCursor());
        cursor.makeRectangleColorChange();
        cursor.setX(currentX);
        cursor.setY(currentY - cursor.getHeight());
        Scene scene = new Scene(group, 300, 400, Color.WHITE);
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateCursor() {
        if (textBuffer.size() > 0) {
            currentX = textBuffer.getLetter().getX() + textBuffer.getLetter().getLayoutBounds().getWidth();
            currentY = textBuffer.getLetter().getY();
            cursor.setX(currentX);
            cursor.setY(currentY - cursor.getHeight());
        }
        else {
            currentX = 10;
            currentY = 20;
            cursor.setX(currentX);
            cursor.setY(currentY - cursor.getHeight());
        }
    }

    private class KeyEventHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                if (keyEvent.getCharacter().length() > 0 && keyEvent.getCharacter().charAt(0) != 8) {
                    Text t = new Text();
                    t.setX(currentX);
                    t.setY(currentY);
                    t.setText(keyEvent.getCharacter());
                    textBuffer.addLetter(t);
                    group.getChildren().add(t);
                    updateCursor();
                    keyEvent.consume();
                }
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.BACK_SPACE) {
                    if (textBuffer.size() > 0) {
                        group.getChildren().remove(textBuffer.getCurrentPos() + 1);
                        textBuffer.removeLetter();
                        updateCursor();
                        keyEvent.consume();
                    }
                } else if (code == KeyCode.ENTER) {
                    currentX = 10;
                    currentY += 10;
                    cursor.setX(currentX);
                    cursor.setY(currentY - cursor.getHeight());
                } /*else if (code == KeyCode.RIGHT) {
                    textBuffer.setCurrentPos(textBuffer.getCurrentPos() + 1);
                    updateCursor();
                    keyEvent.consume();
                } else if (code == KeyCode.LEFT) {
                    textBuffer.setCurrentPos(textBuffer.getCurrentPos());
                    updateCursor();
                    keyEvent.consume();
                }*/
            }
        }
    }
}