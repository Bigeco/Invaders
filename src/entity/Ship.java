package entity;

import java.awt.Color;
import java.util.Set;

import effect.ShipEffect;
import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

/**
 * Implements a ship, to be controlled by the player.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */
public class Ship extends Entity {

	/** Time between shots. */
	private static int shootingInterval = 750;
	/** Speed of the bullets shot by the ship. */
	public static final int bulletSpeed = -6;
	/** Speed of the bullets shot by the ship. */
	public static final int bulletYSpeed = -9;
	/** Movement of the ship for each unit of time. */
	private static final int speed = 2;

	/** Minimum time between shots. */
	public Cooldown shootingCooldown;
	/** Time spent inactive between hits. */
	public Cooldown destructionCooldown;
	/** EveryThing of item effect. */
	private ShipEffect shipEffect;

	/**
	 * Constructor, establishes the ship's properties.
	 *
	 * @param positionX
	 *            Initial position of the ship in the X axis.
	 * @param positionY
	 *            Initial position of the ship in the Y axis.
	 */
	public Ship(final int positionX, final int positionY, String type, Color color) {

		super(positionX, positionY, 13 * 2, 8 * 2, color);
		this.shipEffect = new ShipEffect(this);
		if(type.equals("a")) {
			this.spriteType = SpriteType.ShipA;
		}
		else if(type.equals("b")){
			this.spriteType = SpriteType.ShipB;
		}
		else if(type.equals("c")){
			this.spriteType = SpriteType.ShipC;
		}
		else if(type.equals("d")){
			this.spriteType = SpriteType.ShipD;
		}
		else if(type.equals("e")){
			this.spriteType = SpriteType.ShipE;
		}
		else if(type.equals("f")){
			this.spriteType = SpriteType.ShipF;
		}
		else if(type.equals("g")){
			this.spriteType = SpriteType.ShipG;
		}

		this.shootingCooldown = Core.getCooldown(shootingInterval);
		this.destructionCooldown = Core.getCooldown(1000);
	}

	/**
	 * Moves the ship speed uni ts right, or until the right screen border is
	 * reached.
	 *
	 * jtaejune : 스턴 아이템을 먹으면 속도가 0이 됨.
	 */
	public final void moveRight() {

		this.positionX += speed * this.shipEffect.moveEffect();
	}

	/**
	 * Moves the ship speed units left, or until the left screen border is
	 * reached.
	 *
	 * jtaejune : 스턴 아이템을 먹으면 속도가 0이 됨.
	 */
	public final void moveLeft() {
		this.positionX -= speed * this.shipEffect.moveEffect();
	}

