package editor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class Editor extends Application {

    TextContainer textContainer;
    Cursor cursor;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(event -> System.out.println("Hello World!"));

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        Group textRoot = new Group();
        root.getChildren().add(textRoot);

        Scene scene = new Scene(root, 300, 250);

        scene.setOnKeyTyped(this::handleKey);

        cursor = new Cursor(0, 0, 10);
        textContainer = new TextContainer(textRoot);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleKey(KeyEvent event) {
        String c = event.getCharacter();
        addToText(c);
    }

    private void addToText(String input) {
        textContainer.getTextLine(
                cursor.getLinePos()).addAfterPostion(
                cursor.getCharPos(), input);
    }
}