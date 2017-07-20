package editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Cursor {
    private Rectangle cursor;

    public Cursor() {
        cursor = new Rectangle(0, 0, 3, 15);
    }

    public void setHeightAndWidth(double w, double h) {
        cursor.setWidth(w);
        cursor.setHeight(h);
    }

    public double getX() {
        return cursor.getX();
    }

    public void setX(double x) {
        cursor.setX(x);
    }

    public double getY() {
        return cursor.getY();
    }

    public void setY(double y) {
        cursor.setY(y);
    }

    public double getWidth() {
        return cursor.getWidth();
    }

    public double getHeight() {
        return cursor.getHeight();
    }

    public Rectangle getCursor() {
        return cursor;
    }

    /**
     * Makes the text bounding box change color periodically.
     */
    public void makeRectangleColorChange() {
        // Create a Timeline that will call the "handle" function of RectangleBlinkEventHandler
        // every 1 second.
        final Timeline timeline = new Timeline();
        // The rectangle should continue blinking forever.
        timeline.setCycleCount(Timeline.INDEFINITE);
        RectangleBlinkEventHandler cursorChange = new RectangleBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), cursorChange);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private class RectangleBlinkEventHandler implements EventHandler<ActionEvent> {
        private int currentColorIndex = 0;
        private Color[] boxColors =
                {Color.BLACK, Color.WHITE};

        RectangleBlinkEventHandler() {
            // Set the color to be the first color in the list.
            changeColor();
        }

        private void changeColor() {
            cursor.setFill(boxColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % boxColors.length;
        }

        @Override
        public void handle(ActionEvent event) {
            changeColor();
        }
    }
}
