package lsg.exceptions;

import lsg.consumables.Consumable;

public class ConsumeRepairNullWeaponException extends ConsumeException{
    public ConsumeRepairNullWeaponException(Consumable consumable){
        super("Trying to repair null weapon !", consumable);
    }

    public String toString(){
        return "Trying to repair null weapon !";
    }
}
