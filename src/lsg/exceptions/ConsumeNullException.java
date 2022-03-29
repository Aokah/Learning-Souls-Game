package lsg.exceptions;

import lsg.consumables.Consumable;

public class ConsumeNullException extends ConsumeException{
    public ConsumeNullException(Consumable consumable){
        super("Consumable is null !", consumable);
    }

    @Override
    public String toString() {
        return "Consumable is null";
    }
}
