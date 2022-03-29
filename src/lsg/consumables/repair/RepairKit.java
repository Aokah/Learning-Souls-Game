package lsg.consumables.repair;

import lsg.consumables.Consumable;
import lsg.weapons.Weapon;

public class RepairKit extends Consumable{

    public RepairKit(){
        super("Repair Kit", 10, Weapon.DURABILITY_STAT_STRING);
    }

    public int use(){
        setCapacity(getCapacity()-1);
        if(getCapacity()>0) return 1;
        else return 0;
    }
}
