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
public class CauseOfDeath {

    private String causeOfDeath = null;
    private String diseaseOnsetToDeathInterval = null;
    private String tobaccoUse = null;
    private String injuryInvolvedInDeath = null;

    /**
     * @return the causeOfDeath
     */
    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    /**
     * @param causeOfDeath the causeOfDeath to set
     */
    public void setCauseOfDeath(String causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    /**
     * @return the diseaseOnsetToDeathInterval
     */
    public String getDiseaseOnsetToDeathInterval() {
        return diseaseOnsetToDeathInterval;
    }

    /**
     * @param diseaseOnsetToDeathInterval the diseaseOnsetToDeathInterval to set
     */
    public void setDiseaseOnsetToDeathInterval(String diseaseOnsetToDeathInterval) {
        this.diseaseOnsetToDeathInterval = diseaseOnsetToDeathInterval;
    }

    /**
     * @return the tobaccoUse
     */
    public String getTobaccoUse() {
        return tobaccoUse;
    }

    /**
     * @param tobaccoUse the tobaccoUse to set
     */
    public void setTobaccoUse(String tobaccoUse) {
        this.tobaccoUse = tobaccoUse;
    }

    /**
     * @return the injuryInvolvedInDeath
     */
    public String getInjuryInvolvedInDeath() {
        return injuryInvolvedInDeath;
    }

    /**
     * @param injuryInvolvedInDeath the injuryInvolvedInDeath to set
     */
    public void setInjuryInvolvedInDeath(String injuryInvolvedInDeath) {
        this.injuryInvolvedInDeath = injuryInvolvedInDeath;
    }

    public static CauseOfDeath getCauseOfDeathById(String id) throws SQLException {
        CauseOfDeath cod = new CauseOfDeath();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.CAUSE_OF_DEATH_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.CAUSE_OF_DEATH_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        
        if (result.next()) {
            cod.setCauseOfDeath(result.getString(DatabaseConnection.CAUSE_OF_DEATH_CAUSE_OF_DEATH));
            cod.setDiseaseOnsetToDeathInterval(result.getString(DatabaseConnection.CAUSE_OF_DEATH_DISEASE_ONSET_TO_DEATH_INTERVAL));
            cod.setInjuryInvolvedInDeath(result.getString(DatabaseConnection.CAUSE_OF_DEATH_INJURY_INVOLVED_IN_DEATH));
            cod.setTobaccoUse(result.getString(DatabaseConnection.CAUSE_OF_DEATH_TOBACCO_USE));
        } else {
            return null;
        }        

        return cod;
    }

}
