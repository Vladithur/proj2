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
import java.util.Objects;


public class Editor extends Application {
    private Cursor cursor = new Cursor();
    private double currentX;
    private double currentY;
    private double maxX = 300;
    private TextBuffer textBuffer;
    private Group group;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        currentX = 5;
        currentY = 0;
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
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> maxX = newSceneWidth.doubleValue());
        //scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight)
        // -> System.out.println("Height: " + newSceneHeight));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateCursor() {
        if (textBuffer.size() > 0) {
            currentX = textBuffer.getLetter().getX() + textBuffer.getLetter().getLayoutBounds().getWidth();
            currentY = textBuffer.getLetter().getY();
            cursor.setX(currentX);
            cursor.setY(currentY - cursor.getHeight());
        } else {
            currentX = 5;
            currentY = 10;
            cursor.setX(currentX);
            cursor.setY(currentY - cursor.getHeight());
        }
    }

    private void update() {
        LinkedList<LinkedList<Text>> text_words = split_into_words();
        System.out.println(text_words.size());
        for (LinkedList<Text> list : text_words) {
            boolean over_screen = false;
            for (Text text : list) {
                if (text.getX() > maxX - 10) {
                    over_screen = true;
                    break;
                }
            }
            if (over_screen) {
                double cur_x = 10;
                for (Text text : list) {
                    text.setY(text.getY() + 10);
                    text.setX(cur_x);
                    cur_x += text.getLayoutBounds().getWidth();
                }
            }
        }
    }

    private LinkedList<LinkedList<Text>> split_into_words() {
        LinkedList<Text> link = new LinkedList<>();
        LinkedList<LinkedList<Text>> list = new LinkedList<>();
        for (Text t : textBuffer.getBuffer()) {
            if (Objects.equals(t.getText(), " ")) {
                list.add(link);
                link = new LinkedList<>();
            } else {
                link.add(t);
            }
        }
        list.add(link);
        return list;
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
                    if (textBuffer.getCurrentPos() < textBuffer.size() - 1) {
                        System.out.println("Wham");
                        for (int i = textBuffer.getCurrentPos() + 1; i < textBuffer.size(); i++) {
                            Text text = textBuffer.getLetter(i);
                            text.setX(text.getX() + t.getLayoutBounds().getWidth());
                        }
                    }
                    updateCursor();
                    //update();
                    keyEvent.consume();
                }
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.BACK_SPACE) {
                    try {
                        group.getChildren().remove(textBuffer.getCurrentPos() + 1);
                        updateCursor();
                        if (textBuffer.getCurrentPos() < textBuffer.size() - 1) {
                            System.out.println("Wham");
                            for (int i = textBuffer.getCurrentPos() + 1; i < textBuffer.size(); i++) {
                                Text text = textBuffer.getLetter(i);
                                text.setX(text.getX() - textBuffer.getLetter().getLayoutBounds().getWidth());
                            }
                        }
                        textBuffer.removeLetter();
                        keyEvent.consume();
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                } else if (code == KeyCode.ENTER) {
                    currentX = 10;
                    currentY += 10;
                    cursor.setX(currentX);
                    cursor.setY(currentY - cursor.getHeight());
                } else if (code == KeyCode.RIGHT) {
                    if (textBuffer.getCurrentPos() < textBuffer.size() - 1) {
                        textBuffer.setCurrentPos(textBuffer.getCurrentPos() + 1);
                        updateCursor();
                    }
                    keyEvent.consume();
                } else if (code == KeyCode.LEFT) {
                    if (textBuffer.getCurrentPos() > -1) {
                        textBuffer.setCurrentPos(textBuffer.getCurrentPos() - 1);
                        updateCursor();
                    }
                    keyEvent.consume();
                }
            }
        }
    }
}