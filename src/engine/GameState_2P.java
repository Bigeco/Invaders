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
    private int score1P;
    private int score2P;
    /** Current coin. */
    private Coin coin;
    /** Lives currently remaining. */
    private double livesRemaining1P;
    private double livesRemaining2P;
    /** Bullets shot until now. */
    private int bulletsShot1P;
    private int bulletsShot2P;
    /** Ships destroyed until now. */
    private int shipsDestroyed;
    /** HardCore(Only One life) */
    private boolean hardCore;

    private int bulletsRemaining1p;
    private int bulletsRemaining2p;




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
    public GameState_2P(final int level, final int score1P, final int score2P, final Coin coin,
                     final double livesRemaining1P, final double livesRemaining2P, final int bulletsShot1P, final int bulletShot2P,
                     final int shipsDestroyed, final boolean hardCore, final int bulletsRemaining1p,final int bulletsRemaining2p) {
        this.level = level;
        this.score1P = score1P;
        this.score2P = score2P;
        this.coin = coin;
        this.livesRemaining1P = livesRemaining1P;
        this.livesRemaining2P = livesRemaining2P;
        this.bulletsShot1P = bulletsShot1P;
        this.bulletsShot2P = bulletsShot2P;
        this.shipsDestroyed = shipsDestroyed;
        this.hardCore = hardCore;
        this.bulletsRemaining1p = bulletsRemaining1p;
        this.bulletsRemaining2p = bulletsRemaining2p;

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
    public final int getScore1P() {
        return score1P;
    }
    public final int getScore2P() {
        return score2P;
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
    public final double getLivesRemaining1P() {
        return livesRemaining1P;
    }
    public final double getLivesRemaining2P() {
        return livesRemaining2P;
    }
    /**
     * @return the bulletsShot
     */
    public final int getBulletsShot1P() {return bulletsShot1P;
    }
    public final int getBulletsShot2P() {
        return bulletsShot2P;
    }

    public final int getBulletsRemaining1p() { return bulletsRemaining1p;}
    public final int getBulletsRemaining2p() { return bulletsRemaining2p;}

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
        this.livesRemaining1P = 1;
        this.livesRemaining2P = 1;
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
            this.score1P = getScore1P() + 100;
            this.score2P = getScore2P() + 100;
            this.level = getLevel() - 1;
            this.livesRemaining1P = 1;
            this.livesRemaining2P = 1;
        }
        else {
            this.score1P = getScore1P() + 100; // keeping score
            this.score2P = getScore2P() + 100;
            this.level = getLevel() - 1;
            this.livesRemaining1P = 3;
            this.livesRemaining2P = 3;
        }
    }
}
