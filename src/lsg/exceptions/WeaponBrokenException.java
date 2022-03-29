package lsg.exceptions;

import lsg.weapons.Weapon;

public class WeaponBrokenException extends Exception{
    private Weapon weapon;

    public WeaponBrokenException(Weapon weapon){
        super(weapon + " is broken !");
        this.weapon = weapon;
    }

    public Weapon getWeapon(){
        return this.weapon;
    }

    public String toString(){
        return weapon.toString() + " is broken !";
    }
}
