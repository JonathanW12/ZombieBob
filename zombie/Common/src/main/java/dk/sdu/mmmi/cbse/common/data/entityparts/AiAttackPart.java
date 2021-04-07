/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 *AiAttackPart describes the AI level of an entity's attack.
 */
public class AiAttackPart implements EntityPart{
    private int level;
    
    public AiAttackPart(int level){
        this.level = level;
    }
    
    public int getLevel(){
        return level;
    }
}
