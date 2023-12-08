package screen;

import engine.*;
import entity.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;

import screen.GameScreen;
/**
 * Implements the game screen, where the action happens.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class GameScreen_2P extends Screen {
    /** Sound status on/off. */
    private boolean isSoundOn = true;

    /** Milliseconds until the screen accepts user input. */
    private static final int inputdelay = 6000;
    /** Bonus score for each life remaining at the end of the level. */
    private static final int lifeScore = 100;
    /** Minimum time between bonus ship's appearances. */
    private static final int bonusShipInterval = 20000;
    /** Maximum variance in the time between bonus ship's appearances. */
    private static final int bonusShipVariance = 10000;
    /** Time until bonus ship explosion disappears. */
    private static final int bonusShipExplosion = 500;
    /** Maximum variance in the time between laser's appearances. */
    private int laserInterval = 5000;
    /** Maximum variance in the time between Laser's appearances. */
    private int laserVariance = 1000;
    /** Maximum variance in the time between Laser's appearances. */
    private int laserLoad = 2000;
    /** Time until laser disappears. */
    private static final int laseractivate = 1000;
    /** Time from finishing the level to screen change. */
    private static final int screenChangeInterval = 3000;
    /** Height of the interface separation line. */
    private static final int separationLineHeight = 40;

    /** Current game difficulty settings. */
    private GameSettings gameSettings;
    /** Current difficulty level number. */
    private int level;
    /** Formation of enemy ships. */
    private EnemyShipFormation enemyShipFormation;
    /** Player's ship. */
    private Ship ship1P;
    private Ship ship2P;
    /** Bonus enemy ship that appears sometimes. */
    private BulletLine bulletLine1P;
    private BulletLine bulletLine2P;
    private EnemyShip enemyShipSpecial;
    /** Minimum time between bonus ship appearances. */
    private Cooldown enemyShipSpecialCooldown;
    /** Time until bonus ship explosion disappears. */
    private Cooldown enemyShipSpecialExplosionCooldown;
    /** Time from finishing the level to screen change. */
    private Cooldown screenFinishedCooldown;
    /** Laser */
    private Laser laser;
    /** Laserline */
    private LaserLine laserLine;
    /** Location of next Laser */
    private int nextLaserX;
    /** Minimum time between laser launch */
    private Cooldown laserCooldown;
    /** Load time of laser */
    private Cooldown laserLoadCooldown;
    /** Maintaining time of laser*/
    private Cooldown laserLaunchCooldown;
    /** Laser on/off (difficulty normal, upper than 4level or difficulty hard, hardCore */
    private boolean laserActivate;
    /** Set of all bullets fired by on screen ships. */
    private Set<Bullet> bullets;
    private Set<Bullet> bullets1P;
    private Set<Bullet> bullets2P;
    /** Set of "BulletY" fired by player ships. */
    private Set<BulletY> bulletsY;
    private Set<BulletY> bulletsY1P;
    private Set<BulletY> bulletsY2P;
    /** Sound Effects for player's ship and enemy. */
    private SoundEffect soundEffect;
    /** Add and Modify BGM */
    private BGM bgm;
    /** Current score. */
    private int score1P;
    private int score2P;
    /** Current coin. */
    private Coin coin;
    /** Player lives left. */
    private double lives1p;
    private double lives2p;
    /** Total bullets shot by the player. */
    private int bulletsShot1P;
    private int bulletsShot2P;
    /** Total ships destroyed by the player. */
    private int shipsDestroyed;
    /** Moment the game starts. */
    private long gameStartTime;
    /** Checks if the level is finished. */
    private boolean levelFinished;
    /** Checks if a bonus life is received. */
    private boolean bonusLife;
    /** Checks if the game is hardCore. */
    private boolean hardCore;
    /** Checks if the game is paused. */
    private boolean pause;
    /** Set of all items.*/
    private Set<Item> items;
    /** is none exist dropped item?*/
    private boolean isItemAllEat;
    /** Check what color will be displayed*/
    private int colorVariable;
    /** Current Value of Enhancement  Area. */
    private int attackDamage;
    /** Current Value of Enhancement  Attack. */
    private int areaDamage;
    private boolean isBoss;

    private CountUpTimer timer;

    private int bulletsCount1p=50;
    private int bulletsCount2p=50;
    private GameScreen gameScreen;
    private String clearCoin;
    private ItemManager itemManager;
    private EnhanceManager enhanceManager;
    private int bulletsRemaining1p;
    private int bulletsRemaining2p;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param gameState
     *            Current game state.
     * @param gameSettings
     *            Current game settings.
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public GameScreen_2P(final GameState_2P gameState,
                         final GameSettings gameSettings,
                         final EnhanceManager enhanceManager, final ItemManager itemManager,
                         final int width, final int height, final int fps) {
        super(width, height, fps);

        this.gameSettings = gameSettings;
        this.enhanceManager = enhanceManager;
        this.itemManager = itemManager;
        this.level = gameState.getLevel();
        this.score1P = gameState.getScore1P();
        this.score2P = gameState.getScore2P();
        this.coin = gameState.getCoin();
        this.lives1p = gameState.getLivesRemaining1P();
        this.lives2p = gameState.getLivesRemaining2P();
        //if (this.bonusLife)
        //this.lives++;
        this.bulletsShot1P = gameState.getBulletsShot1P();
        this.bulletsShot2P = gameState.getBulletsShot2P();
        this.shipsDestroyed = gameState.getShipsDestroyed();
        this.hardCore = gameState.getHardCore();
        this.pause = false;
        this.attackDamage = gameSettings.getBaseAttackDamage();
        this.areaDamage = gameSettings.getBaseAreaDamage();
        timer = new CountUpTimer();
        this.bulletsRemaining1p = gameState.getBulletsRemaining1p();
        this.bulletsRemaining2p = gameState.getBulletsRemaining2p();

        this.laserActivate = (gameSettings.getDifficulty() == 1 && getGameState().getLevel() >= 4) || (gameSettings.getDifficulty() > 1);
        if (gameSettings.getDifficulty() > 1) {
            laserInterval = 3000;
            laserVariance = 500;
            laserLoad = 1500;
        }
    }

    /**
     * Initializes basic screen properties, and adds necessary elements.
     */
    public final void initialize() {
        super.initialize();

        enemyShipFormation = new EnemyShipFormation(this.gameSettings, 1);
        enemyShipFormation.attach(this);
        this.ship1P = new Ship(this.width / 4, this.height - 30, "a", Color.WHITE);
        this.bulletLine1P = new BulletLine(this.width / 4 , this.height + 120);
        this.ship2P = new Ship((3 * this.width / 4), this.height - 30, "b", Color.RED);
        this.bulletLine2P = new BulletLine(3 * this.width / 4 , this.height + 120);
        // Appears each 10-30 seconds.
        this.enemyShipSpecialCooldown = Core.getVariableCooldown(
                bonusShipInterval, bonusShipVariance);
        this.enemyShipSpecialCooldown.reset();
        this.enemyShipSpecialExplosionCooldown = Core
                .getCooldown(bonusShipExplosion);
        // Laser appears each (4~6 or 2.5~3.5) seconds, be loaded for 2 or 1.5 seconds and takes a second for launch)
        this.nextLaserX = -1;
        this.laser = null;
        this.laserCooldown = Core.getVariableCooldown(
                laserInterval, laserVariance);
        this.laserCooldown.reset();
        this.laserLoadCooldown = Core
                .getCooldown(laserLoad);
        this.laserLoadCooldown.reset();
        this.laserLaunchCooldown = Core
                .getCooldown(laseractivate);
        this.laserLaunchCooldown.reset();
        this.screenFinishedCooldown = Core.getCooldown(screenChangeInterval);
        this.bullets = new HashSet<Bullet>();
        this.bullets1P = new HashSet<Bullet>();
        this.bullets2P = new HashSet<Bullet>();
        this.bulletsY = new HashSet<BulletY>();
        this.bulletsY1P = new HashSet<BulletY>();
        this.bulletsY2P = new HashSet<BulletY>();
        this.items = new HashSet<Item>();
        this.isItemAllEat = false;

        // Special input delay / countdown.
        this.gameStartTime = System.currentTimeMillis();
        this.inputDelay = Core.getCooldown(inputdelay);
        this.inputDelay.reset();

        soundEffect = new SoundEffect();
        bgm = new BGM();

        bgm.inGameBGMplay();

        drawManager.initBackgroundTimer(this, separationLineHeight); // Initializes timer for background animation.
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        this.score1P += lifeScore * (this.lives1p - 1);
        this.score2P += lifeScore * (this.lives2p - 1);
        this.logger.info("Screen cleared with a score of " + this.score1P + " " + this.score2P);

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {

        if (pause) { // Game Pause, press ENTER to continue or BackSpace to quit
            pause = !inputManager.isKeyDown(KeyEvent.VK_ENTER);
            boolean exit = inputManager.isKeyDown(KeyEvent.VK_BACK_SPACE);
            if (exit) {
                this.returnCode = 1;
                this.lives1p = 0;
                this.lives2p = 0;
                this.isRunning = false;
                bgm.inGameBGMstop();
            }
        }
        else {
            super.update();
            if (this.inputDelay.checkFinished() && !this.levelFinished) {
                pause = inputManager.isKeyDown(KeyEvent.VK_ESCAPE);
                if (!this.ship1P.isDestroyed()) {
                    boolean moveRight = inputManager.isKeyDown(KeyEvent.VK_D);
                    boolean moveLeft = inputManager.isKeyDown(KeyEvent.VK_A);

                    boolean isRightBorder = this.ship1P.getPositionX()
                            + this.ship1P.getWidth() + this.ship1P.getSpeed() > this.width - 1;
                    boolean isLeftBorder = this.ship1P.getPositionX()
                            - this.ship1P.getSpeed() < 1;

                    if (this.ship1P.getSpeed() >= 0)
                    {
                        if (moveRight && !isRightBorder) {
                            this.ship1P.moveRight();
                        }
                        if (moveLeft && !isLeftBorder) {
                            this.ship1P.moveLeft();
                        }
                    } else {
                        if (moveRight && !isLeftBorder) {
                            this.ship1P.moveRight();
                        }
                        if (moveLeft && !isRightBorder) {
                            this.ship1P.moveLeft();
                        }
                    }
                    if (inputManager.isKeyDown(KeyEvent.VK_SHIFT)) {
                        if(bulletsShot1P % 6 == 0 && !(bulletsShot1P == 0)) {
                            if (this.ship1P.shootBulletY(this.bulletsY1P, this.attackDamage)) {
                                soundEffect.playShipShootingSound();
                                this.bulletsShot1P++;
                                this.bulletsCount1p--;
                                this.bulletsRemaining1p--;
                            }
                        }
                        else {
                            if (this.ship1P.shoot(this.bullets1P, this.attackDamage)) {
                                soundEffect.playShipShootingSound();
                                this.bulletsShot1P++;
                                this.bulletsCount1p--;
                                this.bulletsRemaining1p--;
                            }
                        }
                    }
                    if(inputManager.isKeyDown(KeyEvent.VK_B)) {
                        if(ship1P.getBomb()){
                            this.enemyShipFormation.bombDestroy(items);
                            this.ship1P.setBomb(false);
                        }
                    }
                }
                if (!this.ship2P.isDestroyed()) {
                    boolean moveRight = inputManager.isKeyDown(KeyEvent.VK_RIGHT);
                    boolean moveLeft = inputManager.isKeyDown(KeyEvent.VK_LEFT);

                    boolean isRightBorder = this.ship2P.getPositionX()
                            + this.ship2P.getWidth() + this.ship2P.getSpeed() > this.width - 1;
                    boolean isLeftBorder = this.ship2P.getPositionX()
                            - this.ship2P.getSpeed() < 1;

                    if (this.ship2P.getSpeed() >= 0)
                    {
                        if (moveRight && !isRightBorder) {
                            this.ship2P.moveRight();
                        }
                        if (moveLeft && !isLeftBorder) {
                            this.ship2P.moveLeft();
                        }
                    } else {
                        if (moveRight && !isLeftBorder) {
                            this.ship2P.moveRight();
                        }
                        if (moveLeft && !isRightBorder) {
                            this.ship2P.moveLeft();
                        }
                    }
                    if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                        if(bulletsShot2P % 6 == 0 && !(bulletsShot2P == 0)) {
                            if (this.ship2P.shootBulletY(this.bulletsY2P, this.attackDamage)) {
                                soundEffect.playShipShootingSound();
                                this.bulletsShot2P++;
                                this.bulletsCount2p--;
                                this.bulletsRemaining2p--;
                            }
                        }
                        else {
                            if (this.ship2P.shoot(this.bullets2P, this.attackDamage)) {
                                soundEffect.playShipShootingSound();
                                this.bulletsShot2P++;
                                this.bulletsCount2p--;
                                this.bulletsRemaining2p--;
                            }
                        }
                    }
                    if(inputManager.isKeyDown(KeyEvent.VK_V)) {
                        if(ship2P.getBomb()){
                            this.enemyShipFormation.bombDestroy(items);
                            this.ship2P.setBomb(false);
                        }
                    }
                }
                if (this.laserActivate) {
                    if (this.laser != null) {
                        if (this.laserLaunchCooldown.checkFinished()) {
                            this.laser = null;
                            this.laserCooldown.reset();
                            this.nextLaserX = -1;
                            this.logger.info("Laser has disappeared.");
                        }
                    }
                    if (this.laser == null) {
                        if (this.laserLoadCooldown.checkFinished() && this.nextLaserX != -1) {
                            this.laserLaunchCooldown.reset();
                            this.laserLine = null;
                            this.laser = new Laser(this.nextLaserX, separationLineHeight, true);
                            this.logger.info("Laser has been launched.");
                        } else {
                            if (this.nextLaserX == -1 && laserCooldown.checkFinished()) {
                                this.logger.info("Laser will be launched.");
                                this.nextLaserX = (int) (Math.random() * 448);
                                this.laserLine = new LaserLine(this.nextLaserX, separationLineHeight);
                                this.laserLoadCooldown.reset();
                            }
                        }
                    }
                }
                if (this.enemyShipSpecial != null) {
                    if (!this.enemyShipSpecial.isDestroyed())
                        this.enemyShipSpecial.move(2, 0);
                    else if (this.enemyShipSpecialExplosionCooldown.checkFinished())
                        this.enemyShipSpecial = null;

                }
                if (this.enemyShipSpecial == null
                        && this.enemyShipSpecialCooldown.checkFinished()) {
                    bgm.enemyShipSpecialBGMplay();
                    colorVariable = (int)(Math.random()*4);
                    switch (colorVariable) {
                        case 0:
                            this.enemyShipSpecial = new EnemyShip(Color.RED);
                            break;
                        case 1:
                            this.enemyShipSpecial = new EnemyShip(Color.YELLOW);
                            break;
                        case 2:
                            this.enemyShipSpecial = new EnemyShip(Color.BLUE);
                            break;
                        case 3:
                            this.enemyShipSpecial = new EnemyShip(Color.WHITE);
                            break;
                        default:
                            break;
                    }
                    this.enemyShipSpecialCooldown.reset();
                    this.logger.info("A special ship appears");
                }
                if (this.enemyShipSpecial != null
                        && this.enemyShipSpecial.getPositionX() > this.width) {
                    bgm.enemyShipSpecialBGMstop();
                    this.enemyShipSpecial = null;
                    this.logger.info("The special ship has escaped");
                }

                this.ship1P.update();
                this.ship2P.update();
                this.enemyShipFormation.update();
                this.enemyShipFormation.shoot(this.bullets);
            }

            manageCollisions();
            manageCollisionsY();
            cleanBullets();
            cleanBullets1P();
            cleanBullets2P();
            cleanBulletsY();
            cleanBulletsY1P();
            cleanBulletsY2P();
            cleanItems();
            draw();
        }
        if (this.enemyShipFormation.isEmpty() && !this.levelFinished) {
            endStageAllEat();
            bgm.enemyShipSpecialBGMstop();
            this.levelFinished = true;
            this.screenFinishedCooldown.reset();
            timer.stop();
        }
        if(this.lives2p<=0){
            ship2P.destroy();
        }
        if(this.lives1p<=0){
            ship1P.destroy();
        }
        if (this.lives1p <= 0 && !this.levelFinished && this.lives2p<=0) {
            bgm.enemyShipSpecialBGMstop();
            this.levelFinished = true;
            //drawManager.ghost1PostionX = this.ship1P.getPositionX();
            //drawManager.ghost1PostionY = this.ship1P.getPositionY() - 25;
            //drawManager.ghost2PostionX = this.ship2P.getPositionX();
            //drawManager.ghost2PostionY = this.ship2P.getPositionY() - 25;
            //drawManager.endTimer.reset();
            //drawManager.ghostTImer = System.currentTimeMillis();
            soundEffect.playShipDestructionSound();
            this.screenFinishedCooldown.reset();
            timer.stop();
        }


        if ((isItemAllEat || this.levelFinished) && this.screenFinishedCooldown.checkFinished()){
            bgm.inGameBGMstop();
            this.isRunning = false;
            timer.stop();
            if ((int)(timer.getElapsedTime() / 1000) > 0 && (int)(timer.getElapsedTime() / 1000) < 30) {
                this.coin.addCoin(20);
            }
            else if ((int)(timer.getElapsedTime() / 1000) >= 30 && (int)(timer.getElapsedTime() / 1000) < 40) {
                this.coin.addCoin(15);
            }
            else if ((int)(timer.getElapsedTime() / 1000) >= 40 && (int)(timer.getElapsedTime() / 1000) < 50) {
                this.coin.addCoin(10);
            }
            else{
                this.coin.addCoin(5);
            }
        }
        if (this.bulletsCount1p <= 0){
            this.ship1P.destroy();
            this.bulletsCount1p = 0;
        }
        if (this.bulletsCount2p <= 0){
            this.ship2P.destroy();
            this.bulletsCount2p = 0;
        }
        if (this.bulletsCount1p == 0 && this.bulletsCount2p == 0 && !this.levelFinished){
            bgm.enemyShipSpecialBGMstop();
            this.levelFinished = true;
            soundEffect.playShipDestructionSound();
            this.screenFinishedCooldown.reset();
        }
        if((this.lives1p <= 0 && this.lives2p <= 0) && !this.levelFinished
        &&  (this.bulletsCount1p == 0 && this.bulletsCount2p == 0)) {
            bgm.enemyShipSpecialBGMstop();
            this.levelFinished = true;
            soundEffect.playShipDestructionSound();
            this.screenFinishedCooldown.reset();
        }

        timer.update();

    }
    /**
     * when the stage end, eat all dropped item.
     */
    private void endStageAllEat(){
        Cooldown a = Core.getCooldown(25);
//        bgm.inGameBGMstop();
        a.reset();
        while(!this.items.isEmpty()){
            if(a.checkFinished()) {
                manageCollisions();
                for (Item item : this.items) {
                    item.resetItem(this.ship1P);
                }
                a.reset();
            }
            draw();
        }
        isItemAllEat = true;
    }


    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);
        drawManager.drawBackground(this, separationLineHeight, (int)this.lives1p);
        drawManager.drawBackground(this, separationLineHeight, (int)this.lives2p);
        if (this.enemyShipSpecial != null) drawManager.drawBackgroundSpecialEnemy(this, separationLineHeight);
        drawManager.drawBackgroundLines(this, separationLineHeight);
        drawManager.drawBackgroundPlayer(this, separationLineHeight, this.ship1P.getPositionX(), this.ship1P.getPositionY(), this.ship1P.getWidth(), this.ship1P.getHeight());
        drawManager.drawBackgroundPlayer(this, separationLineHeight, this.ship2P.getPositionX(), this.ship2P.getPositionY(), this.ship2P.getWidth(), this.ship2P.getHeight());
        drawManager.bulletsCount1p(this,this.bulletsCount1p);
        drawManager.bulletsCount2p(this, this.bulletsCount2p);
        drawManager.drawEntity(this.ship1P, this.ship1P.getPositionX(),
                this.ship1P.getPositionY());
        drawManager.drawEntity(this.bulletLine1P, this.ship1P.getPositionX() + 12,
                this.ship1P.getPositionY() - 320);

        drawManager.drawEntity(this.ship2P, this.ship2P.getPositionX(),
                this.ship2P.getPositionY());
        drawManager.drawEntity(this.bulletLine2P, this.ship2P.getPositionX() + 12,
                this.ship2P.getPositionY() - 320);

        if (this.enemyShipSpecial != null)
            drawManager.drawEntity(this.enemyShipSpecial,
                    this.enemyShipSpecial.getPositionX(),
                    this.enemyShipSpecial.getPositionY());
        if (this.laser != null)
            drawManager.drawEntity(this.laser,
                    this.laser.getPositionX(),
                    this.laser.getPositionY());
        if (this.laserLine != null)
            drawManager.drawEntity(this.laserLine,
                    this.laserLine.getPositionX(),
                    this.laserLine.getPositionY());
        for (Item item : this.items)
            drawManager.drawEntity(item, item.getPositionX(),
                    item.getPositionY());
        enemyShipFormation.draw();

        for (Bullet bullet : this.bullets)
            drawManager.drawEntity(bullet, bullet.getPositionX(),
                    bullet.getPositionY());

        for (Bullet bullet : this.bullets1P)
            drawManager.drawEntity(bullet, bullet.getPositionX(),
                    bullet.getPositionY());

        for (Bullet bullet : this.bullets2P)
            drawManager.drawEntity(bullet, bullet.getPositionX(),
                    bullet.getPositionY());

        for (BulletY bulletY : this.bulletsY)
            drawManager.drawEntity(bulletY, bulletY.getPositionX(),
                    bulletY.getPositionY());

        for (BulletY bulletY : this.bulletsY1P)
            drawManager.drawEntity(bulletY, bulletY.getPositionX(),
                    bulletY.getPositionY());

        for (BulletY bulletY : this.bulletsY2P)
            drawManager.drawEntity(bulletY, bulletY.getPositionX(),
                    bulletY.getPositionY());


        // Interface.
        drawManager.drawScore2p(this, this.score1P,"p1", 105);
        drawManager.drawScore2p(this, this.score1P + this.score2P,"total", 183);
        drawManager.drawScore2p(this, this.score2P,"p2",260);
        drawManager.drawLivesBar2p(this, this.lives1p, 8, "1P lives");
        drawManager.drawLivesBar2p(this, this.lives2p, 330, "2P lives");
        drawManager.drawCoinCount(this, this.coin, 0);
        drawManager.drawItemCircle(this,itemManager.getShieldCount(),itemManager.getBombCount());
        isBoss = gameSettings.checkIsBoss();

        if (inputManager.isKeyPressedOnce(KeyEvent.VK_1)) {
            if (itemManager.getShieldCount() > 0 && timer.getElapsedTime() != 0 && ship1P.getShieldState() != true && ship2P.getShieldState() != true && !levelFinished)
            {
                logger.info("Key number 1 press");
                itemManager.plusShieldCount(-1);
                ship1P .setShieldState(true);
                ship1P.update();
                ship2P .setShieldState(true);
                ship2P.update();
            }


        }
        else if (inputManager.isKeyPressedOnce(KeyEvent.VK_2) & timer.getElapsedTime() != 0 && enemyShipFormation.isEmpty() == false)
        {
            if (itemManager.getBombCount() > 0)
            {
                logger.info("Key number 2 press");
                itemManager.plusBombCount(-1);
                this.enemyShipFormation.bombDestroy(items);
            }

        }

        if (isBoss) {
            for (EnemyShip enemyShip : this.enemyShipFormation)
                drawManager.drawBossLivesBar(this, enemyShip.getEnemyLife());
        }
        drawManager.drawHorizontalLine(this, separationLineHeight - 1);
        //drawManager.scoreEmoji(this, this.score1P);
        drawManager.drawSoundButton2(this);
        if (inputManager.isKeyDown(KeyEvent.VK_C)) {
            isSoundOn = !isSoundOn;
            if (isSoundOn) {
                bgm.inGameBGMplay();
            } else {
                bgm.inGameBGMstop();
                soundEffect.soundEffectStop();
                bgm.enemyShipSpecialBGMstop();
            }
        }

        drawManager.drawSoundStatus2(this, isSoundOn);
        drawManager.drawTimer(this, timer.getElapsedTime());

        //GameOver
        drawManager.gameOver2p(this, this.levelFinished, this.lives1p, this.lives2p, this.bulletsCount1p, this.bulletsCount2p, this.timer, this.coin, this.clearCoin);
        drawManager.changeGhostColor2p(this.levelFinished, this.lives1p, this.lives2p);
        drawManager.drawGhost2p(this.levelFinished, this.lives1p, this.lives2p);
        this.ship1P.endShipMotion(this.levelFinished, this.lives1p);

        this.ship2P.endShipMotion(this.levelFinished, this.lives2p);


        // Countdown to game start.
        if (!this.inputDelay.checkFinished()) {
            int countdown = (int) ((inputdelay
                    - (System.currentTimeMillis()
                    - this.gameStartTime)) / 1000);
            drawManager.drawCountDown(this, this.level, countdown,
                    this.bonusLife);

            // Fade from white at game start.
            drawManager.drawBackgroundStart(this, separationLineHeight);

            /* this code is modified with Clean Code (dodo_kdy)  */
            //drawManager.drawHorizontalLine(this, this.height / 2 - this.height / 12);
            //drawManager.drawHorizontalLine(this, this.height / 2 + this.height / 12);
        }


        // If Game has been paused
        if (this.pause) {
            drawManager.drawPaused(this);
        }

        drawManager.completeDrawing(this);


    }

    /**
     * Cleans bullets that go off screen.
     */
    private void cleanBullets() {
        Set<Bullet> recyclable = new HashSet<Bullet>();
        for (Bullet bullet : this.bullets) {
            bullet.update();
            if (bullet.getPositionY() < separationLineHeight
                    || bullet.getPositionY() > this.height)
                recyclable.add(bullet);
        }

        this.bullets.removeAll(recyclable);
        BulletPool.recycle(recyclable);
    }

    private void cleanBullets1P() {
        Set<Bullet> recyclable = new HashSet<Bullet>();
        for (Bullet bullet : this.bullets1P) {
            bullet.update();
            if (bullet.getPositionY() < separationLineHeight
                    || bullet.getPositionY() > this.height)
                recyclable.add(bullet);
        }

        this.bullets1P.removeAll(recyclable);
        BulletPool.recycle(recyclable);
    }

    private void cleanBullets2P() {
        Set<Bullet> recyclable = new HashSet<Bullet>();
        for (Bullet bullet : this.bullets2P) {
            bullet.update();
            if (bullet.getPositionY() < separationLineHeight
                    || bullet.getPositionY() > this.height)
                recyclable.add(bullet);
        }

        this.bullets2P.removeAll(recyclable);
        BulletPool.recycle(recyclable);
    }

    private void cleanBulletsY() {
        Set<BulletY> recyclable = new HashSet<BulletY>();
        for (BulletY bulletY : this.bulletsY) {
            bulletY.update();
            if (bulletY.getPositionY() < separationLineHeight
                    || bulletY.getPositionY() > this.height)
                recyclable.add(bulletY);
        }
        this.bulletsY.removeAll(recyclable);
        BulletPool.recycleBulletY(recyclable);
    }

    private void cleanBulletsY1P() {
        Set<BulletY> recyclable = new HashSet<BulletY>();
        for (BulletY bulletY : this.bulletsY1P) {
            bulletY.update();
            if (bulletY.getPositionY() < separationLineHeight
                    || bulletY.getPositionY() > this.height)
                recyclable.add(bulletY);
        }
        this.bulletsY1P.removeAll(recyclable);
        BulletPool.recycleBulletY(recyclable);
    }

    private void cleanBulletsY2P() {
        Set<BulletY> recyclable = new HashSet<BulletY>();
        for (BulletY bulletY : this.bulletsY2P) {
            bulletY.update();
            if (bulletY.getPositionY() < separationLineHeight
                    || bulletY.getPositionY() > this.height)
                recyclable.add(bulletY);
        }
        this.bulletsY2P.removeAll(recyclable);
        BulletPool.recycleBulletY(recyclable);
    }

    /**
     * update and Cleans items that end the Living-Time
     */
    private void cleanItems() {
        Set<Item> recyclable = new HashSet<Item>();
        for (Item item : this.items) {
            item.update(this.getWidth(), this.getHeight(), separationLineHeight);
            if (item.isLivingTimeEnd()){
                recyclable.add(item);
            }
        }
        this.items.removeAll(recyclable);
        ItemPool.recycle(recyclable);
    }

    /**
     * Manages collisions between bullets and ships.
     */
    private void manageCollisions() {
        Set<Bullet> recyclableBullet = new HashSet<Bullet>();
        Set<Item> recyclableItem = new HashSet<Item>();
        for (Bullet bullet : this.bullets)
            if (bullet.getSpeed() > 0) {
                if (checkCollision(bullet, this.ship1P) && !this.levelFinished) {
                    recyclableBullet.add(bullet);
                    if (!this.ship1P.isDestroyed()) {
                        this.ship1P.destroy();
                        if (this.lives1p != 1) soundEffect.playShipCollisionSound();
                        this.lives1p--;
                        this.logger.info("Hit on player ship_1p, " + this.lives1p
                                + " lives remaining.");
                    }
                }
                else if (checkCollision(bullet, this.ship2P) && !this.levelFinished) {
                    recyclableBullet.add(bullet);
                    if (!this.ship2P.isDestroyed()) {
                        this.ship2P.destroy();
                        if (this.lives2p != 1) soundEffect.playShipCollisionSound();
                        this.lives2p--;
                        this.logger.info("Hit on player ship_2p, " + this.lives2p
                                + " lives remaining.");
                    }
                }
            }
        for(Bullet bullet1P : this.bullets1P)
            if (bullet1P.getSpeed() > 0) {
            } else {
                for (EnemyShip enemyShip : this.enemyShipFormation)
                    if (!enemyShip.isDestroyed()
                            && checkCollision(bullet1P, enemyShip)) {
                        enemyShip.reduceEnemyLife(this.attackDamage);
                        soundEffect.playEnemyDestructionSound();
                        if(enemyShip.getEnemyLife() < 1) {
                            this.score1P += enemyShip.getPointValue();
                            this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemyShip, this.items);
                            this.shipsDestroyed++;
                            this.shipsDestroyed += this.enemyShipFormation.getShipsDestroyed();
                        }
                        recyclableBullet.add(bullet1P);
                    }
                if (this.enemyShipSpecial != null
                        && !this.enemyShipSpecial.isDestroyed()
                        && checkCollision(bullet1P, this.enemyShipSpecial)) {
                    enemyShipSpecial.reduceEnemyLife(this.attackDamage);
                    if (enemyShipSpecial.getEnemyLife() < 1) {
                        this.score1P += this.enemyShipSpecial.getPointValue();
                        this.shipsDestroyed++;
                        this.enemyShipSpecial.destroy(this.items);
                        soundEffect.enemyShipSpecialDestructionSound();
                        bgm.enemyShipSpecialBGMstop();
                        if (this.lives1p < 2.9) this.lives1p = this.lives1p + 0.1;
                        if (this.lives2p < 2.9) this.lives2p = this.lives2p + 0.1;
                        this.enemyShipSpecialExplosionCooldown.reset();
                    }
                    recyclableBullet.add(bullet1P);
                }
            }

        for(Bullet bullet2P : this.bullets2P)
            if (bullet2P.getSpeed() > 0) {
            } else {
                for (EnemyShip enemyShip : this.enemyShipFormation)
                    if (!enemyShip.isDestroyed()
                            && checkCollision(bullet2P, enemyShip)) {
                        enemyShip.reduceEnemyLife(this.attackDamage);
                        soundEffect.playEnemyDestructionSound();
                        if(enemyShip.getEnemyLife() < 1) {
                            this.score2P += enemyShip.getPointValue();
                            this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemyShip, this.items);
                            this.shipsDestroyed++;
                            this.shipsDestroyed += this.enemyShipFormation.getShipsDestroyed();
                        }
                        recyclableBullet.add(bullet2P);
                    }
                if (this.enemyShipSpecial != null
                        && !this.enemyShipSpecial.isDestroyed()
                        && checkCollision(bullet2P, this.enemyShipSpecial)) {
                    enemyShipSpecial.reduceEnemyLife(this.attackDamage);
                    if (enemyShipSpecial.getEnemyLife() < 1) {
                        this.score2P += this.enemyShipSpecial.getPointValue();
                        this.shipsDestroyed++;
                        this.enemyShipSpecial.destroy(this.items);
                        soundEffect.enemyShipSpecialDestructionSound();
                        bgm.enemyShipSpecialBGMstop();
                        if (this.lives1p < 2.9) this.lives1p = this.lives1p + 0.1;
                        if (this.lives2p < 2.9) this.lives2p = this.lives2p + 0.1;
                        this.enemyShipSpecialExplosionCooldown.reset();
                    }
                    recyclableBullet.add(bullet2P);
                }
            }

        if (this.laser != null) {
            if (checkCollision(this.laser, this.ship1P) && !this.levelFinished) {
                if (!this.ship1P.isDestroyed()) {
                    this.ship1P.destroy();
                    if (this.lives1p != 1) soundEffect.playShipCollisionSound();
                    this.lives1p--;
                    if (gameSettings.getDifficulty() == 3) this.lives1p = 0;
                    this.logger.info("Hit on ship_1 " + this.lives1p
                            + " lives remaining.");
                }
            }
            if (checkCollision(this.laser, this.ship2P) && !this.levelFinished) {
                if (!this.ship2P.isDestroyed()) {
                    this.ship2P.destroy();
                    if (this.lives2p != 1) soundEffect.playShipCollisionSound();
                    this.lives2p--;
                    if (gameSettings.getDifficulty() == 3) this.lives2p = 0;
                    this.logger.info("Hit on ship_2 " + this.lives2p
                            + " lives remaining.");
                }
            }
        }
        for (Item item : this.items){
            if(checkCollision(item, this.ship1P) && !this.levelFinished && !item.isDestroyed()){
                recyclableItem.add(item);
                this.logger.info("Get Item Ship_1");

                //* settings of coins randomly got when killing monsters
                ArrayList<Integer> coinProbability = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 1, 1, 1, 2, 3, 4));
                Random random = new Random();
                int randomIndex = random.nextInt(coinProbability.size());

                if(item.getSpriteType() == DrawManager.SpriteType.Coin){
                    this.coin.addCoin(coinProbability.get(randomIndex));

                }
                if(item.getSpriteType() == DrawManager.SpriteType.BlueEnhanceStone){
                    this.enhanceManager.plusNumEnhanceStoneArea(1);
                }
                if(item.getSpriteType() == DrawManager.SpriteType.PerpleEnhanceStone){
                    this.enhanceManager.plusNumEnhanceStoneAttack(1);
                }
                this.ship1P.checkGetItem(item);
            }
            if(checkCollision(item, this.ship2P) && !this.levelFinished && !item.isDestroyed()){
                recyclableItem.add(item);
                this.logger.info("Get Item Ship_2");
                //* settings of coins randomly got when killing monsters
                ArrayList<Integer> coinProbability = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 1, 1, 1, 2, 3, 4));
                Random random = new Random();
                int randomIndex = random.nextInt(coinProbability.size());

                if(item.getSpriteType() == DrawManager.SpriteType.Coin){
                    this.coin.addCoin(coinProbability.get(randomIndex));

                }
                if(item.getSpriteType() == DrawManager.SpriteType.BlueEnhanceStone){
                    this.enhanceManager.plusNumEnhanceStoneArea(1);
                }
                if(item.getSpriteType() == DrawManager.SpriteType.PerpleEnhanceStone){
                    this.enhanceManager.plusNumEnhanceStoneAttack(1);
                }

                this.ship2P.checkGetItem(item);
            }
        }
        for (Bullet bullet : recyclableBullet) {
            if (bullet.getSpeed() < 0 && bullet.isEffectBullet() == 0) {
                bullet.splash(this.bullets);
            }
        }

        this.items.removeAll(recyclableItem);
        this.bullets.removeAll(recyclableBullet);
        this.bullets1P.removeAll(recyclableBullet);
        this.bullets2P.removeAll(recyclableBullet);
        ItemPool.recycle(recyclableItem);
        BulletPool.recycle(recyclableBullet);
    }

    /**
     * Manages collisions between bulletsY and ships.
     */
    private void manageCollisionsY() {
        Set<BulletY> recyclableBulletY = new HashSet<BulletY>();
        Set<Item> recyclableItem = new HashSet<Item>();
        for (BulletY bulletY : this.bulletsY)
            if (bulletY.getSpeed() > 0) {
                if (checkCollision(bulletY, this.ship1P) && !this.levelFinished) {
                    recyclableBulletY.add(bulletY);
                    if (!this.ship1P.isDestroyed()) {
                        this.ship1P.destroy();
                        if (this.lives1p != 1) soundEffect.playShipCollisionSound();
                        this.lives1p--;
                        this.logger.info("Hit on player ship, " + this.lives1p
                                + " lives remaining.");
                    }
                }
                else if (checkCollision(bulletY, this.ship2P) && !this.levelFinished) {
                    recyclableBulletY.add(bulletY);
                    if (!this.ship2P.isDestroyed()) {
                        this.ship2P.destroy();
                        if (this.lives2p != 1) soundEffect.playShipCollisionSound();
                        this.lives2p--;
                        this.logger.info("Hit on player ship, " + this.lives2p
                                + " lives remaining.");
                    }
                }
            }
        for (BulletY bulletY1P : this.bulletsY1P)
            if (bulletY1P.getSpeed() > 0) {
            } else {
                for (EnemyShip enemyShip : this.enemyShipFormation)
                    if (!enemyShip.isDestroyed()
                            && checkCollision(bulletY1P, enemyShip)) {
                        enemyShip.reduceEnemyLife(bulletY1P.getDamage());
                        soundEffect.playEnemyDestructionSound();
                        if(enemyShip.getEnemyLife() < 1) {
                            this.score1P += enemyShip.getPointValue();
                            this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemyShip, this.items);
                            this.shipsDestroyed++;
                            this.shipsDestroyed += this.enemyShipFormation.getShipsDestroyed();
                        }
                        recyclableBulletY.add(bulletY1P);
                    }
                if (this.enemyShipSpecial != null
                        && !this.enemyShipSpecial.isDestroyed()
                        && checkCollision(bulletY1P, this.enemyShipSpecial)) {
                    enemyShipSpecial.reduceEnemyLife(bulletY1P.getDamage());
                    if(enemyShipSpecial.getEnemyLife() < 1) {
                        this.score1P += this.enemyShipSpecial.getPointValue();
                        this.shipsDestroyed++;
                        this.enemyShipSpecial.destroy(this.items);
                        soundEffect.enemyShipSpecialDestructionSound();
                        bgm.enemyShipSpecialBGMstop();
                        if (this.lives1p < 2.9) this.lives1p = this.lives1p + 0.1;
                        if (this.lives2p < 2.9) this.lives2p = this.lives2p + 0.1;
                        this.enemyShipSpecialExplosionCooldown.reset();
                    }
                    recyclableBulletY.add(bulletY1P);
                }
            }
        for (BulletY bulletY2P : this.bulletsY2P)
            if (bulletY2P.getSpeed() > 0) {
            } else {
                for (EnemyShip enemyShip : this.enemyShipFormation)
                    if (!enemyShip.isDestroyed()
                            && checkCollision(bulletY2P, enemyShip)) {
                        enemyShip.reduceEnemyLife(bulletY2P.getDamage());
                        soundEffect.playEnemyDestructionSound();
                        if(enemyShip.getEnemyLife() < 1) {
                            this.score2P += enemyShip.getPointValue();
                            this.enemyShipFormation.destroy(enhanceManager.getlvEnhanceArea(), enemyShip, this.items);
                            this.shipsDestroyed++;
                            this.shipsDestroyed += this.enemyShipFormation.getShipsDestroyed();
                        }
                        recyclableBulletY.add(bulletY2P);
                    }
                if (this.enemyShipSpecial != null
                        && !this.enemyShipSpecial.isDestroyed()
                        && checkCollision(bulletY2P, this.enemyShipSpecial)) {
                    enemyShipSpecial.reduceEnemyLife(bulletY2P.getDamage());
                    if(enemyShipSpecial.getEnemyLife() < 1) {
                        this.score2P += this.enemyShipSpecial.getPointValue();
                        this.shipsDestroyed++;
                        this.enemyShipSpecial.destroy(this.items);
                        soundEffect.enemyShipSpecialDestructionSound();
                        bgm.enemyShipSpecialBGMstop();
                        if (this.lives1p < 2.9) this.lives1p = this.lives1p + 0.1;
                        if (this.lives2p < 2.9) this.lives2p = this.lives2p + 0.1;
                        this.enemyShipSpecialExplosionCooldown.reset();
                    }
                    recyclableBulletY.add(bulletY2P);
                }
            }
        this.items.removeAll(recyclableItem);
        this.bulletsY.removeAll(recyclableBulletY);
        this.bulletsY1P.removeAll(recyclableBulletY);
        this.bulletsY2P.removeAll(recyclableBulletY);
        ItemPool.recycle(recyclableItem);
        BulletPool.recycleBulletY(recyclableBulletY);
    }

    /**
     * Checks if two entities are colliding.
     *
     * @param a
     *            First entity, the bullet.
     * @param b
     *            Second entity, the ship.
     * @return Result of the collision test.
     */
    private boolean checkCollision(final Entity a, final Entity b) {
        // Calculate center point of the entities in both axis.
        int centerAX = a.getPositionX() + a.getWidth() / 2;
        int centerAY = a.getPositionY() + a.getHeight() / 2;
        int centerBX = b.getPositionX() + b.getWidth() / 2;
        int centerBY = b.getPositionY() + b.getHeight() / 2;
        // Calculate maximum distance without collision.
        int maxDistanceX = a.getWidth() / 2 + b.getWidth() / 2;
        int maxDistanceY = a.getHeight() / 2 + b.getHeight() / 2;
        // Calculates distance.
        int distanceX = Math.abs(centerAX - centerBX);
        int distanceY = Math.abs(centerAY - centerBY);

        return distanceX < maxDistanceX && distanceY < maxDistanceY;
    }

    /**
     * Returns a GameState object representing the status of the game.
     *
     * @return Current game state.
     */
    public final GameState_2P getGameState() {
        return new GameState_2P(this.level, this.score1P, this.score2P, this.coin, this.lives1p, this.lives2p,
                this.bulletsShot1P, this.bulletsShot2P, this.shipsDestroyed, this.hardCore,this.bulletsRemaining1p,this.bulletsRemaining2p);
    }
}