/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.AD;
import hl7OrgV3.CE;
import hl7OrgV3.II;
import hl7OrgV3.POCDMT000040Birthplace;
import hl7OrgV3.POCDMT000040Patient;
import hl7OrgV3.POCDMT000040PatientRole;
import hl7OrgV3.POCDMT000040Place;
import hl7OrgV3.POCDMT000040RecordTarget;
import hl7OrgV3.TS;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.XmlCursor;


/**
 *
 * @author mccaffrey
 */
public class PatientDemographics {
    
    // PID|1||987-65-4321^^^&2.16.840.1.113883.4.1&ISO^SS||Smith^Madeline^NMI||19350312|F|||5590 Lockwood Drive^^2403120^37^20621^US||||||||||||||||||20101102140000-0500|Y

    private String socialSecurityNumber = null;
    private Name name = null;
    private String birthTime = null;
    private String sex = null;
    private Address address = null;
    private String deathTime = null;
    private String raceCode1 = null;
    private String raceCode2 = null;
    private String ethnicGroup = null;
    private String maritalStatus = null;
    private String birthPlace = null;
    
    /**
     * @return the socialSecurityNumber
     */
    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    /**
     * @param socialSecurityNumber the socialSecurityNumber to set
     */
    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    /**
     * @return the name
     */
    public Name getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * @return the birthTime
     */
    public String getBirthTime() {
        return birthTime;
    }

    /**
     * @param birthTime the birthTime to set
     */
    public void setBirthTime(String birthTime) {
        this.birthTime = birthTime;
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

    /**
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return the deathTime
     */
    public String getDeathTime() {
        return deathTime;
    }

    /**
     * @param deathTime the deathTime to set
     */
    public void setDeathTime(String deathTime) {
        this.deathTime = deathTime;
    }
    
    public static PatientDemographics getPatientDemographicsById(String id) throws SQLException {
        PatientDemographics pd = new PatientDemographics();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.PATIENT_DEMOGRAPHICS_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.PATIENT_DEMOGRAPHICS_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {            
            pd.setBirthTime(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_BIRTH_TIME));
            pd.setDeathTime(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_DEATH_TIME));
            pd.setSex(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_SEX));
            pd.setSocialSecurityNumber(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_SOCIAL_SECURITY_NUMBER));
            pd.setRaceCode1(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_RACE_CODE_1));
            pd.setRaceCode2(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_RACE_CODE_2));
            pd.setEthnicGroup(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_ETHNIC_GROUP));
            pd.setMaritalStatus(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_MARITAL_STATUS));
            pd.setBirthPlace(result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_BIRTH_PLACE));
            String addressID = result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_ADDRESS_ID);
            String nameID = result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_NAME_ID);
            Address add = Address.getAddressById(addressID);
            Name name = Name.getNameById(nameID);
            pd.setAddress(add);
            pd.setName(name);                        
        } else {
            return null;
        }
        return pd;
    }
    
    
    public static POCDMT000040RecordTarget populateRecordTarget(POCDMT000040RecordTarget recordTarget, PatientDemographics patientDemographics) {
    
        POCDMT000040PatientRole patientRole = recordTarget.addNewPatientRole();
        patientRole.addNewAddr();
        patientRole.setAddrArray(0, patientDemographics.getAddress().toAD());
        II ssn = patientRole.addNewId();
        ssn.setExtension(patientDemographics.getSocialSecurityNumber());
        ssn.setRoot("2.16.840.1.113883.4.1");
        
        
        POCDMT000040Patient patient = patientRole.addNewPatient();
        patient.addNewName();
        patient.setNameArray(0, patientDemographics.getName().toPN());
        patient.addNewBirthTime().setValue(patientDemographics.getBirthTime());
        patient.addNewAdministrativeGenderCode().setCode(patientDemographics.getSex());
        
        CE raceCode1 = patient.addNewRaceCode();
        raceCode1.setCodeSystem("2.16.840.1.113883.6.238");
        raceCode1.setCodeSystemName("Race &amp; Ethnicity - CDC");
        raceCode1.setCode(patientDemographics.getRaceCode1());

        CE raceCode2 = patient.addNewRaceCode2();
        raceCode2.setCodeSystem("2.16.840.1.113883.6.238");
        raceCode2.setCodeSystemName("Race &amp; Ethnicity - CDC");
        raceCode2.setCode(patientDemographics.getRaceCode2());
                
        CE ethnicCode = patient.addNewEthnicGroupCode();
        ethnicCode.setCodeSystem("2.16.840.1.113883.6.238");
        ethnicCode.setCodeSystemName("Race &amp; Ethnicity - CDC");
        ethnicCode.setCode(patientDemographics.getEthnicGroup());
                                             
        TS deceasedTime = patient.addNewDeceasedTime();
        deceasedTime.setValue(patientDemographics.getDeathTime());
        QName type = new QName("http://www.w3.org/2001/XMLSchema-instance","type");  
        XmlCursor cursor = deceasedTime.newCursor();
        cursor.setAttributeText(type, "UVP_TS");                                
        QName probability = new QName("probability");
        cursor.setAttributeText(probability, "1");   
        patient.addNewDeceasedInd().setValue(true);
        
        if(patientDemographics.getMaritalStatus() != null && !patientDemographics.getMaritalStatus().isEmpty()) {
            CE maritalStatus = patient.addNewMaritalStatusCode();
            maritalStatus.setCodeSystem("2.16.840.1.113883.12.2");
            maritalStatus.setCodeSystemName("Marital status (HL7)");
            maritalStatus.setCode(patientDemographics.getMaritalStatus());                        
        }
        
        if(patientDemographics.getBirthPlace() != null && !patientDemographics.getBirthPlace().isEmpty()) {
            POCDMT000040Birthplace birthPlace = patient.addNewBirthplace();
            POCDMT000040Place place = birthPlace.addNewPlace();
            AD address = place.addNewAddr();
            address.addNewCity().setNullFlavor("UNK");
            address.addNewState().setNullFlavor("UNK");
            address.addNewCountry().newCursor().setTextValue(patientDemographics.getBirthPlace());                     
        }
        return recordTarget;
        
    }

    /**
     * @return the raceCode1
     */
    public String getRaceCode1() {
        return raceCode1;
    }

    /**
     * @param raceCode1 the raceCode1 to set
     */
    public void setRaceCode1(String raceCode1) {
        this.raceCode1 = raceCode1;
    }

    /**
     * @return the raceCode2
     */
    public String getRaceCode2() {
        return raceCode2;
    }

    /**
     * @param raceCode2 the raceCode2 to set
     */
    public void setRaceCode2(String raceCode2) {
        this.raceCode2 = raceCode2;
    }

    /**
     * @return the ethnicCode
     */
    public String getEthnicGroup() {
        return ethnicGroup;
    }

    /**
     * @param ethnicCode the ethnicCode to set
     */
    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
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
     * @return the birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * @param birthPlace the birthPlace to set
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }
    
}
