package lsg.exceptions;

import lsg.consumables.Consumable;

public class ConsumeEmptyException extends ConsumeException{
    public ConsumeEmptyException(Consumable consumable){
        super("Consumable is empty !", consumable);
    }

    public String toString(){
        return "Consumable is empty !";
    }
}
