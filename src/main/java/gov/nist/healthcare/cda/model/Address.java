/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.AD;
import hl7OrgV3.AdxpCity;
import hl7OrgV3.AdxpCountry;
import hl7OrgV3.AdxpCounty;
import hl7OrgV3.AdxpPostalCode;
import hl7OrgV3.AdxpState;
import hl7OrgV3.AdxpStreetAddressLine;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.xmlbeans.XmlOptions;

/**
 *
 * @author mccaffrey
 */
public class Address {

    private String streetAddress1 = null;
    private String streetAddress2 = null;
    private String city = null;
    private String state = null;
    private String county = null;
    private String postalCode = null;
    private String country = null;

    /**
     * @return the streetAddress1
     */
    public String getStreetAddress1() {
        return streetAddress1;
    }

    /**
     * @param streetAddress1 the streetAddress1 to set
     */
    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    /**
     * @return the streetAddress2
     */
    public String getStreetAddress2() {
        return streetAddress2;
    }

    /**
     * @param streetAddress2 the streetAddress2 to set
     */
    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the county
     */
    public String getCounty() {
        return county;
    }

    /**
     * @param county the county to set
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    public static Address getAddressById(String id) throws SQLException {
        Address add = new Address();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.ADDRESS_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.ADDRESS_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {
            add.setCity(result.getString(DatabaseConnection.ADDRESS_CITY));
            add.setCountry(result.getString(DatabaseConnection.ADDRESS_COUNTRY));
            add.setCounty(result.getString(DatabaseConnection.ADDRESS_COUNTY));
            add.setPostalCode(result.getString(DatabaseConnection.ADDRESS_POSTAL_CODE));
            add.setState(result.getString(DatabaseConnection.ADDRESS_STATE));
            add.setStreetAddress1(result.getString(DatabaseConnection.ADDRESS_STREETADDRESS1));
            add.setStreetAddress2(result.getString(DatabaseConnection.ADDRESS_STREETADDRESS2));

        } else {
            return null;
        }
        return add;
    }

    public AD toAD() {
        AD ad = AD.Factory.newInstance();
        return Address.populateAD(ad, this);        
    }
    
    public static AD populateAD(AD ad, Address addr) {

        if (addr.getCity() != null) {
            AdxpCity city = ad.addNewCity();
            city.newCursor().setTextValue(addr.getCity());
        }
        if (addr.getCountry() != null) {
            AdxpCountry country = ad.addNewCountry();
            country.newCursor().setTextValue(addr.getCountry());
        }

        if (addr.getCounty() != null) {
            AdxpCounty county = ad.addNewCounty();
            county.newCursor().setTextValue(addr.getCounty());
        }
        if (addr.getPostalCode() != null) {
            AdxpPostalCode postalCode = ad.addNewPostalCode();
            postalCode.newCursor().setTextValue(addr.getPostalCode());
        }
        if (addr.getState() != null) {
            AdxpState state = ad.addNewState();
            state.newCursor().setTextValue(addr.getState());
        }
        if (addr.getStreetAddress1() != null) {
            AdxpStreetAddressLine sa1 = ad.addNewStreetAddressLine();
            sa1.newCursor().setTextValue(addr.getStreetAddress1());
        }
        if (addr.getStreetAddress2() != null) {
            AdxpStreetAddressLine sa2 = ad.addNewStreetAddressLine();
            sa2.newCursor().setTextValue(addr.getStreetAddress2());
        }

        return ad;
    }

    public static void main(String[] args) throws SQLException {

        Address add = Address.getAddressById("Add1");

        //ClinicalDocumentDocument1 cdaRoot = ClinicalDocumentDocument1.Factory.newInstance();        
        //POCDMT000040ClinicalDocument1 cda = POCDMT000040ClinicalDocument1.Factory.newInstance();
        AD ad = AD.Factory.newInstance();
        ad = Address.populateAD(ad, add);

        XmlOptions options = new XmlOptions();
        options.setCharacterEncoding("UTF-8");
        options.setSavePrettyPrint();

        System.out.println(ad.xmlText(options));

    }

}
