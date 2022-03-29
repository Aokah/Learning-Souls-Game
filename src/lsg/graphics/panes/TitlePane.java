package lsg.graphics.panes;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import lsg.graphics.widgets.texts.GameLabel;


public class TitlePane extends VBox {
    private Scene scene;
    private GameLabel titleLabel;
    private static final Duration ANIMATION_DURATION = Duration.millis(1500);
    private static final double ZOOM_SCALE = 1.5;
    private static final double ZOOM_Y = 0.25;


    public TitlePane(Scene scene, String title){
        this.scene = scene;
        titleLabel = new GameLabel(title);
        this.getChildren().add(titleLabel);
    }

    public void zoomIn(EventHandler<ActionEvent> finishedHandler){
        ScaleTransition st = new ScaleTransition(ANIMATION_DURATION);
        st.setToX(ZOOM_SCALE);
        st.setToY(ZOOM_SCALE);

        TranslateTransition tt = new TranslateTransition(ANIMATION_DURATION);
        tt.setToY(scene.getHeight()*ZOOM_Y);

        ParallelTransition pt = new ParallelTransition(tt, st);
        pt.setNode(titleLabel);
        pt.setCycleCount(1);
        pt.setOnFinished((event -> finishedHandler.handle(event)));
        pt.play();
    }

    public void zoomOut(EventHandler<ActionEvent> finishedHandler){
        ScaleTransition st = new ScaleTransition(ANIMATION_DURATION);
        st.setToX(1);
        st.setToY(1);

        TranslateTransition tt = new TranslateTransition(ANIMATION_DURATION);
        tt.setToY(-scene.getHeight()+800);

        ParallelTransition pt = new ParallelTransition(tt, st);
        pt.setNode(titleLabel);
        pt.setCycleCount(1);
        pt.play();
    }
}
