package lsg.exceptions;

import lsg.consumables.Consumable;

public abstract class ConsumeException extends Exception{
    private Consumable consumable;

    public ConsumeException(String msg, Consumable consumable){
        super(msg);
        this.consumable = consumable;
    }

    public Consumable getConsumable(){
        return this.consumable;
    }
}
