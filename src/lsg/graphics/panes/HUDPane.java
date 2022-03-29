package lsg.graphics.panes;

import javafx.animation.TranslateTransition;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import lsg.graphics.widgets.characters.statbars.StatBar;
import lsg.graphics.widgets.skills.SkillBar;
import lsg.graphics.widgets.texts.GameLabel;

public class HUDPane extends BorderPane{
    private MessagePane messagePane;
    private StatBar heroStatBar;
    private StatBar monsterStatBar;
    private SkillBar skillBar;
    public IntegerProperty score = new SimpleIntegerProperty();
    private GameLabel scoreLabel;

    public HUDPane(){
        buildTop();
        buildCenter();
        buildBottom();

        score.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scoreLabel.setText(score.toString());
            }
        });
    }

    public MessagePane getMessagePane() {
        return messagePane;
    }

    public StatBar getHeroStatBar() {
        return heroStatBar;
    }

    public StatBar getMonsterStatBar() {
        return monsterStatBar;
    }

    public SkillBar getSkillBar(){
        return skillBar;
    }

    public IntegerProperty scoreProperty(){
        return this.score;
    }

    private void buildTop(){
        BorderPane bp = new BorderPane();
        heroStatBar = new StatBar();
        monsterStatBar = new StatBar();
        monsterStatBar.flip();
        scoreLabel = new GameLabel("0");

        scoreLabel.setScaleX(1.3);
        scoreLabel.setScaleY(1.3);
        TranslateTransition tt = new TranslateTransition();
        tt.setToY(40);
        tt.setNode(scoreLabel);
        tt.setCycleCount(1);
        tt.play();

        heroStatBar.setStyle("-fx-padding: 70px 0 0 0 ");
        monsterStatBar.setStyle("-fx-padding: 70px 0 0 0 ");

        bp.setLeft(heroStatBar);
        bp.setCenter(scoreLabel);
        bp.setRight(monsterStatBar);

        setTop(bp);
    }

    private void buildCenter(){
        messagePane = new MessagePane();
        messagePane.setAlignment(Pos.CENTER);
        setCenter(messagePane);
    }

    private void buildBottom(){
        skillBar = new SkillBar();
        skillBar.setAlignment(Pos.CENTER);
        setBottom(skillBar);
    }
}
