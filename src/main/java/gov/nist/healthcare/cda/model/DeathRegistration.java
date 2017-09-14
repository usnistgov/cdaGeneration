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
import hl7OrgV3.POCDMT000040Act;
import hl7OrgV3.POCDMT000040Entry;
import hl7OrgV3.POCDMT000040Observation;
import hl7OrgV3.POCDMT000040Section;
import hl7OrgV3.ST;
import hl7OrgV3.StrucDocText;
import hl7OrgV3.XActClassDocumentEntryAct;
import hl7OrgV3.XActMoodDocumentObservation;
import hl7OrgV3.XDocumentActMood;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mccaffrey
 */
public class DeathRegistration {

    private String timestamp = null;

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public static DeathRegistration getDeathRegistrationById(String id) throws SQLException {
        DeathRegistration dr = new DeathRegistration();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.DEATH_REGISTRATION_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.DEATH_REGISTRATION_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {

            dr.setTimestamp(result.getString(DatabaseConnection.DEATH_REGISTRATION_TIMESTAMP));
        } else {
            return null;
        }
        return dr;
    }

    public static POCDMT000040Section populateDeathRegistrationSection(POCDMT000040Section section, DeathRegistration dr) {

        II templateIdSection = section.addNewTemplateId();
        templateIdSection.setRoot("2.16.840.1.113883.10.20.26.1.2.3");
        templateIdSection.setExtension("2016-12-01");

        CE code = section.addNewCode();
        code.setCode("47519-4");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("History of procedures");

        ST title = section.addNewTitle();
        title.newCursor().setTextValue("Death Administration Section");

        StrucDocText text = section.addNewText();
        text.newCursor().setTextValue("He's dead, Jim.");

        POCDMT000040Entry deathRegistrationEntry = section.addNewEntry();
        POCDMT000040Act deathRegistrationAct = deathRegistrationEntry.addNewAct();
        DeathRegistration.populateDeathRegistrationAct(deathRegistrationAct, dr);

        return section;

    }

    public static POCDMT000040Act populateDeathRegistrationAct(POCDMT000040Act act, DeathRegistration dr) {

        act.setClassCode(XActClassDocumentEntryAct.ACT);
        act.setMoodCode(XDocumentActMood.EVN);

        II templateId = act.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.46");
        templateId.setExtension("2016-12-01");

        CD code = act.addNewCode();
        code.setCode(" 307928008");
        code.setCodeSystem("2.16.840.1.113883.6.96");
        code.setCodeSystemName("SNOMED-CT");
        code.setDisplayName("\"Death administrative procedure");

        act.addNewEffectiveTime().setValue(dr.getTimestamp());
        
        return act;

    }

}
