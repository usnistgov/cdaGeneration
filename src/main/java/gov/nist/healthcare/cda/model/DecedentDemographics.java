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
import hl7OrgV3.POCDMT000040Section;
import hl7OrgV3.PQ;
import hl7OrgV3.ST;
import hl7OrgV3.StrucDocText;
import hl7OrgV3.XActClassDocumentEntryAct;
import hl7OrgV3.XActMoodDocumentObservation;
import hl7OrgV3.XActRelationshipEntryRelationship;
import hl7OrgV3.XDocumentActMood;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mccaffrey
 */
public class DecedentDemographics {

    private String ageAtDeath = null;
    private String birthCertificateId = null;
    private String birthCertificateDataYear = null;
    private String educationLevel = null;
    private String maritalStatus = null;
    private String occupation = null;
    private String sex = null;

    /**
     * @return the ageAtDeath
     */
    public String getAgeAtDeath() {
        return ageAtDeath;
    }

    /**
     * @param ageAtDeath the ageAtDeath to set
     */
    public void setAgeAtDeath(String ageAtDeath) {
        this.ageAtDeath = ageAtDeath;
    }

    /**
     * @return the birthCertificateId
     */
    public String getBirthCertificateId() {
        return birthCertificateId;
    }

    /**
     * @param birthCertificateId the birthCertificateId to set
     */
    public void setBirthCertificateId(String birthCertificateId) {
        this.birthCertificateId = birthCertificateId;
    }

    /**
     * @return the birthCertificateDataYear
     */
    public String getBirthCertificateDataYear() {
        return birthCertificateDataYear;
    }

    /**
     * @param birthCertificateDataYear the birthCertificateDataYear to set
     */
    public void setBirthCertificateDataYear(String birthCertificateDataYear) {
        this.birthCertificateDataYear = birthCertificateDataYear;
    }

    /**
     * @return the educationLevel
     */
    public String getEducationLevel() {
        return educationLevel;
    }

