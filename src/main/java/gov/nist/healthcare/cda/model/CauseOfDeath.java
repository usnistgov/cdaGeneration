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
import hl7OrgV3.ED;
import hl7OrgV3.II;
import hl7OrgV3.INT;
import hl7OrgV3.POCDMT000040Component4;
import hl7OrgV3.POCDMT000040Entry;
import hl7OrgV3.POCDMT000040EntryRelationship;
import hl7OrgV3.POCDMT000040Observation;
import hl7OrgV3.POCDMT000040Organizer;
import hl7OrgV3.POCDMT000040Section;
import hl7OrgV3.ST;
import hl7OrgV3.StrucDocText;
import hl7OrgV3.XActClassDocumentEntryOrganizer;
import hl7OrgV3.XActMoodDocumentObservation;
import hl7OrgV3.XActRelationshipEntryRelationship;
import java.math.BigInteger;
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
    private String surgeryAssociatedWithDeath = null;

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

    public static CauseOfDeath getCodedCauseOfDeathById(String id) throws SQLException {
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
        //    cod.setSurgeryAssociatedWithDeath(result.getString(DatabaseConnection.CAUSE_OF_DEATH_SURGERY_ASSOCIATED_WITH_DEATH));
        } else {
            return null;
        }

        return cod;
    }

    public static POCDMT000040Section populateCauseOfDeathSection(POCDMT000040Section section, CauseOfDeath cod) {
        
        
        II templateIdSection = section.addNewTemplateId();
        templateIdSection.setRoot("2.16.840.1.113883.10.20.26.1.2.4");
        templateIdSection.setExtension("2016-12-01");

        CE code = section.addNewCode();
        code.setCode("11450-4");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Problem list");

        ST title = section.addNewTitle();
        title.newCursor().setTextValue("Cause Of Death Section");

        StrucDocText text = section.addNewText();
        
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        sb.append("<thead>");
        sb.append("<tr><th>Cause Of Death</th></tr>");
        sb.append("<tr><th>Surgery Associated With Death</th></tr>");
        sb.append("<tr><th>Injury Involved In Death</th></tr>");        
        sb.append("<tr><th>Tobacco Use Contributed to Death</th></tr>");
        sb.append("</thead>");
        sb.append("<tr>" + cod.getCauseOfDeath() + " </td></tr>");
        sb.append("<tr>" + cod.getSurgeryAssociatedWithDeath() + " </td></tr>");
        sb.append("<tr>" + cod.getInjuryInvolvedInDeath() + " </td></tr>");
        sb.append("<tr>" + cod.getTobaccoUse() + " </td></tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        text.newCursor().setTextValue(sb.toString());       
        
        POCDMT000040Entry causalInformationEntry = section.addNewEntry();
        POCDMT000040Organizer causalInformationOrganizer = causalInformationEntry.addNewOrganizer();
        CauseOfDeath.populateDeathCausalInformationOrganizer(causalInformationOrganizer, cod);

        POCDMT000040Entry injuryEntry = section.addNewEntry();
        POCDMT000040Organizer injuryOrganizer = injuryEntry.addNewOrganizer();
        CauseOfDeath.populateInjuryOrganizer(injuryOrganizer, cod);
                
        POCDMT000040Entry pregnancyStatusEntry = section.addNewEntry();
        POCDMT000040Observation pregnancyStatusObservation = pregnancyStatusEntry.addNewObservation();
        CauseOfDeath.populatePregnancyStatusObservation(pregnancyStatusObservation, cod);

        POCDMT000040Entry tobaccoUseEntry = section.addNewEntry();
        POCDMT000040Observation tobaccoUseObservation = tobaccoUseEntry.addNewObservation();
        CauseOfDeath.populateTobaccoUseObservation(tobaccoUseObservation, cod);

        return section;
    }
    
    public static POCDMT000040Section populateCodedCauseOfDeathSection(POCDMT000040Section section, CauseOfDeath cod) {

        II templateIdSection = section.addNewTemplateId();
        templateIdSection.setRoot("2.16.840.1.113883.10.20.26.1.2.5");
        templateIdSection.setExtension("2016-12-01");

        CE code = section.addNewCode();
        code.setCode("11450-4");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Problem list");

        ST title = section.addNewTitle();
        title.newCursor().setTextValue("Coded Cause Of Death Section");

        StrucDocText text = section.addNewText();
        
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        sb.append("<thead>");
        sb.append("<tr><th>Cause Of Death</th></tr>");
        sb.append("<tr><th>Disease Onset To Death Interval</th></tr>");
        sb.append("<tr><th>Injury Involved In Death</th></tr>");        
        sb.append("<tr><th>Tobacco Use Contributed to Death</th></tr>");
        sb.append("</thead>");
        sb.append("<tr>" + cod.getCauseOfDeath() + " </td></tr>");
        sb.append("<tr>" + cod.getDiseaseOnsetToDeathInterval() + " </td></tr>");
        sb.append("<tr>" + cod.getInjuryInvolvedInDeath() + " </td></tr>");
        sb.append("<tr>" + cod.getTobaccoUse() + " </td></tr>");
        sb.append("</tbody>");
        sb.append("</table>");
        text.newCursor().setTextValue(sb.toString());

        POCDMT000040Entry causalInformationEntry = section.addNewEntry();
        POCDMT000040Organizer causalInformationOrganizer = causalInformationEntry.addNewOrganizer();
        CauseOfDeath.populateCodedDeathCausalInformationOrganizer(causalInformationOrganizer, cod);

        POCDMT000040Entry injuryEntry = section.addNewEntry();
        POCDMT000040Organizer injuryOrganizer = injuryEntry.addNewOrganizer();
        CauseOfDeath.populateInjuryOrganizer(injuryOrganizer, cod);

        POCDMT000040Entry pregnancyStatusEntry = section.addNewEntry();
        POCDMT000040Observation pregnancyStatusObservation = pregnancyStatusEntry.addNewObservation();
        CauseOfDeath.populatePregnancyStatusObservation(pregnancyStatusObservation, cod);

        POCDMT000040Entry tobaccoUseEntry = section.addNewEntry();
        POCDMT000040Observation tobaccoUseObservation = tobaccoUseEntry.addNewObservation();
        CauseOfDeath.populateTobaccoUseObservation(tobaccoUseObservation, cod);

        POCDMT000040Entry activityEntry = section.addNewEntry();
        POCDMT000040Observation activityObservation = activityEntry.addNewObservation();
        CauseOfDeath.populateActivityAtTimeOfDeathObservation(activityObservation, cod);

        return section;
        
    }

    private static void populateDeathCausalInformationOrganizer(POCDMT000040Organizer organizer, CauseOfDeath cod) {
        
        organizer.setClassCode(XActClassDocumentEntryOrganizer.CLUSTER);
        organizer.setMoodCode("EVN");

        II templateId = organizer.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.6");
        templateId.setExtension("2016-12-01");
    
        CS statusCode = organizer.addNewStatusCode();
        statusCode.setCode("completed");

        POCDMT000040Component4 causeOfDeathTextComponent = organizer.addNewComponent();
        causeOfDeathTextComponent.addNewSequenceNumber().setValue(BigInteger.ONE);
        causeOfDeathTextComponent.addNewSeperatableInd().setValue(false);
        POCDMT000040Observation causeOfDeathTextObservation = causeOfDeathTextComponent.addNewObservation();
        CauseOfDeath.populateCauseOfDeathTextObservation(causeOfDeathTextObservation, cod);
        
        
        
        
        
    }

    
    private static POCDMT000040Organizer populateCodedDeathCausalInformationOrganizer(POCDMT000040Organizer organizer, CauseOfDeath cod) {

        organizer.setClassCode(XActClassDocumentEntryOrganizer.CLUSTER);
        organizer.setMoodCode("EVN");

        II templateId = organizer.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.26");
        templateId.setExtension("2016-12-01");

        CS statusCode = organizer.addNewStatusCode();
        statusCode.setCode("completed");

        POCDMT000040Component4 entityAxisCodeComponent = organizer.addNewComponent();
        entityAxisCodeComponent.addNewSequenceNumber().setValue(BigInteger.ONE);
        entityAxisCodeComponent.addNewSeperatableInd().setValue(false);

        POCDMT000040Observation axisCodeObservation = entityAxisCodeComponent.addNewObservation();
        CauseOfDeath.populateEntityAxisCodeObservation(axisCodeObservation, cod);

        POCDMT000040Component4 recordAxisCodeComponent = organizer.addNewComponent();
        POCDMT000040Observation recordAxisCodeObservation = recordAxisCodeComponent.addNewObservation();
        CauseOfDeath.populateRecordAxisCodeObservation(recordAxisCodeObservation, cod);

        POCDMT000040Component4 underlyingComponent = organizer.addNewComponent();
        POCDMT000040Observation underlyingObservation = underlyingComponent.addNewObservation();
        CauseOfDeath.populateUnderlyingObservation(underlyingObservation, cod);

        POCDMT000040Component4 conversionFlagComponent = organizer.addNewComponent();
        POCDMT000040Observation conversionFlagObservation = conversionFlagComponent.addNewObservation();
        CauseOfDeath.populateConversionFlagObservation(conversionFlagObservation, cod);

        return organizer;
        
    }

    private static POCDMT000040Observation populateEntityAxisCodeObservation(POCDMT000040Observation observation, CauseOfDeath cod) {
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.27");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("80356-9");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Cause of death entity axis code");

        CD value = CD.Factory.newInstance();
        //TODO: Placeholder?
        value.setCode(cod.getCauseOfDeath());
        observation.addNewValue();
        observation.setValueArray(0, value);

        POCDMT000040EntryRelationship relationship = observation.addNewEntryRelationship();
        relationship.setTypeCode(XActRelationshipEntryRelationship.COMP);
        POCDMT000040Observation sequenceWithinLineObservation = relationship.addNewObservation();
        CauseOfDeath.populateSequenceWithinLineObservation(sequenceWithinLineObservation, cod);

        return observation;
    }

    private static POCDMT000040Observation populateSequenceWithinLineObservation(POCDMT000040Observation observation, CauseOfDeath cod) {

        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.30");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("PHC1427");
        code.setCodeSystem("2.16.840.1.114222.4.5.274");
        code.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        code.setDisplayName("Sequence within line");

        INT value = INT.Factory.newInstance();
        value.setValue(BigInteger.ONE);
        observation.addNewValue();
        observation.setValueArray(0, value);

        return observation;
    }

    private static POCDMT000040Observation populateRecordAxisCodeObservation(POCDMT000040Observation observation, CauseOfDeath cod) {

        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.28");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("80357-7");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Cause of death record axis code");

        CD value = CD.Factory.newInstance();
        //TODO: Placeholder?
        value.setCode(cod.getCauseOfDeath());
        observation.addNewValue();
        observation.setValueArray(0, value);

        return observation;

    }

    private static POCDMT000040Observation populateUnderlyingObservation(POCDMT000040Observation observation, CauseOfDeath cod) {

        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.29");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("80358-5");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Cause of death.underlying [Automated]");

        CD value = CD.Factory.newInstance();
        //TODO: Placeholder?
        value.setCode(cod.getCauseOfDeath());
        observation.addNewValue();
        observation.setValueArray(0, value);

        return observation;

    }

    private static POCDMT000040Observation populateConversionFlagObservation(POCDMT000040Observation observation, CauseOfDeath cod) {

        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.31");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("PHC1422");
        code.setCodeSystem("2.16.840.1.114222.4.5.274");
        code.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        code.setDisplayName("Conversion Flag");

        CD value = CD.Factory.newInstance();
        value.setCode("PHC1376");
        value.setCodeSystem("2.16.840.1.114222.4.5.274");
        value.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        value.setDisplayName("Conversion using non-ambivalent table entries");
        observation.addNewValue();
        observation.setValueArray(0, value);

        return observation;
        
    }

    private static POCDMT000040Organizer populateInjuryOrganizer(POCDMT000040Organizer organizer, CauseOfDeath cod) {
        
        organizer.setClassCode(XActClassDocumentEntryOrganizer.CLUSTER);
        organizer.setMoodCode("EVN");

        II templateId = organizer.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.9");
        templateId.setExtension("2016-12-01");

        CS statusCode = organizer.addNewStatusCode();
        //statusCode.setCode("completed");

        POCDMT000040Component4 injuryComponent = organizer.addNewComponent();
        POCDMT000040Observation injuryInvolvedInDeathObservation = injuryComponent.addNewObservation();
        CauseOfDeath.populateInjuryInvolvedInDeathObservation(injuryInvolvedInDeathObservation,cod);
        
        return organizer;
        
    }

    private static POCDMT000040Observation populateInjuryInvolvedInDeathObservation(POCDMT000040Observation observation, CauseOfDeath cod) {
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.40");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("71481-6");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Did the death of this person involve injury of any kind");
        
        CD value = CD.Factory.newInstance();
        value.setCode(cod.getInjuryInvolvedInDeath());
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        return observation;
        
        
    }

    private static POCDMT000040Observation populatePregnancyStatusObservation(POCDMT000040Observation observation, CauseOfDeath cod) {
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.12");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("69442-2");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Pregnancy Status");
        

        //TODO: Hardcoded for now
        CD value = CD.Factory.newInstance();
        value.setCode("PHC1260");
        value.setCodeSystem("2.16.840.1.114222.4.5.274");
        value.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        value.setDisplayName("Not pregnant within the past year");
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        return observation;
        
    }

    private static POCDMT000040Observation populateTobaccoUseObservation(POCDMT000040Observation observation, CauseOfDeath cod) {
        
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();                            
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.14");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("69443-0");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Did tobacco use contribute to death");
        

        CD value = CD.Factory.newInstance();
        value.setCode(cod.tobaccoUse);
        value.setCodeSystem("2.16.840.1.113883.6.96");
        value.setCodeSystemName("SNOMED CT");
       
        observation.addNewValue();
        observation.setValueArray(0, value);
                
        return observation;
        
    }
    private static POCDMT000040Observation populateActivityAtTimeOfDeathObservation(POCDMT000040Observation observation, CauseOfDeath cod) {
        
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.21");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("80626-5");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Activity at time of death");
        
        //Hardcoded for now
        CD value = CD.Factory.newInstance();
        value.setCode("PHC1350");
        value.setCodeSystem("2.16.840.1.114222.4.5.274");
        value.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        value.setDisplayName("During unspecified activity");
        
        observation.addNewValue();
        observation.setValueArray(0, value);
                
        return observation;
    }

    private static POCDMT000040Observation populateCauseOfDeathTextObservation(POCDMT000040Observation observation, CauseOfDeath cod) {
       
        
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.16");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("69543-9");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Cause Of Death (US Standard Certificate of Death)");
        
        ED value = ED.Factory.newInstance();        
        value.newCursor().setTextValue(cod.getCauseOfDeath());
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        POCDMT000040EntryRelationship deathIntervalER = observation.addNewEntryRelationship();
        deathIntervalER.setTypeCode(XActRelationshipEntryRelationship.COMP);
        POCDMT000040Observation deathIntervalObservation = deathIntervalER.addNewObservation();
        CauseOfDeath.populateDeathIntervalObservation(deathIntervalObservation, cod);
        
        return observation;
    }
   
    
    private static POCDMT000040Observation populateDeathIntervalObservation(POCDMT000040Observation observation, CauseOfDeath cod) {
        
        
        
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.18");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("69440-6");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Disease onset to death interval");
        
        ED value = ED.Factory.newInstance();        
        value.newCursor().setTextValue(cod.getDiseaseOnsetToDeathInterval());
        observation.addNewValue();
        observation.setValueArray(0, value);
      
        return observation;
    }

    /**
     * @return the surgeryAssociatedWithDeath
     */
    public String getSurgeryAssociatedWithDeath() {
        return surgeryAssociatedWithDeath;
    }

    /**
     * @param surgeryAssociatedWithDeath the surgeryAssociatedWithDeath to set
     */
    public void setSurgeryAssociatedWithDeath(String surgeryAssociatedWithDeath) {
        this.surgeryAssociatedWithDeath = surgeryAssociatedWithDeath;
    }

    

}
