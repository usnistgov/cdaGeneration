/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.CD;
import hl7OrgV3.CE;
import hl7OrgV3.II;
import hl7OrgV3.POCDMT000040Entry;
import hl7OrgV3.POCDMT000040Observation;
import hl7OrgV3.POCDMT000040Section;
import hl7OrgV3.ST;
import hl7OrgV3.StrucDocText;
import hl7OrgV3.XActMoodDocumentObservation;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mccaffrey
 */
public class RaceAndEthnicity {
    
    private String race = null;
    private String ethnicity = null;

    /**
     * @return the race
     */
    public String getRace() {
        return race;
    }

    /**
     * @param race the race to set
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * @return the ethnicity
     */
    public String getEthnicity() {
        return ethnicity;
    }

    /**
     * @param ethnicity the ethnicity to set
     */
    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }
    
    public static RaceAndEthnicity getRaceAndEthnicityById(String raceId, String ethnicityId) throws SQLException {
        RaceAndEthnicity rae = new RaceAndEthnicity();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sqlRace = new StringBuilder();
        sqlRace.append("SELECT * ");
        sqlRace.append("FROM " + DatabaseConnection.RACE_NAME + " ");
        sqlRace.append("WHERE " + DatabaseConnection.RACE_ID + " = '" + raceId + "';");

        ResultSet result = db.executeQuery(sqlRace.toString());

        if (result.next()) {            
            rae.setRace(result.getString(DatabaseConnection.RACE_RACE));            
        } else {
            return null;
        }
        
        StringBuilder sqlEthnicity = new StringBuilder();
        sqlEthnicity.append("SELECT * ");
        sqlEthnicity.append("FROM " + DatabaseConnection.ETHNICITY_NAME + " ");
        sqlEthnicity.append("WHERE " + DatabaseConnection.ETHNICITY_ETHNICITY + " = '" + ethnicityId + "';");
        
        ResultSet result2 = db.executeQuery(sqlEthnicity.toString());

        if (result2.next()) {            
            rae.setEthnicity(result2.getString(DatabaseConnection.ETHNICITY_ETHNICITY));            
        } else {
            return null;
        }
        
        return rae;
    }
    
    
    public static POCDMT000040Section populateRaceAndEthnicitySection(POCDMT000040Section section, RaceAndEthnicity rae) {
        
        II templateIdSection = section.addNewTemplateId();
        templateIdSection.setRoot("2.16.840.1.113883.10.20.26.1.2.2");
        templateIdSection.setExtension("2016-12-01");

        CE code = section.addNewCode();
        code.setCode("45970-1");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Demographic Section");

        ST title = section.addNewTitle();
        title.newCursor().setTextValue("Coded Race and Ethnicity Section");

        StrucDocText text = section.addNewText();
        text.newCursor().setTextValue("Race = " + rae.getRace() + "\n<br/>Ethnicity = " + rae.getEthnicity());
        
        POCDMT000040Entry raceEntry = section.addNewEntry();
        POCDMT000040Observation raceObservation = raceEntry.addNewObservation();
        
        raceObservation.setClassCode("OBS");
        raceObservation.setMoodCode(XActMoodDocumentObservation.EVN);
        
        II raceTemplateId = raceObservation.addNewTemplateId();
        raceTemplateId.setRoot("2.16.840.1.113883.10.20.26.1.3.22");
        raceTemplateId.setExtension("2016-12-01");
        
        CD raceCode = raceObservation.addNewCode();
        raceCode.setCode("PHC1430");
        raceCode.setCodeSystem("2.16.840.1.114222.4.5.274");
        raceCode.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        raceCode.setDisplayName("Race post edit");
  
        CD raceValue = CD.Factory.newInstance();
        raceValue.setCode(rae.getRace());
        raceObservation.addNewValue();
        raceObservation.setValueArray(0, raceValue);
        
        

        
        POCDMT000040Entry ethnicityEntry = section.addNewEntry();
        POCDMT000040Observation ethnicityObservation = ethnicityEntry.addNewObservation();
        
        raceObservation.setClassCode("OBS");
        raceObservation.setMoodCode(XActMoodDocumentObservation.EVN);
        
        II ethnicityTemplateId = ethnicityObservation.addNewTemplateId();
        ethnicityTemplateId.setRoot("2.16.840.1.113883.10.20.26.1.3.24");
        ethnicityTemplateId.setExtension("2016-12-01");
        
        CD ethnicityCode = ethnicityObservation.addNewCode();
        ethnicityCode.setCode("PHC1425");
        ethnicityCode.setCodeSystem("2.16.840.1.114222.4.5.274");
        ethnicityCode.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        ethnicityCode.setDisplayName("Ethnicity post edit");
  
        CD ethnicityValue = CD.Factory.newInstance();
        ethnicityValue.setCode(rae.getEthnicity());
        ethnicityObservation.addNewValue();
        ethnicityObservation.setValueArray(0, ethnicityValue);

        return section;

        
    }

    
    
    
}
