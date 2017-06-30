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
public class DeathCertificate {
    
    private Address certifierAddress = null;
    private String certifierType = null;

    /**
     * @return the certifierAddress
     */
    public Address getCertifierAddress() {
        return certifierAddress;
    }

    /**
     * @param certifierAddress the certifierAddress to set
     */
    public void setCertifierAddress(Address certifierAddress) {
        this.certifierAddress = certifierAddress;
    }

    /**
     * @return the certifierType
     */
    public String getCertifierType() {
        return certifierType;
    }

    /**
     * @param certifierType the certifierType to set
     */
    public void setCertifierType(String certifierType) {
        this.certifierType = certifierType;
    }
    
}
