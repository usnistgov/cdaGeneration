/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mccaffrey
 */
public class DatabaseConnection {
    
    //TODO: Move this to a separate class?
    
    public static final String DATABASE_NAME = "VRDRTestData";
    public static final String ADDRESS_NAME = "Address";
    public static final String ADDRESS_ID = "AddressID";
    public static final String ADDRESS_STREETADDRESS1 = "StreetAddress1";
    public static final String ADDRESS_STREETADDRESS2 = "StreetAddress2";
    public static final String ADDRESS_CITY = "City";
    public static final String ADDRESS_STATE = "State";
    public static final String ADDRESS_COUNTY = "County";
    public static final String ADDRESS_POSTAL_CODE = "PostalCode";
    public static final String ADDRESS_COUNTRY = "Country";
    
    public static final String AUTOPSY_DETAILS_NAME = "AutopsyDetails";
    public static final String AUTOPSY_DETAILS_ID = "AutopsyDetailsID";
    public static final String AUTOPSY_DETAILS_RESULTS_AVAILABLE = "ResultsAvailable";
    
    public static final String CAUSE_OF_DEATH_NAME = "CauseOfDeath";
    public static final String CAUSE_OF_DEATH_ID = "CauseOfDeathID";
    public static final String CAUSE_OF_DEATH_CAUSE_OF_DEATH = "CauseOfDeath";
    public static final String CAUSE_OF_DEATH_DISEASE_ONSET_TO_DEATH_INTERVAL = "DiseaseOnsetToDeathInterval";
    public static final String CAUSE_OF_DEATH_TOBACCO_USE = "TobaccoUse";
    public static final String CAUSE_OF_DEATH_INJURY_INVOLVED_IN_DEATH = "InjuryInvolvedInDeath";
    public static final String CAUSE_OF_DEATH_SURGERY_ASSOCIATED_WITH_DEATH = "SurgeryAssociatedWithDeath";
    
    public static final String CORONER_REFERRAL_NAME = "CoronerReferral";
    public static final String CORONER_REFERRAL_ID = "CoronerReferralID";
    public static final String CORONER_REFERRAL_CORONER_REFERRAL_NOTE = "CoronerReferralNote";
    
    public static final String DEATH_ADMINISTRATION_NAME = "DeathAdministration";
    public static final String DEATH_ADMINISTRATION_ID = "DeathAdministrationID";
    public static final String DEATH_ADMINISTRATION_DATE_TIME_PRONOUNCED_DEAD = "DateTimePronouncedDead";
    
    public static final String DEATH_CERTIFICATE_NAME = "DeathCertificate";
    public static final String DEATH_CERTIFICATE_ID = "DeathCertificateID";
    public static final String DEATH_CERTIFICATE_ADDRESS_ID = "AddressID";
    public static final String DEATH_CERTIFICATE_CERTIFIER_TYPE = "CertifierType";
    
    public static final String DEATH_EVENT_NAME = "DeathEvent";
    public static final String DEATH_EVENT_ID = "DeathEventID";
    public static final String DEATH_EVENT_MANNER_OF_DEATH = "MannerOfDeath";
    public static final String DEATH_EVENT_LOCATION_OF_DEATH = "LocationOfDeath";
    
    public static final String RACE_NAME = "Race";
    public static final String RACE_ID = "RaceID";
    public static final String RACE_RACE = "Race";
    
    public static final String ETHNICITY_NAME = "Ethnicity";
    public static final String ETHNICITY_ID = "EthnicityID";
    public static final String ETHNICITY_ETHNICITY = "Ethnicity";
    
    public static final String METHOD_OF_DISPOSITION_NAME = "MethodOfDisposition";
    public static final String METHOD_OF_DISPOSITION_ID = "MethodOfDispositionID";
    public static final String METHOD_OF_DISPOSITION_METHOD_OF_DISPOSITION = "MethodOfDisposition";
    
    public static final String DEATH_REGISTRATION_NAME = "DeathRegistration";
    public static final String DEATH_REGISTRATION_ID = "DeathRegistrationID";
    public static final String DEATH_REGISTRATION_TIMESTAMP = "Timestamp";
   
    public static final String DECEDENT_DEMOGRAPHICS_NAME = "DecedentDemographics";
    public static final String DECEDENT_DEMOGRAPHICS_ID = "DecedentDemographicsID";
    public static final String DECEDENT_DEMOGRAPHICS_AGE_AT_DEATH = "AgeAtDeath";
    public static final String DECEDENT_DEMOGRAPHICS_BIRTH_CERTIFICATE_ID = "BirthCertificateId";
    public static final String DECEDENT_DEMOGRAPHICS_BIRTH_CERTIFICATE_DATA_YEAR = "BirthCertificateDataYear";
    public static final String DECEDENT_DEMOGRAPHICS_EDUCATION_LEVEL = "EducationLevel";
    public static final String DECEDENT_DEMOGRAPHICS_MARITAL_STATUS = "MaritalStatus";    
    public static final String DECEDENT_DEMOGRAPHICS_OCCUPATION = "Occupation";
    public static final String DECEDENT_DEMOGRAPHICS_SEX = "Sex";
        
    public static final String NAME_NAME = "Name";
    public static final String NAME_ID = "NameID";        
    public static final String NAME_LAST_NAME = "LastName";
    public static final String NAME_FIRST_NAME = "FirstName";
    public static final String NAME_MIDDLE_NAME = "MiddleName";
    public static final String NAME_PREFIX = "Prefix";
    public static final String NAME_SUFFIX = "Suffix";
    
