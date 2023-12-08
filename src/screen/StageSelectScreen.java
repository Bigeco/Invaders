package screen;

import engine.Cooldown;
import engine.Core;
import engine.SoundEffect;

import java.awt.event.KeyEvent;

public class StageSelectScreen extends Screen {

    /** Milliseconds between changes in user selection. */
    private static final int selectionTime = 200;

    /** Time between changes in user selection. */
    private Cooldown selectionCooldown;

    /** stage Selected */
    private int stage;

    /** Total number of Stages. */
    private int totalStage;

    /** For selection moving sound */
    private SoundEffect soundEffect;
    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width
     *            Screen width.
     * @param height
     *            Screen height.
     * @param fps
     *            Frames per second, frame rate at which the game is run.
     */
    public StageSelectScreen(final int width, final int height, final int fps, final int totalstage, final int stage1){
        super(width, height, fps);

        // Defaults to stage 1 (index = 0).
        stage = stage1-1;
        totalStage = totalstage;
        this.selectionCooldown = Core.getCooldown(selectionTime);
        this.selectionCooldown.reset();

        soundEffect = new SoundEffect();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.stage+1; //return selected stage (index + 1)
    }


    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_UP)
                    || inputManager.isKeyDown(KeyEvent.VK_W)) {
                soundEffect.playButtonClickSound();
                upMenuItem(stage);
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_DOWN)
                    || inputManager.isKeyDown(KeyEvent.VK_S)) {
                soundEffect.playButtonClickSound();
                downMenuItem(stage);
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                    || inputManager.isKeyDown(KeyEvent.VK_D)) {
                soundEffect.playButtonClickSound();
                rightMenuItem(stage);
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
                    || inputManager.isKeyDown(KeyEvent.VK_A)) {
                soundEffect.playButtonClickSound();
                leftMenuItem(stage);
                this.selectionCooldown.reset();
            }
            if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                soundEffect.playSpaceButtonSound();
                this.isRunning = false;
            }
            if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                this.stage = -1;
                this.isRunning = false;
            }
        }
    }

    /**
     * Shifts the focus to the right, left, down, and up menu item. Each line has 5 items.
     */
    private void rightMenuItem(int i) {
        if (this.stage == totalStage-1)
            this.stage = 0;
        else
            this.stage = i+1;
    }
    private void leftMenuItem(int i) {
        if (this.stage == 0)
            this.stage = totalStage-1;
        else
            this.stage = i-1;
    }
    private void downMenuItem(int i) {
        this.stage = i + 5;
        if (this.stage >= totalStage)
            this.stage = this.stage % 5;
    }

    private void upMenuItem(int i) {
        this.stage = i - 5;
        if (this.stage < 0) {
            if (totalStage % 5 > i % 5)
                this.stage = 5 * (totalStage / 5) + i % 5;
            else
                this.stage = 5 * (totalStage / 5 - 1) + i % 5;
        }
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawStageSelect(this, this.stage, this.totalStage);

        drawManager.completeDrawing(this);
    }
}
