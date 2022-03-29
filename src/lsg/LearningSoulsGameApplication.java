package lsg;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lsg.characters.Character;
import lsg.characters.Hero;
import lsg.characters.Zombie;
import lsg.consumables.food.SuperBerry;
import lsg.exceptions.*;
import lsg.graphics.CSSFactory;
import lsg.graphics.ImageFactory;
import lsg.graphics.panes.AnimationPane;
import lsg.graphics.panes.CreationPane;
import lsg.graphics.panes.HUDPane;
import lsg.graphics.panes.TitlePane;
import lsg.graphics.widgets.characters.renderers.CharacterRenderer;
import lsg.graphics.widgets.characters.renderers.HeroRenderer;
import lsg.graphics.widgets.characters.renderers.ZombieRenderer;
import lsg.graphics.widgets.skills.SkillAction;
import lsg.graphics.widgets.skills.SkillBar;
import lsg.weapons.Sword;


public class LearningSoulsGameApplication extends javafx.application.Application{

    private Scene scene;
    private AnchorPane root;
    private TitlePane gameTitle;
    private CreationPane creationPane;
    private String heroName;
    private AnimationPane animationPane;

    private Hero hero;
    private HeroRenderer heroRenderer;

    private Zombie zombie;
    private ZombieRenderer zombieRenderer;

    private HUDPane hudPane;

    private SkillBar skillBar;

    private BooleanProperty heroCanPlay = new SimpleBooleanProperty(false);
    private IntegerProperty score = new SimpleIntegerProperty();

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Learning Souls Game");

        root = new AnchorPane();

        scene = new Scene(root, 1200, 800);

        stage.setScene(scene);
        stage.setResizable(false);

        buildUI();
        addListeners();

