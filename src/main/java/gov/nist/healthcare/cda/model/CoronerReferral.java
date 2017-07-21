/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.CD;
import hl7OrgV3.ED;
import hl7OrgV3.II;
import hl7OrgV3.POCDMT000040Observation;
import hl7OrgV3.XActMoodDocumentObservation;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mccaffrey
 */
public class CoronerReferral {
    
    private String coronerReferralNote = null;

    /**
     * @return the coronerReferralNote
     */
    public String getCoronerReferralNote() {
        return coronerReferralNote;
    }

    /**
     * @param coronerReferralNote the coronerReferralNote to set
     */
    public void setCoronerReferralNote(String coronerReferralNote) {
        this.coronerReferralNote = coronerReferralNote;
    }
    
    public static CoronerReferral getCoronerReferralById(String id) throws SQLException {
        CoronerReferral cr = new CoronerReferral();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.CORONER_REFERRAL_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.CORONER_REFERRAL_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        
        if (result.next()) {
            cr.setCoronerReferralNote(result.getString(DatabaseConnection.CORONER_REFERRAL_CORONER_REFERRAL_NOTE));
        } else {
            return null;
        }        

        return cr;
    }
    
    public static POCDMT000040Observation populateCoronerReferralObservation(POCDMT000040Observation observation, CoronerReferral cr) {
        
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.5");      

        CD code = observation.addNewCode();
        code.setCode("69438-0");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Referral note forensic medicine");
        
        ED value = ED.Factory.newInstance();
        value.newCursor().setTextValue(cr.getCoronerReferralNote());       
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        return observation;
        
    }
    
}
