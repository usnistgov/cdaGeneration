/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.CD;
import hl7OrgV3.CE;
import hl7OrgV3.CS;
import hl7OrgV3.II;
import hl7OrgV3.IVLTS;
import hl7OrgV3.POCDMT000040Act;
import hl7OrgV3.POCDMT000040Entry;
import hl7OrgV3.POCDMT000040EntryRelationship;
import hl7OrgV3.POCDMT000040Observation;
import hl7OrgV3.POCDMT000040Procedure;
import hl7OrgV3.POCDMT000040Section;
import hl7OrgV3.ST;
import hl7OrgV3.StrucDocText;
import hl7OrgV3.XActClassDocumentEntryAct;
import hl7OrgV3.XActRelationshipEntryRelationship;
import hl7OrgV3.XDocumentActMood;
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
 
    public static POCDMT000040Section populateDeathAdministrationSection(POCDMT000040Section section, DeathAdministration da, AutopsyDetails ad, DeathCertification dc, CoronerReferral cr) {
        
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
        
        POCDMT000040Entry deathPronouncementEntry = section.addNewEntry();
        POCDMT000040Act deathPronouncementAct = deathPronouncementEntry.addNewAct();
        DeathAdministration.populateDeathPronouncementAct(deathPronouncementAct, da);

        POCDMT000040Entry autopsyDetailsEntry = section.addNewEntry();
        POCDMT000040Procedure autopsyDetailsProcedure = autopsyDetailsEntry.addNewProcedure();
        AutopsyDetails.populateProcedure(autopsyDetailsProcedure, ad);
        
        POCDMT000040Entry deathCertificationEntry = section.addNewEntry();
        POCDMT000040Act deathCertificationAct = deathCertificationEntry.addNewAct();
        DeathCertification.populateDeathCertificationAct(deathCertificationAct, dc);
        
        POCDMT000040Entry coronerCaseTransferEntry = section.addNewEntry();
        POCDMT000040Act coronerCaseTransferAct = coronerCaseTransferEntry.addNewAct();
        DeathAdministration.populatecoronerCaseTransferAct(coronerCaseTransferAct, da, cr);
        
        
        return section;
        
    }

    private static POCDMT000040Act populateDeathPronouncementAct(POCDMT000040Act act, DeathAdministration da) {
        
        act.setClassCode(XActClassDocumentEntryAct.ACT);
        act.setMoodCode(XDocumentActMood.EVN);
        act.addNewId();
        II templateId1 = act.addNewTemplateId();
        templateId1.setRoot("2.16.840.1.113883.10.20.26.1.15");
        templateId1.setExtension("2016-12-01");

        II templateId2 = act.addNewTemplateId();
        templateId2.setRoot("2.16.840.1.113883.10.20.22.4.12");
        templateId2.setExtension("2014-06-09");
             
        CD code = act.addNewCode();
        //TODO This looks like a mistake. This is a SNOMED CT code
        code.setCode("446661000124101");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Death pronouncement");
        
        CS statusCode = act.addNewStatusCode();
        statusCode.setCode("completed");
        
        IVLTS effectiveTime = act.addNewEffectiveTime();
        effectiveTime.setValue(da.getDateTimePronouncedDead());
        
        return act;
                
    }

    private static POCDMT000040Act populatecoronerCaseTransferAct(POCDMT000040Act act, DeathAdministration da, CoronerReferral cr) {

        act.setClassCode(XActClassDocumentEntryAct.ACT);
        act.setMoodCode(XDocumentActMood.EVN);
        
        II templateId = act.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.4");
        templateId.setExtension("2016-12-01");

        CD code = act.addNewCode();
        code.setCode("74497-9");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Medical examiner or coroner was contacted");

        if(cr != null) {            
            POCDMT000040EntryRelationship entryRelationship = act.addNewEntryRelationship();
            entryRelationship.setTypeCode(XActRelationshipEntryRelationship.COMP);
            POCDMT000040Observation observation = entryRelationship.addNewObservation();
            CoronerReferral.populateCoronerReferralObservation(observation, cr);                       
        }
        
        return act;
        
    }

    
    
}
