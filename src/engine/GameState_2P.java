package engine;

import entity.Coin;

/**
 * Implements an object that stores the state of the game between levels.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class GameState_2P{

    /** Current game level. */
    private int level;
    /** Current score. */
    private int score_1P;
    private int score_2P;
    /** Current coin. */
    private Coin coin;
    /** Lives currently remaining. */
    private double livesRemaining_1P;
    private double livesRemaining_2P;
    /** Bullets shot until now. */
    private int bulletsShot_1P;
    private int bulletsShot_2P;
    /** Ships destroyed until now. */
    private int shipsDestroyed;
    /** HardCore(Only One life) */
    private boolean hardCore;

    private int bulletsRemaining_1p;
    private int bulletsRemaining_2p;




    /**
     * Constructor.
     *
     * @param level
     *            Current game level.
     * @param score
     *            Current score.
     * @param livesRemaining
     *            Lives currently remaining.
     * @param bulletsShot
     *            Bullets shot until now.
     * @param shipsDestroyed
     *            Ships destroyed until now.
     * @param hardCore
     *            Hardcore mode, Only one coin.
     */
    public GameState_2P(final int level, final int score_1P, final int score_2P, final Coin coin,
                     final double livesRemaining_1P, final double livesRemaining_2P, final int bulletsShot_1P, final int bulletShot_2P,
                     final int shipsDestroyed, final boolean hardCore, final int bulletsRemaining_1p,final int bulletsRemaining_2p) {
        this.level = level;
        this.score_1P = score_1P;
        this.score_2P = score_2P;
        this.coin = coin;
        this.livesRemaining_1P = livesRemaining_1P;
        this.livesRemaining_2P = livesRemaining_2P;
        this.bulletsShot_1P = bulletsShot_1P;
        this.bulletsShot_2P = bulletsShot_2P;
        this.shipsDestroyed = shipsDestroyed;
        this.hardCore = hardCore;
        this.bulletsRemaining_1p = bulletsRemaining_1p;
        this.bulletsRemaining_2p = bulletsRemaining_2p;

    }

    /**
     * @return the level
     */
    public final int getLevel() {
        return level;
    }

    /**
     * @return the score
     */
    public final int getScore_1P() {
        return score_1P;
    }
    public final int getScore_2P() {
        return score_2P;
    }

    /**
     * @return the score
     */
    public final Coin getCoin() {
        return coin;
    }

    /**
     * @return the score
     */
    public final Coin setCoin(final Coin coin) {
        return this.coin = coin;
    }

    /**
     * @return the livesRemaining
     */
    public final double getLivesRemaining_1P() {
        return livesRemaining_1P;
    }
    public final double getLivesRemaining_2P() {
        return livesRemaining_2P;
    }
    /**
     * @return the bulletsShot
     */
    public final int getBulletsShot_1P() {return bulletsShot_1P;
    }
    public final int getBulletsShot_2P() {
        return bulletsShot_2P;
    }

    public final int getBulletsRemaining_1p() { return bulletsRemaining_1p;}
    public final int getBulletsRemaining_2p() { return bulletsRemaining_2p;}

    /**
     * @return the shipsDestroyed
     */
    public final int getShipsDestroyed() {
        return shipsDestroyed;
    }

    /**
     * @return the hardCore
     */
    public final boolean getHardCore() {
        return this.hardCore;
    }


    /**
     * Set HardCore
     */
    public final void setHardCore() {
        this.livesRemaining_1P = 1;
        this.livesRemaining_2P = 1;
        this.hardCore = true;
    }

    /**
     * Set Level
     */
    public final void setLevel(int i) {
        this.level = i;
    }

    /**
     * Set LivesRecovery
     */
    public final void setLivesRecovery() {
        if(hardCore){
            this.score_1P = getScore_1P() + 100;
            this.score_2P = getScore_2P() + 100;
            this.level = getLevel() - 1;
            this.livesRemaining_1P = 1;
            this.livesRemaining_2P = 1;
        }
        else {
            this.score_1P = getScore_1P() + 100; // keeping score
            this.score_2P = getScore_2P() + 100;
            this.level = getLevel() - 1;
            this.livesRemaining_1P = 3;
            this.livesRemaining_2P = 3;
        }
    }
}
