package lsg.exceptions;

public class StaminaEmptyException extends Exception{
    public StaminaEmptyException(){
        super("No stamina !");
    }

    @Override
    public String toString() {
        return "ACTION HAS NO EFFECT : no stamina !";
    }
}
