/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

/**
 *
 * @author mccaffrey
 */
public class DeathEvent {
    
    private String mannerOfDeath = null;
    private String locationOfDeath = null;

    /**
     * @return the mannerOfDeath
     */
    public String getMannerOfDeath() {
        return mannerOfDeath;
    }

    /**
     * @param mannerOfDeath the mannerOfDeath to set
     */
    public void setMannerOfDeath(String mannerOfDeath) {
        this.mannerOfDeath = mannerOfDeath;
    }

    /**
     * @return the locationOfDeath
     */
    public String getLocationOfDeath() {
        return locationOfDeath;
    }

    /**
     * @param locationOfDeath the locationOfDeath to set
     */
    public void setLocationOfDeath(String locationOfDeath) {
        this.locationOfDeath = locationOfDeath;
    }
    
}
