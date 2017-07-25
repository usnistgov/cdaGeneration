/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.AD;
import hl7OrgV3.CD;
import hl7OrgV3.CE;
import hl7OrgV3.II;
import hl7OrgV3.POCDMT000040Act;
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
public class DeathEvent {

    private String mannerOfDeath = null;
    private Address locationOfDeath = null;

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
    public Address getLocationOfDeath() {
        return locationOfDeath;
    }

    /**
     * @param locationOfDeath the locationOfDeath to set
     */
    public void setLocationOfDeath(Address locationOfDeath) {
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
            
            String addressID = result.getString(DatabaseConnection.DEATH_EVENT_LOCATION_OF_DEATH);            
            Address add = Address.getAddressById(addressID);            
            de.setLocationOfDeath(add);
            de.setMannerOfDeath(result.getString(DatabaseConnection.DEATH_EVENT_MANNER_OF_DEATH));            
        } else {
            return null;
        }
        return de;
    }
    
    
    public static POCDMT000040Section populateDeathEventSection(POCDMT000040Section section, DeathEvent de) {
        
        II templateIdSection = section.addNewTemplateId();
        templateIdSection.setRoot("2.16.840.1.113883.10.20.26.1.2.6");
        templateIdSection.setExtension("2016-12-01");

        CE code = section.addNewCode();
        code.setCode("TEMP_LOINC_DEATH_EVENT_SECTION");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Death Event Section");

        ST title = section.addNewTitle();
        title.newCursor().setTextValue("Death Event Section");

        StrucDocText text = section.addNewText();
        text.newCursor().setTextValue("He's dead, Jim.");
        
        POCDMT000040Entry deathLocationTypeEntry = section.addNewEntry();
        POCDMT000040Observation deathLocationTypeObservation = deathLocationTypeEntry.addNewObservation();
        DeathEvent.populateDeathLocationTypeObservation(deathLocationTypeObservation, de);

        POCDMT000040Entry locationOfDeathEntry = section.addNewEntry();
        POCDMT000040Observation locationOfDeathObservation = locationOfDeathEntry.addNewObservation();
        DeathEvent.populateLocationOfDeathObservation(locationOfDeathObservation, de);
        
        POCDMT000040Entry mannerOfDeathEntry = section.addNewEntry();
        POCDMT000040Observation mannerOfDeathObservation = mannerOfDeathEntry.addNewObservation();
        DeathEvent.populateMannerOfDeathObservation(mannerOfDeathObservation, de);
        
        return section;

        
    }

    private static POCDMT000040Observation populateDeathLocationTypeObservation(POCDMT000040Observation observation, DeathEvent de) {

        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.8");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("58332-8");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Location of Death");

        // TODO hardcoded for now
        CD value = CD.Factory.newInstance();
        value.setCode("63238001");
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        return observation;
    }
    private static POCDMT000040Observation populateLocationOfDeathObservation(POCDMT000040Observation observation, DeathEvent de) {
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.10");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("58332-8");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Location of Death");

        
        AD value = de.getLocationOfDeath().toAD();
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        return observation;
                
    }

    private static POCDMT000040Observation populateMannerOfDeathObservation(POCDMT000040Observation observation, DeathEvent de) {
    
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.11");        

        CD code = observation.addNewCode();
        code.setCode("69449-7");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Manner of Death");

        
        CD value = CD.Factory.newInstance();
        value.setCode(de.getMannerOfDeath());
        value.setCodeSystem("2.16.840.1.113883.6.96");
        value.setCodeSystemName("SNOMED CT");
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        return observation;
    }

    
    
}