	/**
	 * Shoots a bullet upwards.
	 * 
	 * @param bullets
	 *            List of bullets on screen, to add the new bullet.
	 * @param enhancedDamage
	 *            Enhanced Attack Damage. (on EnhanceScreen)
	 * @return Checks if the bullet was shot correctly.
	 */
	public final boolean shoot(final Set<Bullet> bullets, final int enhancedDamage) {
		this.shipEffect.attackSpeedUp();
		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();
			this.shipEffect.shoot(bullets, bulletSpeed, enhancedDamage);
			return true;
		}
		return false;
	}

	public final boolean shootBulletY(final Set<BulletY> bulletsY, final int enhancedDamage) {
		this.shipEffect.attackSpeedUp();
		if (this.shootingCooldown.checkFinished()) {
			this.shootingCooldown.reset();
			this.shipEffect.shootBulletY(bulletsY, bulletYSpeed, enhancedDamage);
			return true;
		}
		return false;
	}

	/**
	 * Updates status of the ship.
	 */
	public final void update() {
		if (this.spriteType == SpriteType.ShipA || 
			this.spriteType == SpriteType.ShipADestroyed || 
			this.spriteType == SpriteType.ShipAShileded) {
			if (this.shipEffect.getShieldState()){
				this.spriteType = SpriteType.ShipAShileded;
			} else {
				if (!this.destructionCooldown.checkFinished()) {
					this.spriteType = SpriteType.ShipADestroyed;
				} else {
					this.spriteType = SpriteType.ShipA;
				}
			}
		} else if (this.spriteType == SpriteType.ShipB || 
				this.spriteType == SpriteType.ShipBDestroyed || 
				this.spriteType == SpriteType.ShipBShileded) {
			if (this.shipEffect.getShieldState()){
				this.spriteType = SpriteType.ShipBShileded;
			} else {
				if (!this.destructionCooldown.checkFinished()) {
					this.spriteType = SpriteType.ShipBDestroyed;
				} else {
					this.spriteType = SpriteType.ShipB;
				}
			}
		} else if(this.spriteType == SpriteType.ShipC || 
				this.spriteType == SpriteType.ShipCDestroyed || 
				this.spriteType == SpriteType.ShipCShileded) {
			if (this.shipEffect.getShieldState()){
				this.spriteType = SpriteType.ShipCShileded;
			} else {
				if (!this.destructionCooldown.checkFinished()) {
					this.spriteType = SpriteType.ShipCDestroyed;
				} else {
					this.spriteType = SpriteType.ShipC;
				}
			}
		} else if(this.spriteType == SpriteType.ShipD || 
				this.spriteType == SpriteType.ShipDDestroyed) {
			if (!this.destructionCooldown.checkFinished()) {
				this.spriteType = SpriteType.ShipDDestroyed;
			} else {
				this.spriteType = SpriteType.ShipD;
			}
		} else if(this.spriteType == SpriteType.ShipE || 
				this.spriteType == SpriteType.ShipEDestroyed) {
			if (!this.destructionCooldown.checkFinished()) {
				this.spriteType = SpriteType.ShipEDestroyed;
			} else {
				this.spriteType = SpriteType.ShipE;
			}
		} else if(this.spriteType == SpriteType.ShipF || 
				this.spriteType == SpriteType.ShipFDestroyed) {
			if (!this.destructionCooldown.checkFinished()) {
				this.spriteType = SpriteType.ShipFDestroyed;
			} else {
				this.spriteType = SpriteType.ShipF;
			}
		} else if(this.spriteType == SpriteType.ShipG || 
				this.spriteType == SpriteType.ShipGDestroyed) {
			if (!this.destructionCooldown.checkFinished()) {
				this.spriteType = SpriteType.ShipGDestroyed;
			} else {
				this.spriteType = SpriteType.ShipG;
			}
		}
	}

	/**
	 * Switches the ship to its destroyed state.
	 */
	public final void destroy() {
		this.destructionCooldown.reset();
	}

	/**
	 * Checks if the ship is destroyed.
	 * 
	 * @return True if the ship is currently destroyed.
	 */
	public final boolean isDestroyed() {
		return !this.destructionCooldown.checkFinished();
	}

	/**
	 * Getter for the ship's speed.
	 * 
	 * @return Speed of the ship.
	 */
	public final int getSpeed() {
		return speed * this.shipEffect.moveEffect();
	}

	/**
	 * Reset cooldown when ship get an item
	 *
	 * @param item Items acquired by ship
	 */
	public final void checkGetItem(final Item item) {
		item.setDestroy(true);
		this.shipEffect.cooldownReset(item.getSpriteType());
	}



	/* -- Item 6. some helpful code */
	public final void endShipMotion(boolean levelFinished, double lives){
		if (levelFinished){
			if (lives == 0) {
				this.setColor(Color.gray);
				this.spriteType = SpriteType.gravestone;
			}
		}
	}

	/** Return the ship's attack speed
	 * @return shootingCooldown
	 */
	public Cooldown getShootingInterval(){ 
		return this.shootingCooldown; 
	}

	/** Set the ship's attack speed */
	public void setShootingInterval(Cooldown cool){ 
		this.shootingCooldown = cool; 
	}

	/** Return the ship's shiled state.
	 * @return ShiledState
	 */
	public boolean getShieldState() { 
		return this.shipEffect.getShieldState(); 
	}

	/** set the ship's shiled state. */
	public void setShieldState(boolean state) { 
		this.shipEffect.setShieldState(state); 
	}

	/** Return the ship has bomb?.
	 * @return bomb
	 */
	public boolean getBomb(){
		return shipEffect.bomb;
	}

	/** set the ship has bomb?. */
	public void setBomb(boolean t){
		this.shipEffect.bomb = t;
	}
}
