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
public class AutopsyDetails {
    
    //private String autopsyDetailsID = null;
    private Boolean resultsAvailable = null;

    /**
     * @return the resultsAvailable
     */
    public Boolean getResultsAvailable() {
        return resultsAvailable;
    }

    /**
     * @param resultsAvailable the resultsAvailable to set
     */
    public void setResultsAvailable(Boolean resultsAvailable) {
        this.resultsAvailable = resultsAvailable;
    }
    
        
    public static AutopsyDetails getAutopsyDetailsById(String id) throws SQLException {
        AutopsyDetails ad = new AutopsyDetails();
        
        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.AUTOPSY_DETAILS_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.AUTOPSY_DETAILS_ID + " = '" + id + "';");
        
        ResultSet result = db.executeQuery(sql.toString());
        
        Boolean resultsAvail = null;
        
        if(result.next()) {
            resultsAvail = result.getBoolean(DatabaseConnection.AUTOPSY_DETAILS_RESULTS_AVAILABLE);            
        } else {
            return null;
        }
        ad.setResultsAvailable(resultsAvail);
        
        return ad;
    }

    
    
    
}
