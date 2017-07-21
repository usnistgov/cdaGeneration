/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.ClinicalDocumentDocument1;
import hl7OrgV3.POCDMT000040AssignedAuthor;
import hl7OrgV3.POCDMT000040AssignedCustodian;
import hl7OrgV3.POCDMT000040Author;
import hl7OrgV3.POCDMT000040ClinicalDocument1;
import hl7OrgV3.POCDMT000040Custodian;
import hl7OrgV3.POCDMT000040CustodianOrganization;
import hl7OrgV3.POCDMT000040InfrastructureRootTypeId;
import hl7OrgV3.POCDMT000040RecordTarget;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mccaffrey
 */
public class VitalRecordsDeathReport {
    
    private PatientDemographics patientDemographics = null;
    private AutopsyDetails autopsyDetails = null;
    private CauseOfDeath causeOfDeath = null;
    private CoronerReferral coronerReferral = null;
    private DeathAdministration deathAdministration = null;
    private DeathCertification deathCertificate = null;
    private DeathEvent deathEvent = null;

    /**
     * @return the patientDemographics
     */
    public PatientDemographics getPatientDemographics() {
        return patientDemographics;
    }

    /**
     * @param patientDemographics the patientDemographics to set
     */
    public void setPatientDemographics(PatientDemographics patientDemographics) {
        this.patientDemographics = patientDemographics;
    }

    /**
     * @return the autopsyDetails
     */
    public AutopsyDetails getAutopsyDetails() {
        return autopsyDetails;
    }

    /**
     * @param autopsyDetails the autopsyDetails to set
     */
    public void setAutopsyDetails(AutopsyDetails autopsyDetails) {
        this.autopsyDetails = autopsyDetails;
    }

    /**
     * @return the causeOfDeath
     */
    public CauseOfDeath getCauseOfDeath() {
        return causeOfDeath;
    }

