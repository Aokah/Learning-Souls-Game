package lsg.graphics.widgets.skills;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import lsg.consumables.Consumable;
import lsg.graphics.CSSFactory;
import lsg.graphics.CollectibleFactory;

public class ConsumableTrigger extends SkillTrigger{
    private Consumable consumable;

    public void setConsumable(Consumable consumable){
        this.consumable = consumable;
        consumable.isEmptyProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                setDisable(consumable.isEmptyProperty().get());
            }
        });
    }

    public ConsumableTrigger(KeyCode code, String text, Consumable consumable, SkillAction action){
        super(code, text, null, action);
        this.getStylesheets().addAll(CSSFactory.getStyleSheet("ConsumableTrigger.css"));
        this.getStyleClass().addAll("consumable");
        //this.setImage(CollectibleFactory.getImageFor(consumable));
        //setConsumable(consumable);
    }
}
