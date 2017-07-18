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
    
    
    
}
