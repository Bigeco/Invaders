package screen;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import engine.Cooldown;
import engine.Core;
import engine.Score;

/**
 * Implements the high scores screen, it shows player records.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class HighScoreScreen extends Screen {

	/** List of past high scores. */
	private List<Score> highScoresEASY;
	private List<Score> highScoresNORMAL;
	private List<Score> highScoresHARD;
	private List<Score> highScoresHARDCORE;
	private int difficulty;
	private Cooldown selectCooldown;
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
	public HighScoreScreen(final int width, final int height, final int fps) {
		super(width, height, 60);
		this.selectCooldown = Core.getCooldown(200);
		this.selectCooldown.reset();
		this.returnCode = 1;
		this.difficulty = 0;
		try {
			this.highScoresEASY = Core.getFileManager().loadHighScores(0);
			this.highScoresNORMAL = Core.getFileManager().loadHighScores(1);
			this.highScoresHARD = Core.getFileManager().loadHighScores(2);
			this.highScoresHARDCORE = Core.getFileManager().loadHighScores(3);
		} catch (NumberFormatException | IOException e) {
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
		if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
				&& this.selectCooldown.checkFinished()) {
			this.difficulty += 1;
			if (this.difficulty > 3) {
				this.difficulty = 0;
			}
			this.selectCooldown.reset();
			this.selectCooldown.reset();
		}
		else if (inputManager.isKeyDown(KeyEvent.VK_LEFT)
				&& this.selectCooldown.checkFinished()) {
			this.difficulty -= 1;
			if (this.difficulty < 0) {
				this.difficulty = 3;
			}
			this.selectCooldown.reset();
			this.selectCooldown.reset();
		}
		else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)
				&& this.inputDelay.checkFinished()) {
			this.isRunning = false;
		}
	}

	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);
		drawManager.drawHighScoreMenu(this);
		drawManager.drawDiffScore(this, this.difficulty);
		if (this.difficulty == 0) {
			drawManager.drawHighScores(this, this.highScoresEASY);
		} else if (this.difficulty == 1) {
			drawManager.drawHighScores(this, this.highScoresNORMAL);
		} else if (this.difficulty == 2) {
			drawManager.drawHighScores(this, this.highScoresHARD);
		} else {
			drawManager.drawHighScores(this, this.highScoresHARDCORE);
		}

		drawManager.completeDrawing(this);
	}
}
