package lsg.characters;

import lsg.characters.Character;
import lsg.weapons.Weapon;

public class Zombie extends Monster{
    public Zombie(){
        super("Zombie");
        setWeapon(new Weapon("Zombie's hands", 5, 20, 1, 1000));
        setSkinThickness(10);
    }
}
