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
    private Group group;

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
        group = new Group();
        Scene scene = new Scene(group, 300, 400, Color.WHITE);
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class KeyEventHandler implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {

            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {

            }
        }
    }
}