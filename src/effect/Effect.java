package effect;

import engine.Cooldown;
import engine.Core;
import engine.DrawManager.SpriteType;

public class Effect {

    /** Cooltime in tripleshot */
    protected final Cooldown tripleShotEffectCooldown;
    /** Cooltime in buffSplashEffect */
    protected final Cooldown buffSplashEffectCooldown;
    /** Cooltime in attakSpeedEffect*/
    protected final Cooldown attackSpeedEffectCooldown;
    /** Cooltime in debuffEffectCooldown */
    protected final Cooldown debuffEffectCooldown;
    /** Cooltime in debuffSturnEffect*/
    protected final Cooldown debuffSturnEffect;
    /** boolean in shieldState**/
    protected boolean shieldState = false;
    /** boolean in bomb
     * int로 바꾸셔서 개수 활용하셔도 됩니다.*/
    public boolean bomb;


    /**
     * Initialize effect cool time
     */
    public Effect() {
        tripleShotEffectCooldown = Core.getCooldown(5000);
        buffSplashEffectCooldown = Core.getCooldown(5000);
        attackSpeedEffectCooldown = Core.getCooldown(5000);
        debuffEffectCooldown = Core.getCooldown(5000);
        debuffSturnEffect = Core.getCooldown(2000);
        bomb = false;
    }
    /**
     * Initialize effect cool time according to item Sprite
     *
     * @param s
     *          When an item collision event occurs,
     *          a sprite type of the item is received.
     */
    public void cooldownReset(SpriteType s) {
        double prob = Math.random();
        switch (s) {
            case Buff_Item:
                if(prob < 0.2){
                    tripleShotEffectCooldown.reset();
                }else if(prob < 0.4){
                    attackSpeedEffectCooldown.reset();
                } else if (prob < 0.6){
                    buffSplashEffectCooldown.reset();
                } else if (prob < 0.85){
                    this.shieldState = true;
                }else {
                    bomb = true;
                }
                break;
            case Debuff_Item:
                if(prob < 0.5){
                    debuffEffectCooldown.reset();
                }else{
                    debuffSturnEffect.reset();
                }
                break;
            default:
                break;
        }
    }
    /** get state of shiled.*/
    public boolean getShieldState() { return this.shieldState; }
    /** set state of shiled.*/
    public void setShieldState(boolean state) { this.shieldState = state; }
}
