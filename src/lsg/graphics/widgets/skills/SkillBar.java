package lsg.graphics.widgets.skills;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.security.Key;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class SkillBar extends HBox{
    private static LinkedHashMap<KeyCode, String> DEFAULT_BINDING = new LinkedHashMap<>();
    private SkillTrigger triggers[];
    private ConsumableTrigger consumableTrigger = new ConsumableTrigger(KeyCode.C, "c", null, null);

    static {
        DEFAULT_BINDING.put(KeyCode.AMPERSAND, "&");
        DEFAULT_BINDING.put(KeyCode.UNDEFINED, "é");
        DEFAULT_BINDING.put(KeyCode.QUOTEDBL, "\"");
        DEFAULT_BINDING.put(KeyCode.QUOTE, "'");
        DEFAULT_BINDING.put(KeyCode.LEFT_PARENTHESIS, "(");
    }

    public SkillBar(){
        init();
        setSpacing(10);
        prefHeight(110);
        setPadding(new Insets(35));
    }

    private void init(){
        triggers = new SkillTrigger[DEFAULT_BINDING.size()];
        int cpt = 0;

        for(KeyCode a : DEFAULT_BINDING.keySet()){
            triggers[cpt] = new SkillTrigger(a, DEFAULT_BINDING.get(a), null, null);
            this.getChildren().addAll(triggers[cpt]);
            cpt++;
        }
        this.getChildren().add(new Rectangle(30, 0));
        this.getChildren().add(consumableTrigger);
    }

    public SkillTrigger getTrigger(int slot){
        return triggers[slot-1];
    }
    public ConsumableTrigger getConsumableTrigger(){
        return consumableTrigger;
    }

    public void process(KeyCode code){
        if(!isDisabled()){
            if(code == KeyCode.C){
                consumableTrigger.trigger();
            } else {
                for(SkillTrigger trigger: triggers){
                    if(trigger.getKeyCode() == code){
                        trigger.trigger();
                    }
                }
            }
        }
    }
}
