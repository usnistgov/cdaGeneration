/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.CD;
import hl7OrgV3.II;
import hl7OrgV3.POCDMT000040Act;
import hl7OrgV3.XActClassDocumentEntryAct;
import hl7OrgV3.XDocumentActMood;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mccaffrey
 */
public class DeathCertification {
   
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
    
    public static DeathCertification getDeathCertificateById(String id) throws SQLException {
        DeathCertification dc = new DeathCertification();

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
    
    static POCDMT000040Act populateDeathCertificationAct(POCDMT000040Act act, DeathCertification dc) {
     
        act.setClassCode(XActClassDocumentEntryAct.ACT);
        act.setMoodCode(XDocumentActMood.EVN);
        
        II templateId = act.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.7");
        templateId.setExtension("2016-12-01");
        
        CD code = act.addNewCode();       
        code.setCode("308646001");
        code.setCodeSystem("2.16.840.1.113883.6.96");
        code.setCodeSystemName("SNOMED CT");
        code.setDisplayName("Death certification");
        
        act.addNewStatusCode().setCode("completed");
        
        act.addNewEffectiveTime();
        
        // TODO: This may need to be expanded?
        
        return act;
    }
        
    
}
