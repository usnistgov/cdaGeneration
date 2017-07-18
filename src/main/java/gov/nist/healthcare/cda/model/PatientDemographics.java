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
public class PatientDemographics {
    
    // PID|1||987-65-4321^^^&2.16.840.1.113883.4.1&ISO^SS||Smith^Madeline^NMI||19350312|F|||5590 Lockwood Drive^^2403120^37^20621^US||||||||||||||||||20101102140000-0500|Y

    private String socialSecurityNumber = null;
    private Name name = null;
    private String birthTime = null;
    private String sex = null;
    private Address address = null;
    private String deathTime = null;

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
    
    
}