    /**
     * @param educationLevel the educationLevel to set
     */
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    /**
     * @return the maritalStatus
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * @param maritalStatus the maritalStatus to set
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * @return the occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation the occupation to set
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    public static DecedentDemographics getDecedentDemographicsById(String id) throws SQLException {

        DecedentDemographics dd = new DecedentDemographics();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.DECEDENT_DEMOGRAPHICS_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.DECEDENT_DEMOGRAPHICS_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {
            dd.setAgeAtDeath(result.getString(DatabaseConnection.DECEDENT_DEMOGRAPHICS_AGE_AT_DEATH));
            dd.setBirthCertificateId(result.getString(DatabaseConnection.DECEDENT_DEMOGRAPHICS_BIRTH_CERTIFICATE_ID));
            dd.setBirthCertificateDataYear(result.getString(DatabaseConnection.DECEDENT_DEMOGRAPHICS_BIRTH_CERTIFICATE_DATA_YEAR));
            dd.setEducationLevel(result.getString(DatabaseConnection.DECEDENT_DEMOGRAPHICS_EDUCATION_LEVEL));
            dd.setMaritalStatus(result.getString(DatabaseConnection.DECEDENT_DEMOGRAPHICS_MARITAL_STATUS));
            dd.setOccupation(result.getString(DatabaseConnection.DECEDENT_DEMOGRAPHICS_OCCUPATION));
            dd.setSex(result.getString(DatabaseConnection.DECEDENT_DEMOGRAPHICS_SEX));

        } else {
            return null;
        }

        return dd;

    }

    public static POCDMT000040Section populateDecedentDemographicsSection(POCDMT000040Section section, DecedentDemographics dd) {

        II templateIdSection = section.addNewTemplateId();
        templateIdSection.setRoot("2.16.840.1.113883.10.20.26.1.2.1");
        templateIdSection.setExtension("2016-12-01");

        CE code = section.addNewCode();
        code.setCode("45970-1");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Demographic Section");

        ST title = section.addNewTitle();
        title.newCursor().setTextValue("Decedent Demographics Section");

        StrucDocText text = section.addNewText();
        text.newCursor().setTextValue("He's dead, Jim.");

        POCDMT000040Entry ageAtDeathEntry = section.addNewEntry();
        POCDMT000040Observation ageAtDeathObservation = ageAtDeathEntry.addNewObservation();
        DecedentDemographics.populateAgeAtDeathObservation(ageAtDeathObservation, dd.getAgeAtDeath());

        POCDMT000040Entry birthCertificateEntry = section.addNewEntry();
        POCDMT000040Act birthCertificateAct = birthCertificateEntry.addNewAct();        
        DecedentDemographics.populateBirthCertificateAct(birthCertificateAct, dd.getBirthCertificateId(), dd.getBirthCertificateDataYear());

        POCDMT000040Entry educationLevelEntry = section.addNewEntry();
        POCDMT000040Observation educationLevelObservation = educationLevelEntry.addNewObservation();
        DecedentDemographics.populateEducationLevelObservation(educationLevelObservation, dd.getEducationLevel());

        POCDMT000040Entry maritalStatusEntry = section.addNewEntry();
        POCDMT000040Observation maritalStatusObservation = maritalStatusEntry.addNewObservation();
        DecedentDemographics.populateMaritalStatusObservation(maritalStatusObservation, dd.getMaritalStatus());

        POCDMT000040Entry occupationEntry = section.addNewEntry();
        POCDMT000040Observation occupationObservation = occupationEntry.addNewObservation();
        DecedentDemographics.populateOccupationObservation(occupationObservation, dd.getOccupation());

        POCDMT000040Entry sexEntry = section.addNewEntry();
        POCDMT000040Observation sexObservation = sexEntry.addNewObservation();
        DecedentDemographics.populateSexObservation(sexObservation, dd.getSex());

        return section;
    }

    public static POCDMT000040Observation populateAgeAtDeathObservation(POCDMT000040Observation observation, String ageAtDeath) {

        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        observation.addNewId();
        II templateId1 = observation.addNewTemplateId();
        templateId1.setRoot("2.16.840.1.113883.10.20.26.1.3.36");
        templateId1.setExtension("2016-12-01");

        II templateId2 = observation.addNewTemplateId();
        templateId2.setRoot("2.16.840.1.113883.10.20.22.4.31");

        CD code = observation.addNewCode();
        code.setCode("445518008");
        code.setCodeSystem("2.16.840.1.113883.6.96");
        code.setCodeSystemName("SNOMED-CT");
        code.setDisplayName("Age At Onset");

        observation.addNewStatusCode().setCode("completed");
        
        PQ value = PQ.Factory.newInstance();
        value.setValue("65");
        value.setUnit("a");
        observation.addNewValue();
        observation.setValueArray(0, value);

        POCDMT000040EntryRelationship ageEditEntryRelationship = observation.addNewEntryRelationship();
        ageEditEntryRelationship.setTypeCode(XActRelationshipEntryRelationship.SUBJ);
        ageEditEntryRelationship.setInversionInd(true);
        POCDMT000040Observation ageEditObservation = ageEditEntryRelationship.addNewObservation();

        ageEditObservation.setClassCode("OBS");
        ageEditObservation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId3 = ageEditObservation.addNewTemplateId();
        templateId3.setRoot("2.16.840.1.113883.10.20.26.1.3.37");
        templateId3.setExtension("2016-12-01");

        CD ageEditCode = ageEditObservation.addNewCode();
        ageEditCode.setCode("PHC1421");
        ageEditCode.setCodeSystem("2.16.840.1.114222.4.5.274");
        ageEditCode.setCodeSystemName("PHIN VS (CDC Local Coding System");
        ageEditCode.setDisplayName("PHC1421");

        CD ageEditValue = CD.Factory.newInstance();
        ageEditValue.setCode("PHC1362");
        ageEditValue.setCodeSystem("2.16.840.1.114222.4.5.274");
        ageEditValue.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        ageEditValue.setDisplayName("Edit Passed");
        ageEditObservation.addNewValue();
        ageEditObservation.setValueArray(0, ageEditValue);

        return observation;

    }

    private static POCDMT000040Act populateBirthCertificateAct(POCDMT000040Act act, String birthCertificateId, String birthCertificateDataYear) {

        act.setClassCode(XActClassDocumentEntryAct.REG);
        act.setMoodCode(XDocumentActMood.EVN);

        II templateId1 = act.addNewTemplateId();
        templateId1.setRoot("2.16.840.1.113883.10.20.26.1.3.45");
        templateId1.setExtension("2016-12-01");        
        act.addNewId().setExtension(birthCertificateId);                        
        CD code = act.addNewCode();
        code.setCode("444561001");
        code.setCodeSystem("2.16.840.1.113883.6.96");
        code.setCodeSystemName("SNOMED CT");
        code.setDisplayName("Birth certificate");

        act.addNewEffectiveTime().setValue(birthCertificateDataYear);

        return act;
    }

    private static POCDMT000040Observation populateEducationLevelObservation(POCDMT000040Observation observation, String educationLevel) {

        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId1 = observation.addNewTemplateId();
        templateId1.setRoot("2.16.840.1.113883.10.20.26.1.3.35");
        templateId1.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("80913-7");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Decedent education level");

        CD value = CD.Factory.newInstance();
        value.setCode("PHC1453");
        value.setCodeSystem("2.16.840.1.114222.4.5.274");
        value.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        observation.addNewValue();
        observation.setValueArray(0, value);

        POCDMT000040EntryRelationship editFlag = observation.addNewEntryRelationship();
        editFlag.setTypeCode(XActRelationshipEntryRelationship.SUBJ);
        editFlag.setInversionInd(true);

        POCDMT000040Observation editFlagObservation = editFlag.addNewObservation();
        editFlagObservation.setClassCode("OBS");
        editFlagObservation.setMoodCode(XActMoodDocumentObservation.EVN);

        II editFlagTemplate = editFlagObservation.addNewTemplateId();
        editFlagTemplate.setRoot("2.16.840.1.113883.10.20.26.1.3.43");
        editFlagTemplate.setExtension("2016-12-01");

        CD editFlagCode = editFlagObservation.addNewCode();
        editFlagCode.setCode("PHC1424");
        editFlagCode.setCodeSystem("2.16.840.1.114222.4.5.274");
        editFlagCode.setCodeSystemName("PHIN VS (CDC Local Coding System");
        editFlagCode.setDisplayName("Education Level Edit Flag");

        CD editFlagValue = CD.Factory.newInstance();
        editFlagValue.setCode("PHC1362");
        editFlagValue.setCodeSystem("2.16.840.1.114222.4.5.274");
        editFlagValue.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        editFlagValue.setDisplayName("Edit Passed");
        editFlagObservation.addNewValue();
        editFlagObservation.setValueArray(0, value);

        return observation;
        
    }

    private static POCDMT000040Observation populateMaritalStatusObservation(POCDMT000040Observation observation, String maritalStatus) {
    
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId1 = observation.addNewTemplateId();
        templateId1.setRoot("2.16.840.1.113883.10.20.26.1.3.49");
        templateId1.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("PHC1426");
        code.setCodeSystem("2.16.840.1.114222.4.5.274");
        code.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        code.setDisplayName("Edit Passed");
        
        CD value = CD.Factory.newInstance();
        value.setCode("PHC1362");
        value.setCodeSystem("2.16.840.1.114222.4.5.274");
        value.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        value.setDisplayName("Edit Passed");
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        return observation;
        
    }
    private static POCDMT000040Observation populateOccupationObservation(POCDMT000040Observation observation, String occupation) {
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId1 = observation.addNewTemplateId();
        templateId1.setRoot("2.16.840.1.113883.10.20.26.1.3.38");
        templateId1.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("21843-8");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("History of usual occuptation");
        
        
        CD value = CD.Factory.newInstance();
        value.setCode(occupation);
        value.setCodeSystem("2.16.840.1.114222.4.5.314");
        value.setCodeSystemName("Occupation CDC Census");
        observation.addNewValue();
        observation.setValueArray(0, value);
        
        POCDMT000040EntryRelationship industryER = observation.addNewEntryRelationship();
        industryER.setTypeCode(XActRelationshipEntryRelationship.COMP);
        POCDMT000040Observation industryObservation = industryER.addNewObservation();
        
        industryObservation.setClassCode("OBS");
        industryObservation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId2 = industryObservation.addNewTemplateId();
        templateId2.setRoot("2.16.840.1.113883.10.20.26.1.3.39");
        templateId2.setExtension("2016-12-01");

        CD code2 = industryObservation.addNewCode();
        code2.setCode("21844-6");
        code2.setCodeSystem("2.16.840.1.113883.6.1");
        code2.setCodeSystemName("LOINC");
        code2.setDisplayName("\"History of usual industry");
        
        CD value2 = CD.Factory.newInstance();
        // TODO: Placeholder
        value2.setCode("PLACEHOLDER");
        value2.setCodeSystem("2.16.840.1.114222.4.5.315");
        value2.setCodeSystemName("\"Industry CDC Census 2010");        
        industryObservation.addNewValue();
        industryObservation.setValueArray(0, value2);
        
        return observation;
        
        
    }

    
    private static POCDMT000040Observation populateSexObservation(POCDMT000040Observation observation, String sex) {
    
        
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId1 = observation.addNewTemplateId();
        templateId1.setRoot("2.16.840.1.113883.10.20.26.1.3.48");
        templateId1.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("PHC1432");
        code.setCodeSystem("2.16.840.1.114222.4.5.274");
        code.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        code.setDisplayName("Sex Edit Flag");
        
        CD value = CD.Factory.newInstance();
        value.setCode("PHC1362");
        value.setCodeSystem("2.16.840.1.114222.4.5.274");
        value.setCodeSystemName("PHIN VS (CDC Local Coding System)");
        value.setDisplayName("Edit Passed");
        observation.addNewValue();
        observation.setValueArray(0, value);
    
        return observation;
        
    }
   

    
    
}
