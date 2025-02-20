package entity;

import java.awt.Color;

import engine.DrawManager.SpriteType;

/**
 * Implements a laser that moves vertically up or down.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 *
 */
public class Laser extends Entity {

    /**
     * Speed of the Laser, positive or negative depending on direction -
     * positive is down.
     */
    private boolean activated;

    /**
     * Constructor, establishes the bullet's properties.
     *
     * @param positionX
     *            Initial position of the bullet in the X axis.
     * @param positionY
     *            Initial position of the bullet in the Y axis.
     * @param act
     *            Activated Lazer
     */
    public Laser(final int positionX, final int positionY, final boolean act) {
        super(positionX, positionY, 1*2, 448, Color.RED);
        this.activated = act;
        setSprite();
    }

    /**
     * Sets correct sprite for the bullet, based on speed.
     */
    public final void setSprite() {
        this.spriteType = SpriteType.Laser;
    }

    public final void launch() {
    }

    public final void activate(final boolean act) {this.activated = act;}
    public final boolean isActivated() {
        return this.activated;
    }

}
