package effect;

import engine.Core;
import entity.Bullet;
import entity.BulletY;
import entity.BulletPool;
import entity.Ship;

import java.util.Set;

public class ShipEffect extends Effect {
    /**
     * About the ships to run the effects
     */
    private Ship ship;

    /**
     * @param ship a ship object
     */
    public ShipEffect(Ship ship) {
        super();
        this.ship = ship;
    }

    /**
     * Item Effect: Effect on reversal.
     */
    public int moveEffect() {
        if (this.debuffEffectCooldown.checkFinished()) {
            return (shipSturnEffect());
        }
        return (-1 * shipSturnEffect());
    }

    /**
     * Bullet shooting effect
     *
     * @param bullets         The factor of the ship's shoot method.
     * @param bulletSpeed    the speed of a ship's bullet
     * @param enhancedDamage the enhanced damage of a ship's bullet
     */
    public void shoot(final Set<Bullet> bullets, final int bulletSpeed, final int enhancedDamage) {
        if (this.tripleShotEffectCooldown.checkFinished()) {
            bullets.add(BulletPool.getBullet(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), bulletSpeed, enhancedDamage));
        } else {
            bullets.add(BulletPool.getBullet(ship.getPositionX() + ship.getWidth() / 2 + 10,
                    ship.getPositionY(), bulletSpeed, enhancedDamage));
            bullets.add(BulletPool.getBullet(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), bulletSpeed, enhancedDamage));
            bullets.add(BulletPool.getBullet(ship.getPositionX() + ship.getWidth() / 2 - 10,
                    ship.getPositionY(), bulletSpeed, enhancedDamage));
        }
    }

    /**
     * Bullet shooting effect
     *
     * @param bulletsY        The factor of the ship's shoot method.
     * @param bulletYSpeed   the speed of a ship's bulletY
     * @param enhancedDamage the enhanced damage of a ship's bullet
     */
    public void shootBulletY(final Set<BulletY> bulletsY, final int bulletYSpeed, final int enhancedDamage) {
        if (this.tripleShotEffectCooldown.checkFinished()) {
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), bulletYSpeed, enhancedDamage));
        } else {
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2 + 10,
                    ship.getPositionY(), bulletYSpeed, enhancedDamage));
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2,
                    ship.getPositionY(), bulletYSpeed, enhancedDamage));
            bulletsY.add(BulletPool.getBulletY(ship.getPositionX() + ship.getWidth() / 2 - 10,
                    ship.getPositionY(), bulletYSpeed, enhancedDamage));
        }
    }

    /**
     * attack speed variety effect
     */
    public void attackSpeedUp() {
        if (this.attackSpeedEffectCooldown.checkFinished()) {
            if (ship.getShootingInterval().getMilliseconds() == 100)
                ship.setShootingInterval(Core.getCooldown(750));
        } else {
            if (ship.getShootingInterval().getMilliseconds() == 750)
                ship.setShootingInterval(Core.getCooldown(100));
        }
    }

    /**
     * 스턴 디버프에 걸리면 0반환
     * 평소에는 1을 반환
     * <p>
     * 사용처 : Ship 클래스
     */
    public int shipSturnEffect() {
        if (this.debuffSturnEffect.checkFinished()) {
            return (1);
        } else {
            return (0);
        }
    }
}