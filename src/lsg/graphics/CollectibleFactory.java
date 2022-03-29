package lsg.graphics;

import javafx.scene.image.Image;
import lsg.bags.Collectible;
import lsg.consumables.drinks.SmallStamPotion;
import lsg.consumables.food.SuperBerry;

public class CollectibleFactory {
    public static Image getImageFor(Collectible collectible){
        System.out.println(collectible.getClass());
        if(collectible.getClass() != SuperBerry.class || collectible.getClass() != SmallStamPotion.class){
            return null;
        } else {
            if(collectible.getClass() == SuperBerry.class) return ImageFactory.getSprites(ImageFactory.SPRITES_ID.SUPER_BERRY)[0];
            else return ImageFactory.getSprites(ImageFactory.SPRITES_ID.SMALL_STAM_POTION)[0];
        }
    }
}
