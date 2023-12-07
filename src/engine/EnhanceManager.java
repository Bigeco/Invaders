package engine;

import java.util.ArrayList;
import java.util.Arrays;
import entity.EnhanceStone;
/**
 * Manages Values Related to Enhance.
 */
public class EnhanceManager {
    /** Current Number of Enhancement Area Stone. */
    private int numEnhanceStoneArea;
    /** Current Number of Enhancement Attack Stone. */
    private int numEnhanceStoneAttack;
    /** Current Level of Enhanced Attack. */
    private int lvEnhanceAttack;
    /** Current Level of Enhanced Area. */
    private int lvEnhanceArea;
    /** Current Damage of Attack */
    private int attackDamage;
    private boolean isEnhanced = false;

    /**
	 * Constructor.
	 */
    public EnhanceManager(final int numEnhanceStoneArea, final int numEnhanceStoneAttack, 
                          final int lvEnhanceArea, final int lvEnhanceAttack,
                          final int attackDamage) {    
        EnhanceStone enhanceStone = new EnhanceStone(0, 0, lvEnhanceArea, lvEnhanceAttack, attackDamage);

        this.numEnhanceStoneArea = numEnhanceStoneArea;
        this.numEnhanceStoneAttack = numEnhanceStoneAttack;
        this.lvEnhanceAttack = enhanceStone.getlvEnhanceAttack();
        this.lvEnhanceArea = enhanceStone.getlvEnhanceArea();
        this.attackDamage = enhanceStone.getAttackDamage();
    }    

    /**
     * Enhance attack damage using Enhance stone.
	 */
    public void enhanceAttackDamage() {
        int numRequiredEnhanceStone = this.getRequiredNumEnhanceStoneAttack();
        if (numEnhanceStoneAttack >= numRequiredEnhanceStone) {
            this.attackDamage += this.getValEnhanceAttack();
            this.numEnhanceStoneAttack -= numRequiredEnhanceStone;
            this.lvEnhanceAttack += 1;
            isEnhanced = true;
        }
    }
    
    /**
     * Enhance area damage using Enhance stone.
     */
    public void enhanceAreaDamage() { 
        int numRequiredEnhanceStone = this.getRequiredNumEnhanceStoneArea();
        if (numEnhanceStoneArea >= numRequiredEnhanceStone) {
            this.numEnhanceStoneArea -= numRequiredEnhanceStone;
            this.lvEnhanceArea += 1;
            isEnhanced = true;
        }
    }
    
    /**
     * Return damage of attack.
     * 
     * @return attackDamage
     */
    public int getAttackDamage() {
        return this.attackDamage;
    }
    
    /**
     * Return level of enhancement of attack.
     * 
     * @return lvEnhanceAttack
     */
    public int getlvEnhanceAttack() {
        return this.lvEnhanceAttack;
    }
    
    /**
     * Return level of enhancement of area.
     * 
     * @return lvEnhanceArea
     */
    public int getlvEnhanceArea() {
        return this.lvEnhanceArea;
    }
    
    
    /**
     * Return number of enhanced stone (attack).
     * 
     * @return numEnhanceStoneAttack
     */
    public int getNumEnhanceStoneAttack() {
        return this.numEnhanceStoneAttack;
    }
    
    /**
     * Return number of enhanced stone (area).
     * 
     * @return numEnhanceStoneArea
     */
    public int getNumEnhanceStoneArea() {
        return this.numEnhanceStoneArea;
    }
    
    /**
     * Return Value of enhanced damage according to level
     * 
     * @return valEnhanceAttack
     */
    public int getValEnhanceAttack() {
        ArrayList<Integer> valEnhanceAttackList = new ArrayList<>(Arrays.asList(1, 1, 3, 5, 8, 12, 0));
        return valEnhanceAttackList.get(this.lvEnhanceAttack);
    }    
    
    /**
     * Return number of required Enhance-Stone-Attack
     * 
     * @return numRequiredEnhanceAttackStone
     */
    public int getRequiredNumEnhanceStoneAttack() {
        ArrayList<Integer> numRequiredEnhanceAttackStoneList = new ArrayList<>(Arrays.asList(1, 2, 4, 7, 10, 15, 0));
        return numRequiredEnhanceAttackStoneList.get(this.lvEnhanceAttack);
    }  
    
    /**
     * Return number of required Enhance-Stone-Attack
     * 
     * @return numRequiredEnhanceAreaStone
     */
    public int getRequiredNumEnhanceStoneArea() {
        ArrayList<Integer> numRequiredEnhanceAreaStoneList = new ArrayList<>(Arrays.asList(2, 5, 8, 0));
        return numRequiredEnhanceAreaStoneList.get(this.lvEnhanceArea);
    }    
    
    /**
     * Set number of Purple-Enhance-Attack-Stone
     */    
    public void plusNumEnhanceStoneAttack(final int num) {
        this.numEnhanceStoneAttack += num;
    }
    
    /**
     * Set number of Blue-Enhance-Area-Stone
     */
    public void plusNumEnhanceStoneArea(final int num) {
        this.numEnhanceStoneArea += num;
    }

    public boolean getIsEnhanced() {
        return isEnhanced;
    }
}
