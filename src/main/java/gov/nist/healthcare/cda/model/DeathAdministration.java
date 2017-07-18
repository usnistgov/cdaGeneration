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
public class DeathAdministration {

    private String dateTimePronouncedDead = null;

    /**
     * @return the dateTimePronouncedDead
     */
    public String getDateTimePronouncedDead() {
        return dateTimePronouncedDead;
    }

    /**
     * @param dateTimePronouncedDead the dateTimePronouncedDead to set
     */
    public void setDateTimePronouncedDead(String dateTimePronouncedDead) {
        this.dateTimePronouncedDead = dateTimePronouncedDead;
    }

    public static DeathAdministration getDeathAdministrationById(String id) throws SQLException {
        DeathAdministration da = new DeathAdministration();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.DEATH_ADMINISTRATION_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.DEATH_ADMINISTRATION_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {
            da.setDateTimePronouncedDead(result.getString(DatabaseConnection.DEATH_ADMINISTRATION_DATE_TIME_PRONOUNCED_DEAD));
        } else {
            return null;
        }

        return da;
    }
}
