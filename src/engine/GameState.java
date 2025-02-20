package engine;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import entity.Coin;

/**
 * Implements an object that stores the state of the game between levels.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class GameState {

	/**
	 * Current game level.
	 */
	private int level;
	/**
	 * Current score.
	 */
	private int score;
	/**
	 * Current coin.
	 */
	private Coin coin;
	/**
	 * Lives currently remaining.
	 */
	private double livesRemaining;
	private double livesRemaining2p;
	/**
	 * Bullets shot until now.
	 */
	private int bulletsShot;

	private int bulletsShot2p;
	/**
	 * Ships destroyed until now.
	 */
	private int shipsDestroyed;
	/**
	 * HardCore(Only One life)
	 */
	private boolean hardCore;

	private Color shipColor;

	private String nowSkinString;

	private Map<Color, Boolean> ownedSkins;
	private Map<Color, Boolean> equippedSkins;

	private int bulletsRemaining;

	private String coinMsg = "";

	/**
	 * Constructor.
	 *
	 * @param level          Current game level.
	 * @param score          Current score.
	 * @param coin           Current coin.
	 * @param livesRemaining Lives currently remaining.
	 * @param bulletsShot    Bullets shot until now.
	 * @param shipsDestroyed Ships destroyed until now.
	 * @param hardCore       Hardcore mode, Only one coin.
	 */
	public GameState(final int level, final int score, final Coin coin,
					final double livesRemaining, final int bulletsShot,
					final int shipsDestroyed, final boolean hardCore, 
					final Color shipColor,final String nowSkinString, 
					final Map<Color, Boolean> ownedSkins, final Map<Color, Boolean> equippedSkins, 
					final int bulletsRemaining) {
		this.level = level;
		this.score = score;
		this.coin = coin;
		this.livesRemaining = livesRemaining;
		this.bulletsShot = bulletsShot;
		this.shipsDestroyed = shipsDestroyed;
		this.hardCore = hardCore;
		this.bulletsRemaining = bulletsRemaining;
		this.shipColor = shipColor;
		this.nowSkinString = nowSkinString;
		if (ownedSkins == null) {
			this.ownedSkins = new HashMap<>();
		} else {
			this.ownedSkins = new HashMap<>(ownedSkins);
		}
		if (ownedSkins == null) {
			this.equippedSkins = new HashMap<>();
		} else {
			this.equippedSkins = new HashMap<>(equippedSkins);
		}
		
		this.coinMsg = coinMsg;

	}
	public final String getNowSkinString(){
		return nowSkinString;
	}
	public final void setNowSkinString(String nowString){
		this.nowSkinString = nowString;
	}

	public final Color getShipColor() {
		return shipColor;
	}

	public final void setShipColor(Color color) {
		this.shipColor = color;
	}
	public final Map<Color, Boolean> getOwnedSkins(){
		return ownedSkins;
	}
	public final Map<Color, Boolean> getEquippedSkins(){
		return equippedSkins;
	}
	public final void setOwnedSkins(Color color, boolean bool){
		this.ownedSkins.put(color, bool);
	}
	public final void setEquipped(Color color, boolean bool){
		this.equippedSkins.put(color, bool);
	}

	public final String getCoinMsg(){
		return this.coinMsg;
	}

	public void setCoinMsg(String newCoinMsg){
		this.coinMsg=newCoinMsg;
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
	public final int getScore() {
		return score;
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
	public final double getLivesRemaining() {
		return livesRemaining;
	}
	
	public final void plusLives() {
		this.livesRemaining += 1;
	}

	/**
	 * @return the bulletsShot
	 */
	public final int getBulletsShot() {
		return bulletsShot;
	}

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
		this.livesRemaining = 1;
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
		this.score = getScore() + 100; // keeping score 
		this.level = getLevel() - 1;
		this.livesRemaining = 3;
	}

	public final int getBulletsRemaining() {
		return bulletsRemaining;
	}
}
