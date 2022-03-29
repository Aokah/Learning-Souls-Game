package lsg.graphics.widgets.characters.statbars;


import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lsg.graphics.ImageFactory;
import lsg.graphics.widgets.texts.GameLabel;

public class StatBar extends BorderPane {
    private ImageView avatar;
    private GameLabel name;
    private ProgressBar lifeBar;
    private ProgressBar stamBar;

    public GameLabel getName() {
        return name;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public ProgressBar getLifeBar() {
        return lifeBar;
    }

    public ProgressBar getStamBar() {
        return stamBar;
    }

    public void flip(){
        this.setScaleX(-this.getScaleX());
        this.name.setScaleX(-this.name.getScaleX());
    }


    public StatBar(){
        this.setPrefSize(350, 100);
        avatar = new ImageView();
        avatar.setPreserveRatio(true);
        avatar.setFitHeight(100);


        name = new GameLabel("");
        name.setStyle("-fx-font-size: 33px");

        lifeBar = new ProgressBar();
        lifeBar.setMaxWidth(Double.MAX_VALUE);
        lifeBar.setMinWidth(200);
        lifeBar.setStyle("-fx-accent: red");


        stamBar = new ProgressBar();
        stamBar.setMaxWidth(170);
        stamBar.setStyle("-fx-accent: greenyellow");


        BorderPane barPane = new BorderPane(lifeBar,name,null,stamBar,null);
        barPane.setMaxHeight(0);
        barPane.setMaxWidth(Double.MAX_VALUE);

        setTop(null);
        setLeft(avatar);
        setRight(barPane);
        setCenter(null);
        setBottom(null);
    }
}
