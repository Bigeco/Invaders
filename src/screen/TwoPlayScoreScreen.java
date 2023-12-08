package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import engine.Cooldown;
import engine.Core;
import engine.GameState_2P;
import engine.Score;

import engine.SoundEffect;

/**
 * Implements the score screen.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class TwoPlayScoreScreen extends Screen {

    /**
     * Milliseconds between changes in user selection.
     */
    private static final int selectionTime = 200;
    /**
     * Maximum number of high scores.
     */
    private static final int maxHighScoreNum = 7;
    /**
     * Code of first mayus character.
     */
    private static final int firstChar = 65;
    /**
     * Code of last mayus character.
     */
    private static final int lastChar = 90;

    /**
     * Current score.
     */
    private int score;
    /**
     * Player lives left.
     */
    private double livesRemaining;
    /**
     * Total bullets shot by the player.
     */
    private int bulletsShot;
    /**
     * Total ships destroyed by the player.
     */
    private int shipsDestroyed;
    /**
     * List of past high scores.
     */
    private List<Score> highScores;
    /**
     * Checks if current score is a new high score.
     */
    private boolean isNewRecord;
    /**
     * Player name for record input.
     */
    private char[] name;
    /**
     * Character of players name selected for change.
     */
    private int nameCharSelected;
    /**
     * Time between changes in user selection.
     */
    private Cooldown selectionCooldown;
    /**
     * Game Difficulty.
     */
    private int difficulty;
    /** For selection moving sound */
    private SoundEffect soundEffect;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width     Screen width.
     * @param height    Screen height.
     * @param fps       Frames per second, frame rate at which the game is run.
     * @param gameState Current game state.
     */
    public TwoPlayScoreScreen(final int width, final int height, final int fps,
                       final GameState_2P gameState, final int difficulty) {
        super(width, height, fps);
        this.difficulty = difficulty;
        this.score = gameState.getScore1P() + gameState.getScore2P();
        this.livesRemaining = gameState.getLivesRemaining1P() + gameState.getLivesRemaining2P();
        this.bulletsShot = gameState.getBulletsShot1P() + gameState.getBulletsShot2P();
        this.shipsDestroyed = gameState.getShipsDestroyed();
        this.isNewRecord = false;
        this.name = "AAA".toCharArray();
        this.nameCharSelected = 0;
        this.selectionCooldown = Core.getCooldown(selectionTime);
        this.selectionCooldown.reset();

        soundEffect = new SoundEffect();

        try {
            this.highScores = Core.getFileManager().loadHighScores(this.difficulty);
            if (highScores.size() < maxHighScoreNum
                    || highScores.get(highScores.size() - 1).getScore()
                    < this.score)
                this.isNewRecord = true;

        } catch (IOException e) {
            logger.warning("Couldn't load high scores!");
        }
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();

        if (this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                soundEffect.playSpaceButtonSound();
                // Return to main menu.
                this.returnCode = 1;
                this.isRunning = false;
                if (this.isNewRecord)
                    saveScore();
            } else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                soundEffect.playSpaceButtonSound();
                // Play again.
                this.returnCode = 2;
                this.isRunning = false;
                if (this.isNewRecord)
                    saveScore();
            }

            if (this.isNewRecord && this.selectionCooldown.checkFinished()) {
                if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
                    soundEffect.playButtonClickSound();
                    this.nameCharSelected = this.nameCharSelected == 2 ? 0
                            : this.nameCharSelected + 1;
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
                    soundEffect.playButtonClickSound();
                    this.nameCharSelected = this.nameCharSelected == 0 ? 2
                            : this.nameCharSelected - 1;
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
                    soundEffect.playButtonClickSound();
                    this.name[this.nameCharSelected] =
                            (char) (this.name[this.nameCharSelected]
                                    == lastChar ? firstChar
                                    : this.name[this.nameCharSelected] + 1);
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
                    soundEffect.playButtonClickSound();
                    this.name[this.nameCharSelected] =
                            (char) (this.name[this.nameCharSelected]
                                    == firstChar ? lastChar
                                    : this.name[this.nameCharSelected] - 1);
                    this.selectionCooldown.reset();
                }
            }
        }

    }

    /**
     * Saves the score as a high score.
     */
    private void saveScore() {
        highScores.add(new Score(new String(this.name), score));
        Collections.sort(highScores);
        if (highScores.size() > maxHighScoreNum)
            highScores.remove(highScores.size() - 1);

        try {
            Core.getFileManager().save2PHighScores(highScores, difficulty);
        } catch (IOException e) {
            logger.warning("Couldn't load high scores!");
        }
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawGameOver(this, this.inputDelay.checkFinished(),
                this.isNewRecord);
        drawManager.drawResults(this, this.score, this.livesRemaining,
                this.shipsDestroyed, this.difficulty, (float) this.shipsDestroyed
                        / this.bulletsShot, this.isNewRecord);

        if (this.isNewRecord)
            drawManager.drawNameInput(this, this.name, this.nameCharSelected);

        drawManager.completeDrawing(this);
    }
}
