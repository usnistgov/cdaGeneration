/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.EN;
import hl7OrgV3.PN;
import hl7OrgV3.POCDMT000040Section;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mccaffrey
 */
public class Name {
    
    private String lastName = null;
    private String firstName = null;
    private String middleName = null;
    private String prefix = null;
    private String suffix = null;

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @param middleName the middleName to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * @return the prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    public static Name getNameById(String id) throws SQLException {
        Name nm = new Name();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.NAME_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.NAME_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {
            nm.setFirstName(result.getString(DatabaseConnection.NAME_FIRST_NAME));
            nm.setLastName(result.getString(DatabaseConnection.NAME_LAST_NAME));
            nm.setMiddleName(result.getString(DatabaseConnection.NAME_MIDDLE_NAME));
            nm.setPrefix(result.getString(DatabaseConnection.NAME_PREFIX));
            nm.setSuffix(result.getString(DatabaseConnection.NAME_SUFFIX));
            
        } else {
            return null;
        }
        return nm;
    }
    
    public static EN populateName(EN en, Name name) {
        List<String> use = new ArrayList<String>();
        use.add("L");
        en.setUse(use);
        en.addNewFamily().newCursor().setTextValue(name.getLastName());
        en.addNewGiven().newCursor().setTextValue(name.getFirstName());
        en.addNewGiven().newCursor().setTextValue(name.getMiddleName());
        en.addNewPrefix().newCursor().setTextValue(name.getPrefix());
        en.addNewSuffix().newCursor().setTextValue(name.getSuffix());
        
        return en;
        
    }
    
    public static PN populateName(PN pn, Name name) {
        List<String> use = new ArrayList<String>();
        use.add("L");
        pn.setUse(use);        
        pn.addNewFamily().newCursor().setTextValue(name.getLastName());
        pn.addNewGiven().newCursor().setTextValue(name.getFirstName());
        pn.addNewGiven().newCursor().setTextValue(name.getMiddleName());
        pn.addNewPrefix().newCursor().setTextValue(name.getPrefix());
        pn.addNewSuffix().newCursor().setTextValue(name.getSuffix());
        
        return pn;
    }
    
    
    public EN toEN() {
        EN en = EN.Factory.newInstance();
        Name.populateName(en, this);
        return en;
    }
    
    public PN toPN() {
        
        PN pn = PN.Factory.newInstance();
        Name.populateName(pn, this);
        return pn;
        
    }
    
}
