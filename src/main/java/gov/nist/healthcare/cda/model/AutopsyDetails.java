/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.BL;
import hl7OrgV3.CD;
import hl7OrgV3.CS;
import hl7OrgV3.II;
import hl7OrgV3.POCDMT000040EntryRelationship;
import hl7OrgV3.POCDMT000040Observation;
import hl7OrgV3.POCDMT000040Procedure;
import hl7OrgV3.XActMoodDocumentObservation;
import hl7OrgV3.XActRelationshipEntryRelationship;
import hl7OrgV3.XDocumentProcedureMood;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.xmlbeans.XmlOptions;

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

        if (result.next()) {
            resultsAvail = result.getBoolean(DatabaseConnection.AUTOPSY_DETAILS_RESULTS_AVAILABLE);
        } else {
            return null;
        }
        ad.setResultsAvailable(resultsAvail);

        return ad;
    }

    public static POCDMT000040Procedure populateProcedure(POCDMT000040Procedure procedure, AutopsyDetails au) {

        procedure.setClassCode("PROC");
        procedure.setMoodCode(XDocumentProcedureMood.EVN);
        if (au.getResultsAvailable()) {
            procedure.setNegationInd(false);
        } else {
            procedure.setNegationInd(true);
        }
        II templateId = procedure.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.2");

        CD procedureCode = procedure.addNewCode();
        procedureCode.setCode("29240004");
        procedureCode.setDisplayName("Autopsy Procedure");
        procedureCode.setCodeSystem("2.16.840.1.113883.6.96");
        procedureCode.setCodeSystemName("SNOMED CT");

        CS statusCode = procedure.addNewStatusCode();
        if (au.getResultsAvailable()) {
            statusCode.setCode("completed");
        }

        procedure.addNewEffectiveTime();

        POCDMT000040EntryRelationship availability = procedure.addNewEntryRelationship();
        availability.setTypeCode(XActRelationshipEntryRelationship.COMP);

        POCDMT000040Observation observation = availability.addNewObservation();
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId2 = observation.addNewTemplateId();
        templateId2.setRoot("2.16.840.1.113883.10.20.26.1.3");
        templateId2.setExtension("2016-12-01");

        CD availCode = observation.addNewCode();
        availCode.setCode("69436-4");
        availCode.setCodeSystem("2.16.840.1.113883.6.1");
        availCode.setCodeSystemName("LOINC");
        availCode.setDisplayName("Autopsy results available");

        BL value = BL.Factory.newInstance();
        value.setValue(true);
        observation.addNewValue();
        observation.setValueArray(0, value);

        return procedure;
    }

    public final static void main(String[] args) throws SQLException {

        AutopsyDetails ad = AutopsyDetails.getAutopsyDetailsById("AD1");
        POCDMT000040Procedure procedure = POCDMT000040Procedure.Factory.newInstance();
        AutopsyDetails.populateProcedure(procedure, ad);
        XmlOptions options = new XmlOptions();
        options.setCharacterEncoding("UTF-8");
        options.setSavePrettyPrint();

        System.out.println(procedure.xmlText(options));

    }

}