    /**
     * @param causeOfDeath the causeOfDeath to set
     */
    public void setCauseOfDeath(CauseOfDeath causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    /**
     * @return the coronerReferral
     */
    public CoronerReferral getCoronerReferral() {
        return coronerReferral;
    }

    /**
     * @param coronerReferral the coronerReferral to set
     */
    public void setCoronerReferral(CoronerReferral coronerReferral) {
        this.coronerReferral = coronerReferral;
    }

    /**
     * @return the deathAdministration
     */
    public DeathAdministration getDeathAdministration() {
        return deathAdministration;
    }

    /**
     * @param deathAdministration the deathAdministration to set
     */
    public void setDeathAdministration(DeathAdministration deathAdministration) {
        this.deathAdministration = deathAdministration;
    }

    /**
     * @return the deathCertificate
     */
    public DeathCertification getDeathCertificate() {
        return deathCertificate;
    }

    /**
     * @param deathCertificate the deathCertificate to set
     */
    public void setDeathCertificate(DeathCertification deathCertificate) {
        this.deathCertificate = deathCertificate;
    }

    /**
     * @return the deathEvent
     */
    public DeathEvent getDeathEvent() {
        return deathEvent;
    }

    /**
     * @param deathEvent the deathEvent to set
     */
    public void setDeathEvent(DeathEvent deathEvent) {
        this.deathEvent = deathEvent;
    }
    
    public static VitalRecordsDeathReport getVRDRById(String id) throws SQLException {
        VitalRecordsDeathReport vrdr = new VitalRecordsDeathReport();
        
        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.VITAL_RECORDS_DEATH_REPORT_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.VITAL_RECORDS_DEATH_REPORT_VITAL_RECORDS_DEATH_REPORT_ID + " = '" + id + "';");
        
        ResultSet result = db.executeQuery(sql.toString());
     
        if(result.next()) {
            String patientDemographicsID = result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_ID);
            String autopsyDetailsID = result.getString(DatabaseConnection.AUTOPSY_DETAILS_ID);
            String causeOfDeathID = result.getString(DatabaseConnection.CAUSE_OF_DEATH_ID);
            String coronerReferralID = result.getString(DatabaseConnection.CORONER_REFERRAL_ID);
            String deathAdministrationID = result.getString(DatabaseConnection.DEATH_ADMINISTRATION_ID);
            String deathCertificateID = result.getString(DatabaseConnection.DEATH_CERTIFICATE_ID);
            String deathEventID = result.getString(DatabaseConnection.DEATH_EVENT_ID);        
            
            PatientDemographics pd = PatientDemographics.getPatientDemographicsById(patientDemographicsID);
            AutopsyDetails ad = AutopsyDetails.getAutopsyDetailsById(autopsyDetailsID);
            CauseOfDeath cod = CauseOfDeath.getCauseOfDeathById(causeOfDeathID);
            CoronerReferral cr = CoronerReferral.getCoronerReferralById(coronerReferralID);
            DeathAdministration da = DeathAdministration.getDeathAdministrationById(deathAdministrationID);
            DeathCertification dc = DeathCertification.getDeathCertificateById(deathCertificateID);
            DeathEvent de = DeathEvent.getDeathEventById(deathEventID);
            
            vrdr.setPatientDemographics(pd);
            vrdr.setAutopsyDetails(ad);
            vrdr.setCauseOfDeath(cod);
            vrdr.setCoronerReferral(cr);
            vrdr.setDeathAdministration(da);
            vrdr.setDeathCertificate(dc);
            vrdr.setDeathEvent(de);            
        }
        
        
        return vrdr;
    }
    
    public static ClinicalDocumentDocument1 populateClinicalDocument(ClinicalDocumentDocument1 cda, VitalRecordsDeathReport vrdr) {
        
                        
        POCDMT000040ClinicalDocument1 clinicalDocument = cda.addNewClinicalDocument();
        POCDMT000040InfrastructureRootTypeId typeId = clinicalDocument.addNewTypeId();
        typeId.setRoot("2.16.840.1.113883.1.3");
        typeId.setExtension("POCD_HD000040");
        clinicalDocument.addNewId().setRoot("PLACEHOLDER"); // TODO
        clinicalDocument.addNewCode().setCode("PLACEHOLDER"); // TODO
        clinicalDocument.addNewEffectiveTime().setValue("PLACEHOLDER"); // TODO
        clinicalDocument.addNewConfidentialityCode().setCode("PLACEHOLDER"); // TODO
        POCDMT000040RecordTarget recordTarget = clinicalDocument.addNewRecordTarget();
        PatientDemographics.populateRecordTarget(recordTarget, vrdr.getPatientDemographics());
        
        //placeholder TODO
        POCDMT000040Author author = clinicalDocument.addNewAuthor();
        author.setTypeCode("AUT");
        author.setContextControlCode("OP");
        author.addNewTime();
        POCDMT000040AssignedAuthor assignedAuthor = author.addNewAssignedAuthor();
        assignedAuthor.setClassCode("ASSIGNED");
        assignedAuthor.addNewId();
        POCDMT000040Custodian custodian = clinicalDocument.addNewCustodian();
        custodian.setTypeCode("CST");
        POCDMT000040AssignedCustodian assignedCustodian = custodian.addNewAssignedCustodian();
        assignedCustodian.setClassCode("ASSIGNED");
        POCDMT000040CustodianOrganization custodianOrganization = assignedCustodian.addNewRepresentedCustodianOrganization();
        custodianOrganization.setClassCode("ORG");
        custodianOrganization.setDeterminerCode("INSTANCE");
        custodianOrganization.addNewId();
        
      
        
    }
    
    
    public static void main (String[] args) throws SQLException {
        
        VitalRecordsDeathReport vrdr = VitalRecordsDeathReport.getVRDRById("VRDR1");
        
        System.out.println(vrdr.getCauseOfDeath().getCauseOfDeath());
        
    }
    
}
