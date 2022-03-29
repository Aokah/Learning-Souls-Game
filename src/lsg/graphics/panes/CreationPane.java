package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.widgets.texts.GameLabel;

public class CreationPane extends VBox {
    private TextField nameField;
    private GameLabel gameLabel;
    private static final Duration ANIMATION_DURATION = Duration.millis(1500);

    public TextField getNameField() {
        return nameField;
    }

    public CreationPane(){
        gameLabel = new GameLabel("Player Name");
        nameField = new TextField();

        gameLabel.setMaxWidth(Double.MAX_VALUE);
        AnchorPane.setLeftAnchor(gameLabel, 0.0);
        AnchorPane.setRightAnchor(gameLabel, 0.0);
        gameLabel.setAlignment(Pos.CENTER);

        nameField.setMaxWidth(250);


        getChildren().add(gameLabel);
        getChildren().add(nameField);
    }

    public void fadeIn(EventHandler<ActionEvent> finishedHandler){
        FadeTransition ft = new FadeTransition(ANIMATION_DURATION, this);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();
    }
}
