
package lsg;

import java.util.Scanner;

import lsg.armor.BlackWitchVeil;
import lsg.armor.RingedKnightArmor;
import lsg.bags.SmallBag;
import lsg.consumables.Consumable;
import lsg.consumables.drinks.Drink;
import lsg.consumables.food.Food;
import lsg.consumables.repair.RepairKit;
import lsg.exceptions.*;
import lsg.armor.DragonSlayerLeggings;
import lsg.buffs.rings.DragonSlayerRing;
import lsg.buffs.rings.RingOfDeath;
import lsg.buffs.talismans.MoonStone;
import lsg.characters.Character;
import lsg.characters.Hero;
import lsg.characters.Lycanthrope;
import lsg.characters.Monster;
import lsg.consumables.food.Hamburger;
import lsg.weapons.Sword;
import lsg.weapons.Weapon;

public class LearningSoulsGame {
	
	Scanner scanner = new Scanner(System.in) ;
	
	Hero hero;
	Monster monster;
	public static String BULLET_POINT = "\u2219";
	public static String GAMU_NAMU = "THE LEARNING SOULS GAME";
	public static String GAMU_NAMU_SURROUNDING = "###############################";

	public LearningSoulsGame() {
		title();
		play();
		testExceptions();
		fight1v1();
	}


	private void title(){
		System.out.println(String.format("%-20s \n#   %-24s  #\n%-30s", GAMU_NAMU_SURROUNDING, GAMU_NAMU, GAMU_NAMU_SURROUNDING));
	}

	private void play(){
		hero = new Hero();
		hero.setWeapon(new Sword());
		hero.setArmorItem(new DragonSlayerLeggings(), 1);
		hero.setRing(new RingOfDeath(), 1);
		hero.setRing(new DragonSlayerRing(), 2);
		hero.setConsumable(new Hamburger());
		
		monster = new Lycanthrope() ; // plus besoin de donner la skin et l'arme !
		monster.setTalisman(new MoonStone());
	}
	
	private void fight1v1(){
		
		refresh();
		
		Character agressor = hero;
		Character target = monster;
		int action = 0; // TODO sera effectivement utilise dans une autre version
		int attack, hit;
		Character tmp;
		while(hero.isAlive() && monster.isAlive()){ // ATTENTION : boucle infinie si 0 stamina...
			
			System.out.println();

			if(agressor == hero) {
				System.out.println("Hero's action for next move : (1) attack | (2) consume > ");
				action = scanner.nextInt();
				while((action > 2) || (action < 1)){
					action = scanner.nextInt();
				}

			}

			if(action == 1 || agressor == monster) {
				try {
					attack = agressor.attack();
				} catch(WeaponNullException n){
					attack = 0;
					System.out.println("WARNING : no weapon has been equiped !!\n");
				} catch (WeaponBrokenException b){
					attack = 0;
					System.out.println("WARNING : " + b);
				} catch (StaminaEmptyException s){
					attack = 0;
					System.out.println(s);
				}

				hit = target.getHitWith(attack);


				System.out.printf("%s attacks %s with %s (ATTACK:%d | DMG : %d)\n", agressor.getName(), target.getName(), agressor.getWeapon(), attack, hit);

				System.out.println();
				refresh();
			}

			if(action == 2 && agressor == hero){
				try {
					hero.consume();
				} catch(ConsumeNullException n){
					System.out.println("IMPOSSIBLE ACTION : no consumable has been equiped !");
				} catch(ConsumeEmptyException e){
					System.out.println("ACTION HAS NO EFFECT : " + e.getConsumable().getName() + " is empty !");
				} catch (ConsumeRepairNullWeaponException rnw){
					System.out.println("IMPOSSIBLE ACTION : no weapon has been equiped !");
				}
			}
			
			tmp = agressor ;
			agressor = target ;
			target = tmp ;
			
		}
		
		Character winner = (hero.isAlive()) ? hero : monster ;
		System.out.println();
		System.out.println("--- " + winner.getName() + " WINS !!! ---");
		
	}

	
	private void refresh(){
		hero.printStats();
		System.out.println(hero.armorToString());
		hero.printRings(hero.getRings());
		hero.printConsumable();
		hero.printWeapon();
		hero.printBag();

		System.out.println();

		monster.printStats();
		monster.printWeapon();
	}

	private void refreshHero(){
		hero.printStats();
		System.out.println(String.format("%-1s %-20s", BULLET_POINT, hero.getWeapon()));
		System.out.println(String.format("%-1s %-20s %n", BULLET_POINT, hero.getConsumable()));
	}

	/*public void createExhaustedHero(){
		hero = new Hero();
		hero.getHitWith(99);
		Weapon triste_vie = new Weapon("Grosse Arme", 0, 0, 1000, 100);
		hero.setWeapon(triste_vie);
		try {
			hero.attack();
		} catch(WeaponNullException n) {
			n.printStackTrace();
		} catch (WeaponBrokenException b){
			b.printStackTrace();
		} catch (StaminaEmptyException s){
			s.printStackTrace();
		}
		System.out.println("Create exhausted hero :\n" + hero.toString());
	}*/

	public void testExceptions(){
		Weapon triste_vie = new Weapon("Grosse Arme", 0, 0, 1000, 0);
		hero.setWeapon(null);
		try {
			hero.setBag(new SmallBag());
		} catch (BagFullException e) {
			e.printStackTrace();
		}

		try {
			hero.pickUp(new Hamburger());
			hero.pickUp(new DragonSlayerLeggings());
			hero.pickUp(new RingOfDeath());
			hero.pickUp(new BlackWitchVeil());
			hero.pickUp(new RingedKnightArmor());
		} catch (NoBagException e) {
			e.printStackTrace();
		} catch (BagFullException e) {
			System.out.println("Full bag !");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new LearningSoulsGame();

	}

}
