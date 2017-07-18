/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static DeathEvent getDeathEventById(String id) throws SQLException {
        DeathEvent de = new DeathEvent();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.DEATH_EVENT_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.DEATH_EVENT_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {
            de.setLocationOfDeath(result.getString(DatabaseConnection.DEATH_EVENT_LOCATION_OF_DEATH));
            de.setMannerOfDeath(result.getString(DatabaseConnection.DEATH_EVENT_MANNER_OF_DEATH));            
        } else {
            return null;
        }
        return de;
    }
}
