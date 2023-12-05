package entity;

import java.awt.Color;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

public class Item extends Entity {
    /**
     * Movement of the Item for each unit of time.
     */
    private final int speed = 2;
    /**
     * Living-Time of Item
     */
    private final Cooldown livingTime = Core.getCooldown(20000);
    /**
     * Movement direction X of item for each unit of time.
     */
    public int item_dx;
    /**
     * Movement direction Y of item for each unit of time.
     */
    public int item_dy;

    private boolean isDetroyed;

    /**
     * Constructor, establishes the Item's properties.
     * and Set sprite dot image which can find what Item it ts.
     *
     * @param positionX Initial position of the Item in the X axis.
     * @param positionY Initial position of the Item in the Y axis.
     */
    public Item(final int positionX, final int positionY) {
        super(positionX, positionY, 9 * 2, 9 * 2, Color.green);
        this.livingTime.reset();
        item_dx = Math.random() > 0.5 ? 1 : -1;
        item_dy = Math.random() > 0.5 ? 1 : -1;
        this.setSprite();
        isDetroyed = false;
    }

    /**
     * Set Sprite dot image.
     */
    public void setSprite() {
        double type = Math.random();
        if(type < 0.1){ //10%
            this.spriteType = SpriteType.Buff_Item;
            this.setColor(Color.GREEN);
        }else if(type < 0.3){ //20%
            this.spriteType = SpriteType.Debuff_Item;
            this.setColor(Color.GRAY);
        }else if(type < 0.4){ //10%
            this.spriteType = SpriteType.BlueEnhanceStone;
            this.setColor(Color.BLUE);
        }else if(type < 0.5){ //10%
            this.spriteType = SpriteType.PerpleEnhanceStone;
            this.setColor(Color.magenta);  
        }else if(type < 0.7){ //20%
            this.spriteType = SpriteType.LifeItem;
            this.setColor(Color.pink);  
        }
        else { 
            this.spriteType = SpriteType.Coin;
            this.setColor(Color.YELLOW);
        }
    }

    /**
     * update item in screen
     *
     * @param width         width of GameScreen
     * @param height        height of GameScreen
     * @param SEPERATE_LINE height of SEPERATE_LINE
     */
    public final void update(final int width, final int height, final int SEPERATE_LINE) {
        boolean isRightBorder = (this.getWidth() + this.getPositionX()) > width;
        boolean isLeftBorder = (this.getPositionX()) < 0;
        boolean isTopBorder = (this.getPositionY()) < SEPERATE_LINE;
        boolean isBottomBorder = (this.getHeight() + this.getPositionY()) > height;


        if (isRightBorder || isLeftBorder) {
            // 왼쪽 또는 오른쪽 경계에 부딪혔을 때는 x 방향 반대로 설정
            this.item_dx = -this.item_dx;
        }

        if (isTopBorder || isBottomBorder) {
            // 위쪽 또는 아래쪽 경계에 부딪혔을 때는 y 방향 반대로 설정
            this.item_dy = -this.item_dy;
        }
        positionX += this.speed * this.item_dx;
        positionY += this.speed * this.item_dy;
    }

    /**
     * check is item livingTime end.
     *
     * @return temp.checkFinished();
     */
    public final boolean isLivingTimeEnd() {
        return livingTime.checkFinished();
    }

    public final boolean isDestroyed() {
        return this.isDetroyed;
    }

    public final void setDestroy(boolean t) {
        this.isDetroyed = t;
    }


    /**
     * when reuse item, reset livingTime.
     */
    public final void coolReset() {
        this.livingTime.reset();
    }

    /**
     * get dropped item when stage is ended.
     */


    public final void resetItem(Ship ship) {
        this.coolReset();
        this.item_dx = ship.getPositionX() - this.positionX > 0 ? 1 : -1;
        this.item_dy = ship.getPositionY() - this.positionY > 0 ? 1 : -1;
        positionX += this.item_dx * ((int) Math.sqrt(Math.abs(ship.getPositionX() - this.positionX)) + 1);
        positionY += this.item_dy * ((int) Math.sqrt(Math.abs(ship.getPositionY() - this.positionY)) + 1);
    }
}