    public static final String PATIENT_DEMOGRAPHICS_NAME = "PatientDemographics";
    public static final String PATIENT_DEMOGRAPHICS_ID = "PatientDemographicsID";
    public static final String PATIENT_DEMOGRAPHICS_NAME_ID = "NameID";
    public static final String PATIENT_DEMOGRAPHICS_ADDRESS_ID = "AddressID";
    public static final String PATIENT_DEMOGRAPHICS_SOCIAL_SECURITY_NUMBER = "SocialSecurityNumber";
    public static final String PATIENT_DEMOGRAPHICS_BIRTH_TIME = "BirthTime";
    public static final String PATIENT_DEMOGRAPHICS_SEX = "Sex";
    public static final String PATIENT_DEMOGRAPHICS_RACE_CODE_1 = "RaceCode1";
    public static final String PATIENT_DEMOGRAPHICS_RACE_CODE_2 = "RaceCode2";
    public static final String PATIENT_DEMOGRAPHICS_ETHNIC_GROUP = "EthnicGroup";
    public static final String PATIENT_DEMOGRAPHICS_DEATH_TIME = "DeathTime";
    public static final String PATIENT_DEMOGRAPHICS_MARITAL_STATUS = "MaritalStatus";
    public static final String PATIENT_DEMOGRAPHICS_BIRTH_PLACE = "BirthPlace";
    
    public static final String VITAL_RECORDS_DEATH_REPORT_NAME = "VitalRecordsDeathReport";
    public static final String VITAL_RECORDS_DEATH_REPORT_DOCUMENT_TYPE = "DocType";
    public static final String VITAL_RECORDS_DEATH_REPORT_VITAL_RECORDS_DEATH_REPORT_ID = "VitalRecordsDeathReportID";
    public static final String VITAL_RECORDS_DEATH_REPORT_AUTOPSY_DETAILS_ID = "AutopsyDetailsID";
    public static final String VITAL_RECORDS_DEATH_REPORT_CAUSE_OF_DEATH_ID = "CauseOfDeathID";
    public static final String VITAL_RECORDS_DEATH_REPORT_CORONER_REFERRAL_ID = "CoronerReferralID";
    public static final String VITAL_RECORDS_DEATH_REPORT_DEATH_ADMINISTRATION = "DeathAdministrationID";
    public static final String VITAL_RECORDS_DEATH_REPORT_DEATH_CERTIFICATE = "DeathCertificateID";
    public static final String VITAL_RECORDS_DEATH_REPORT_DEATH_EVENT_ID= "DeathEventID";
    
    public static final String EXISTENCE_CHECK_NAME = "ExistenceCheck";
    public static final String EXISTENCE_CHECK_EXISTENCE_CHECK_ID = "ExistenceCheckID";
    public static final String EXISTENCE_CHECK_XPATH = "XPath";
    public static final String EXISTENCE_CHECK_MESSAGE = "Message";
                        
    private static Connection con;
    private static Statement stmt;
    private boolean successfulConnection = false;
    
    /*
    public DatabaseConnection(Configuration config) throws SQLException {
        this.setHostname("localhost");
        this.setConfig(config);
        this.initialize();
    }
     */
    /** Creates a new instance of JdbcConnection */
    public DatabaseConnection() throws SQLException {
        //this.setConfig(config);
        this.initialize();
    }
    private void initialize() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = null; 
            
            url = "jdbc:mysql://localhost/VRDRTestData?user=vrdr&serverTimezone=UTC";
            
            System.out.println("Connecting to mysql on url " + url);
            try {
          //      if(this.getConfig().getLogDatabaseUsername() != null) {
          //          con = DriverManager.getConnection(url, this.getConfig().getLogDatabaseUsername(), this.getConfig().getLogDatabasePassword());
          //      } else {
                    con = DriverManager.getConnection(url);                    
          //      }
            } catch (Exception e) {
                e.printStackTrace();
            }
            stmt = con.createStatement();
            setSuccessfulConnection(true);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
            setSuccessfulConnection(false);
        }
        
     //   GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON test.* TO 'dummy'@'localhost';
    
        
        
    }
    public void close() throws SQLException {
        con.close();
    }
        
    public ResultSet executeQuery(String sql) throws SQLException {
        ResultSet result = null;
        result = stmt.executeQuery(sql);
        return result;
    }
    
    public int executeUpdate(String sql) throws SQLException {
        int i = 0;
        i = stmt.executeUpdate(sql);
        return i;
    }
 public boolean isAlive() throws SQLException {
        return !con.isClosed();
    }
    
    public static String makeSafe(String input) {
        if(input == null)
            return "";
        String output = input.replaceAll("'", "\"");
        return output;
    }

    /**
     * @return the successfulConnection
     */
    public boolean isSuccessfulConnection() {
        return successfulConnection;
    }

    /**
     * @param successfulConnection the successfulConnection to set
     */
    public void setSuccessfulConnection(boolean successfulConnection) {
        this.successfulConnection = successfulConnection;
    }
    
    
    public static final void main (String[] args) {
        
        try {
            DatabaseConnection dbCon = new DatabaseConnection();
            dbCon.executeQuery("SELECT * FROM WORLD");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
        
    }

}
