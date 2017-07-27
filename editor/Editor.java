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
    private static boolean needWordWrap = false;
    private static Cursor cursor = new Cursor();
    private static int fontSize = 12;
    private static double currentX;
    private static double currentY;
    private static TextBuffer textBuffer;
    private static double maxX = 300;
    private Group group;

    public static void main(String[] args) {
        launch(args);
    }

    static void updateCursor() {
        if (textBuffer.size() > 0) {
            //System.out.println(textBuffer.getLetter().getLayoutBounds().getWidth() + "  " + Math.round(textBuffer.getLetter().getLayoutBounds().getWidth()));
            currentX = textBuffer.getLetter().getX() + Math.round(textBuffer.getLetter().getLayoutBounds().getWidth());
            currentY = textBuffer.getLetter().getY();
            cursor.setX(currentX);
            cursor.setY(currentY - cursor.getHeight());
        } else {
            currentX = 5;
            currentY = fontSize;
            cursor.setX(currentX);
            cursor.setY(currentY - cursor.getHeight());
        }
        cursor.setHeightAndWidth(1, fontSize);
        if (needWordWrap) {
            wordWrap();
            needWordWrap = false;
        }
    }

    static private void wordWrap() {
        LinkedList<LinkedList<Text>> text_words = splitIntoWords();
        for (LinkedList<Text> list : text_words) {
            boolean over_screen = false;
            double width = (list.getLast().getX() + list.getLast().getX()) - list.getFirst().getX();
            for (Text text : list) {
                if (text.getX() + Math.round(text.getLayoutBounds().getWidth()) > maxX - 5) {
                    over_screen = true;
                    break;
                }
            }
            if (over_screen) {
                double cur_x = 5;
                for (Text text : list) {
                    text.setY(text.getY() + fontSize);
                    text.setX(cur_x);
                    cur_x += Math.round(text.getLayoutBounds().getWidth());
                }
            }
        }
    }

    static private LinkedList<LinkedList<Text>> splitIntoWords() {
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

    @Override
    public void start(Stage primaryStage) {
        currentX = 5;
        currentY = fontSize;
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
        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {maxX = newSceneWidth.doubleValue(); needWordWrap = true;});
        //scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight)
        // -> System.out.println("Height: " + newSceneHeight));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void change_font(int size) {
        /*
        int oldFontSize = fontSize;
        fontSize = size;
        currentX = 5;
        currentY = fontSize;
        if (size > oldFontSize && Math.abs(size - oldFontSize) >= 4) {
            for (Text text : textBuffer.getBuffer()) {
                double width = Math.round(text.getLayoutBounds().getWidth()) / 0.875;
                if (text.getY() >= oldFontSize + currentY) {
                    currentY += fontSize;
                }
                text.setStyle("-fx-font:" + fontSize + " Verdana;");
                text.setX(currentX);
                text.setY(currentY);
                currentX += width;
            }
        } else if (size < oldFontSize && Math.abs(size - oldFontSize) >= 4) {
            for (Text text : textBuffer.getBuffer()) {
                double width = Math.round(text.getLayoutBounds().getWidth()) * 0.875;
                if (text.getY() >= oldFontSize + currentY) {
                    currentY += fontSize;
                }
                text.setStyle("-fx-font:" + fontSize + " Verdana;");
                text.setX(currentX);
                text.setY(currentY);
                currentX += width;
            }
        } else {
            for (Text text : textBuffer.getBuffer()) {
                double width = Math.round(text.getLayoutBounds().getWidth());
                text.setStyle("-fx-font:" + fontSize + " Verdana;");
                text.setX(currentX);
                text.setY(currentY);
                currentX += width;
            }
        }
        updateCursor();*/
    }

    private class KeyEventHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                if (keyEvent.getCharacter().length() > 0 && keyEvent.getCharacter().charAt(0) != 8) {
                    needWordWrap = true;
                    Text t = new Text();
                    t.setText(keyEvent.getCharacter());
                    t.setStyle("-fx-font:" + fontSize + " Verdana;");
                    t.setY(currentY);
                    t.setX(currentX);
                    group.getChildren().add(t);
                    textBuffer.addLetter(t);
                    updateCursor();
                    wordWrap();
                    keyEvent.consume();
                }
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.BACK_SPACE) {
                    try {
                        group.getChildren().remove(textBuffer.getCurrentPos() + 1);
                        textBuffer.removeLetter();
                        updateCursor();
                        keyEvent.consume();
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }/*else if (code == KeyCode.RIGHT) {
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
                } else if (keyEvent.isShortcutDown()) {
                    if (keyEvent.getCode() == KeyCode.EQUALS) {
                        change_font(fontSize + 4);
                    } else if (keyEvent.getCode() == KeyCode.MINUS) {
                        if (fontSize > 4) change_font(fontSize - 4);
                    } else if (keyEvent.getCode() == KeyCode.P) {
                        System.out.println("x: " + cursor.getX() + ",  y: " + cursor.getY());
                    }
                }*/
            }
        }
    }
}