        stage.show();
        startGame();
    }

    private void buildUI(){
        scene.getStylesheets().add(CSSFactory.getStyleSheet("LSG.css"));
        gameTitle = new TitlePane(scene, "Learning Souls Game");

        root.getChildren().addAll(gameTitle);
        AnchorPane.setTopAnchor(gameTitle, 0.0);
        AnchorPane.setLeftAnchor(gameTitle, 0.0);
        AnchorPane.setRightAnchor(gameTitle, 0.0);
        gameTitle.setAlignment(Pos.CENTER);

        creationPane = new CreationPane();
        AnchorPane.setRightAnchor(creationPane, 0.0);
        AnchorPane.setLeftAnchor(creationPane, 0.0);
        AnchorPane.setTopAnchor(creationPane, 0.0);
        AnchorPane.setBottomAnchor(creationPane, 0.0);
        creationPane.setOpacity(0);
        creationPane.setAlignment(Pos.CENTER);
        root.getChildren().addAll(creationPane);

        animationPane = new AnimationPane(root);

        hudPane = new HUDPane();
        AnchorPane.setRightAnchor(hudPane, 0.0);
        AnchorPane.setLeftAnchor(hudPane, 0.0);
        AnchorPane.setBottomAnchor(hudPane, 0.0);
        AnchorPane.setTopAnchor(hudPane, 0.0);
    }

    private void addListeners(){
        creationPane.getNameField().setOnAction(event -> {
            heroName = creationPane.getNameField().getText();
            System.out.println("Nom du héro : " + heroName);
            if(!heroName.isEmpty()){
                root.getChildren().remove(creationPane);
                gameTitle.zoomOut(null);
                play();
            }
        });
    }

    private void startGame(){
        gameTitle.zoomIn(event ->  {
            creationPane.fadeIn((event1 -> {
                ImageFactory.preloadAll((() -> {
                    System.out.println("Préchargement terminé");
                }));
            }));
        });
    }

    private void play(){
        root.getChildren().addAll(animationPane);
        root.getChildren().addAll(hudPane);

        createHero();
        createSkills();

        createMonster((event -> {
            hudPane.getMessagePane().showMessage("FIGHT !");
            heroCanPlay.set(true);
        }));

        hudPane.scoreProperty().bind(score);
    }

    private void createHero(){
        hero = new Hero(heroName);
        hero.setWeapon(new Sword());
        hero.setConsumable(new SuperBerry());

        heroRenderer = animationPane.createHeroRenderer();
        heroRenderer.goTo(animationPane.getPrefWidth()*0.5 - heroRenderer.getFitWidth()*0.65, null);
        hudPane.getHeroStatBar().getLifeBar().progressProperty().bind(hero.lifeRateProperty());
        hudPane.getHeroStatBar().getStamBar().progressProperty().bind(hero.stamRateProperty());
        hudPane.getHeroStatBar().getName().setText(heroName);
        hudPane.getHeroStatBar().getAvatar().setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.HERO_HEAD)[0]);
    }

    private void createMonster(EventHandler<ActionEvent> finishedHandler){
        zombie = new Zombie();

        zombieRenderer = animationPane.createZombieRenderer();
        zombieRenderer.goTo(animationPane.getPrefWidth()*0.5 - zombieRenderer.getBoundsInLocal().getWidth() * 0.15, finishedHandler);
        hudPane.getMonsterStatBar().getLifeBar().progressProperty().bind(zombie.lifeRateProperty());
        hudPane.getMonsterStatBar().getStamBar().progressProperty().bind(zombie.stamRateProperty());
        hudPane.getMonsterStatBar().getName().setText("Sombie");
        hudPane.getMonsterStatBar().getAvatar().setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.ZOMBIE_HEAD)[0]);
        hudPane.getMonsterStatBar().getAvatar().setRotate(30);
    }

    private void createSkills(){
        skillBar = hudPane.getSkillBar();

        skillBar.getTrigger(1).setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.ATTACK_SKILL)[0]);
        skillBar.getTrigger(1).setAction(new SkillAction() {
            @Override
            public void execute() {
                heroAttack();
            }
        });

        skillBar.getTrigger(2).setImage(ImageFactory.getSprites(ImageFactory.SPRITES_ID.RECUPERATE_SKILL)[0]);
        skillBar.getTrigger(2).setAction(new SkillAction() {
            @Override
            public void execute() {
                heroRecuperate();
            }
        });

        skillBar.getConsumableTrigger().setConsumable(hero.getConsumable());
        skillBar.getConsumableTrigger().setAction(new SkillAction() {
            @Override
            public void execute() {
                heroConsume();
            }
        });

        scene.setOnKeyReleased(event -> {
            skillBar.process(event.getCode());
        });

        skillBar.disableProperty().set(!heroCanPlay.getValue());

        heroCanPlay.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                skillBar.disableProperty().set(!heroCanPlay.getValue());
            }
        });
    }

    private void characterAttack(Character agressor, CharacterRenderer agressorR, Character target, CharacterRenderer targetR, EventHandler<ActionEvent> finishedHandler){
        int value = 0;

        try {
            value = agressor.attack();
        } catch (WeaponNullException e) {
            hudPane.getMessagePane().showMessage("Pas d'arme équipé !");
        } catch (WeaponBrokenException e) {
            hudPane.getMessagePane().showMessage("Arme cassée !");
        } catch (StaminaEmptyException e) {
            hudPane.getMessagePane().showMessage("Plus de stamina !");
        }

        int finalValue = value;
        agressorR.attack(event -> {
            target.getHitWith(finalValue);
            if(target.isAlive()){
                targetR.hurt(finishedHandler);
            } else {
                targetR.die(finishedHandler);
            }
        });
    }

    private void heroAttack(){
        heroCanPlay.set(false);
        characterAttack(hero, heroRenderer, zombie, zombieRenderer, event -> {
            finishTurn();
        });
    }

    private void monsterAttack(){
        characterAttack(zombie, zombieRenderer, hero, heroRenderer, event -> {
            if(hero.isAlive()){
                heroCanPlay.set(true);
            } else {
                gameOver();
            }
        });
    }

    private void gameOver(){
        hudPane.getMessagePane().showMessage("GAME OVER");
    }

    private void finishTurn(){
        if(zombie.isAlive()){
            monsterAttack();
        } else {
            animationPane.getChildren().remove(zombieRenderer);
            score.add(1);
            createMonster(event -> {
                monsterAttack();
            });
        }
    }

    private void heroRecuperate(){
        heroCanPlay.set(false);
        hero.recuperate();
        finishTurn();
    }

    private void heroConsume(){
        heroCanPlay.set(false);
        try {
            hero.consume();
        } catch (ConsumeNullException | ConsumeEmptyException | ConsumeRepairNullWeaponException e) {
            hudPane.getMessagePane().showMessage(e.toString());
        }
        finishTurn();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
