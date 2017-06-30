/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

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
    
    
}
