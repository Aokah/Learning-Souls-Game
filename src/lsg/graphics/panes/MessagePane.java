package lsg.graphics.panes;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.widgets.texts.GameLabel;

public class MessagePane extends VBox {

    public void showMessage(String msg){
        GameLabel gameLabel = new GameLabel(msg);
        this.getChildren().addAll(gameLabel);

        TranslateTransition tt = new TranslateTransition(Duration.millis(3000));
        tt.setToY(this.getLayoutX() - 200);

        FadeTransition ft = new FadeTransition(Duration.millis(3000));
        ft.setToValue(0);

        ParallelTransition pt = new ParallelTransition(tt, ft);
        pt.setNode(this);
        pt.setOnFinished((event -> this.getChildren().remove(gameLabel)));
        pt.setCycleCount(2);
        pt.setAutoReverse(true);
        pt.play();
    }
}
