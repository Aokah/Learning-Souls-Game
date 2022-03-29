package lsg.graphics.widgets.texts;

import javafx.scene.control.Label;
import lsg.graphics.CSSFactory;

public class GameLabel extends Label{
    public GameLabel(String s){
        super(s);
        this.getStylesheets().add(CSSFactory.getStyleSheet("LSGFonts.css"));
        this.getStyleClass().addAll("game-font");
        this.getStyleClass().addAll("game-font-fx");
    }
}
