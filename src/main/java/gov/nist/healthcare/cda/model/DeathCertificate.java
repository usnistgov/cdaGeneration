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
    
    public static DeathCertificate getDeathCertificateById(String id) throws SQLException {
        DeathCertificate dc = new DeathCertificate();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.DEATH_CERTIFICATE_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.DEATH_CERTIFICATE_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {
            dc.setCertifierType(result.getString(DatabaseConnection.DEATH_CERTIFICATE_CERTIFIER_TYPE));
            String addressID = result.getString(DatabaseConnection.DEATH_CERTIFICATE_ADDRESS_ID);
            Address add = Address.getAddressById(addressID);
            dc.setCertifierAddress(add);
        } else {
            return null;
        }

        return dc;
    }    
}
