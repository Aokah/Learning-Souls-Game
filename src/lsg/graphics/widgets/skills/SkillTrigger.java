package lsg.graphics.widgets.skills;

import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import lsg.graphics.CSSFactory;

import javax.lang.model.type.NullType;

public class SkillTrigger extends AnchorPane {
    private ImageView view;
    private Label text;
    private KeyCode keyCode;
    private SkillAction action;
    private ColorAdjust desaturate;

    public void setAction(SkillAction action) {
        this.action = action;
    }

    public SkillAction getAction() {
        return action;
    }

    public KeyCode getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(KeyCode keyCode) {
        this.keyCode = keyCode;
    }

    public Label getText() {
        return text;
    }

    public void setText(Label text) {
        this.text = text;
    }

    public Image getImage(){
        return view.getImage();
    }

    public void setImage(Image image){
        if(image != null) view.setImage(image);
    }

    public SkillTrigger(KeyCode keyCode, String text, Image image, SkillAction action){
        view = new ImageView();
        view.setImage(image);
        setText(new Label(text));
        setAction(action);
        setKeyCode(keyCode);
        desaturate = new ColorAdjust();
        desaturate.setBrightness(0.6);
        desaturate.setSaturation(-1);
        buildUI();
        addListeners();
    }

    private void buildUI(){
        getStylesheets().add(CSSFactory.getStyleSheet("SkillTrigger.css"));
        this.getStyleClass().addAll("skill");

        if(view != null) {
            view.setFitHeight(50);
            view.setFitWidth(50);

            this.getChildren().add(view);
            AnchorPane.setBottomAnchor(view, 0.0);
            AnchorPane.setLeftAnchor(view, 0.0);
            AnchorPane.setRightAnchor(view, 0.0);
            AnchorPane.setTopAnchor(view, 0.0);
        }

        this.getChildren().add(text);
        text.getStyleClass().addAll("skill-text");
        AnchorPane.setBottomAnchor(text, 0.0);
        AnchorPane.setLeftAnchor(text, 0.0);
        AnchorPane.setTopAnchor(text, 0.0);
        AnchorPane.setRightAnchor(text, 0.0);
        text.setPrefHeight(50);
        text.setPrefWidth(50);
        text.setAlignment(Pos.CENTER);
    }

    public void trigger(){
        if(!isDisabled()){
            this.animate();
            if(action != null) action.execute();
        }
    }

    private void addListeners(){
        this.setOnMouseClicked(event -> {
            this.trigger();
        });
        /*this.disabledProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(disabledProperty().getValue() == true) this.;
                else ;
            }
        });*/
    }

    private void animate(){
        ScaleTransition st = new ScaleTransition(Duration.millis(100));
        st.setToY(1.3);
        st.setToX(1.3);
        st.setNode(this);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }

}
