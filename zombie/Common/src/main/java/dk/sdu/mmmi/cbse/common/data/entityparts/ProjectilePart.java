/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 *ProjectilePart describes the type of an entity.
 */
public class ProjectilePart implements EntityPart{
    private float currentTravelDistance;
    private float maxTravelDistance;

    public ProjectilePart(float maxTravelDistance) {
        this.maxTravelDistance = maxTravelDistance * 60;
    }

    public float getCurrentTravelDistance() {
        return currentTravelDistance;
    }

    public void setCurrentTravelDistance(float currentTravelDistance) {
        this.currentTravelDistance = currentTravelDistance;
    }

    public float getMaxTravelDistance() {
        return maxTravelDistance;
    }

    public void setMaxTravelDistance(float maxTravelDistance) {
        this.maxTravelDistance = maxTravelDistance;
    }
}
