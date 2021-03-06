/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import gov.nist.healthcare.hl7.HL7Utils;
import hl7OrgV3.CE;
import hl7OrgV3.ClinicalDocumentDocument1;
import hl7OrgV3.II;
import hl7OrgV3.POCDMT000040AssignedAuthor;
import hl7OrgV3.POCDMT000040AssignedCustodian;
import hl7OrgV3.POCDMT000040Author;
import hl7OrgV3.POCDMT000040ClinicalDocument1;
import hl7OrgV3.POCDMT000040Component2;
import hl7OrgV3.POCDMT000040Component3;
import hl7OrgV3.POCDMT000040Custodian;
import hl7OrgV3.POCDMT000040CustodianOrganization;
import hl7OrgV3.POCDMT000040InfrastructureRootTypeId;
import hl7OrgV3.POCDMT000040RecordTarget;
import hl7OrgV3.POCDMT000040Section;
import hl7OrgV3.POCDMT000040StructuredBody;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.xmlbeans.XmlOptions;

/**
 *
 * @author mccaffrey
 */
public class VitalRecordsDeathReport {

    public enum DocumentType {
        CODED_CAUSE_OF_DEATH,
        CODED_RACE_AND_ETHNICITY,
        JURISDICTIONAL_DEATH_INFORMATION,
        PROVIDER_DEATH_REGISTRATION,
        VOID_DEATH_CERTIFICATE
    }

    private DocumentType documentType = null;
    private PatientDemographics patientDemographics = null;
    private AutopsyDetails autopsyDetails = null;
    private CauseOfDeath causeOfDeath = null;
    private CoronerReferral coronerReferral = null;
    private DeathAdministration deathAdministration = null;
    private DeathCertification deathCertificate = null;
    private DeathEvent deathEvent = null;
    private DeathRegistration deathRegistration = null;
    private MethodOfDisposition methodOfDisposition = null;
    private DecedentDemographics decedentDemographics = null;
    
    
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

    public static DocumentType convertDocumentType(int docType) {

        switch (docType) {
            case 1:
                return DocumentType.CODED_CAUSE_OF_DEATH;
            case 2:
                return DocumentType.CODED_RACE_AND_ETHNICITY;
            case 3:
                return DocumentType.JURISDICTIONAL_DEATH_INFORMATION;
            case 4:
                return DocumentType.PROVIDER_DEATH_REGISTRATION;
            case 5:
                return DocumentType.VOID_DEATH_CERTIFICATE;
            default:
                return DocumentType.CODED_CAUSE_OF_DEATH;
        }

    }

    public static VitalRecordsDeathReport getVRDRById(String id) throws SQLException {
        VitalRecordsDeathReport vrdr = new VitalRecordsDeathReport();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.VITAL_RECORDS_DEATH_REPORT_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.VITAL_RECORDS_DEATH_REPORT_VITAL_RECORDS_DEATH_REPORT_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {
            int documentType = result.getInt(DatabaseConnection.VITAL_RECORDS_DEATH_REPORT_DOCUMENT_TYPE);
            String patientDemographicsID = result.getString(DatabaseConnection.PATIENT_DEMOGRAPHICS_ID);
            String autopsyDetailsID = result.getString(DatabaseConnection.AUTOPSY_DETAILS_ID);
            String causeOfDeathID = result.getString(DatabaseConnection.CAUSE_OF_DEATH_ID);
            String coronerReferralID = result.getString(DatabaseConnection.CORONER_REFERRAL_ID);
            String deathAdministrationID = result.getString(DatabaseConnection.DEATH_ADMINISTRATION_ID);
            String deathCertificateID = result.getString(DatabaseConnection.DEATH_CERTIFICATE_ID);
            String deathEventID = result.getString(DatabaseConnection.DEATH_EVENT_ID);
            String deathRegistrationID = result.getString(DatabaseConnection.DEATH_REGISTRATION_ID);
            String methodOfDispositionID = result.getString(DatabaseConnection.METHOD_OF_DISPOSITION_ID);
            String decedentDemographicsID = result.getString(DatabaseConnection.DECEDENT_DEMOGRAPHICS_ID);
                    
            PatientDemographics pd = PatientDemographics.getPatientDemographicsById(patientDemographicsID);
            AutopsyDetails ad = AutopsyDetails.getAutopsyDetailsById(autopsyDetailsID);
            CauseOfDeath cod = CauseOfDeath.getCodedCauseOfDeathById(causeOfDeathID);
            CoronerReferral cr = CoronerReferral.getCoronerReferralById(coronerReferralID);
            DeathAdministration da = DeathAdministration.getDeathAdministrationById(deathAdministrationID);
            DeathCertification dc = DeathCertification.getDeathCertificateById(deathCertificateID);
            DeathEvent de = DeathEvent.getDeathEventById(deathEventID);
            DeathRegistration dr = DeathRegistration.getDeathRegistrationById(deathRegistrationID);
            MethodOfDisposition mod = MethodOfDisposition.getMethodOfDispositionById(methodOfDispositionID);
            DecedentDemographics dd = DecedentDemographics.getDecedentDemographicsById(decedentDemographicsID);
            
            vrdr.setDocumentType(VitalRecordsDeathReport.convertDocumentType(documentType));
            vrdr.setPatientDemographics(pd);
            vrdr.setAutopsyDetails(ad);
            vrdr.setCauseOfDeath(cod);
            vrdr.setCoronerReferral(cr);
            vrdr.setDeathAdministration(da);
            vrdr.setDeathCertificate(dc);
            vrdr.setDeathEvent(de);
            vrdr.setDeathRegistration(dr);
            vrdr.setMethodOfDisposition(mod);
            vrdr.setDecedentDemographics(dd);
        }

        return vrdr;
    }

    public static ClinicalDocumentDocument1 populateClinicalDocument(ClinicalDocumentDocument1 cda, VitalRecordsDeathReport vrdr) {

        POCDMT000040ClinicalDocument1 clinicalDocument = cda.addNewClinicalDocument();        
        clinicalDocument.addNewRealmCode().setCode("US");
        POCDMT000040InfrastructureRootTypeId typeId = clinicalDocument.addNewTypeId();
        typeId.setRoot("2.16.840.1.113883.1.3");
        typeId.setExtension("POCD_HD000040");
        II templateIDCoD = clinicalDocument.addNewTemplateId();
        templateIDCoD.setRoot("2.16.840.1.113883.10.20.26.1");
        templateIDCoD.setExtension("2016-12-01");
        II templateID = clinicalDocument.addNewTemplateId();
        switch (vrdr.getDocumentType()) {
            case CODED_CAUSE_OF_DEATH:
                templateID.setRoot("2.16.840.1.113883.10.20.26.1.1.3");
                templateID.setExtension("2016-12-01");
                break;
            case CODED_RACE_AND_ETHNICITY:
                templateID.setRoot("2.16.840.1.113883.10.20.26.1.1.4");
                templateID.setExtension("2016-12-01");
                break;
            case JURISDICTIONAL_DEATH_INFORMATION:
                templateID.setRoot("2.16.840.1.113883.10.20.26.1.1.2");
                templateID.setExtension("2016-12-01");
                break;
            case PROVIDER_DEATH_REGISTRATION:
                templateID.setRoot("2.16.840.1.113883.10.20.26.1.1.1");
                templateID.setExtension("2016-12-01");
                break;
            case VOID_DEATH_CERTIFICATE:
                templateID.setRoot("2.16.840.1.113883.10.20.26.1.1.5");
                templateID.setExtension("2016-12-01");
                break;
            default:
                templateID.setRoot("2.16.840.1.113883.10.20.26.1.1.3");
                templateID.setExtension("2016-12-01");

        }
        clinicalDocument.addNewId().setRoot(UUID.randomUUID().toString());

        CE code = clinicalDocument.addNewCode();
        code.setCode("69409-1");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("\"U.S. standard certificate of death -- 2003 revision");

        clinicalDocument.addNewTitle().newCursor().setTextValue("U.S. standard certificate of death -- 2003 revision");
        clinicalDocument.addNewEffectiveTime().setValue(HL7Utils.convertDate(Calendar.getInstance()));

        CE confidentialityCode = clinicalDocument.addNewConfidentialityCode();

        confidentialityCode.setCode("N");
        confidentialityCode.setCodeSystem("2.16.840.1.11.3883.5.25");
        confidentialityCode.setCodeSystemName("Confidentiality");
        confidentialityCode.setDisplayName("Normal");

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
        custodianOrganization.addNewName();

        POCDMT000040Component2 component = clinicalDocument.addNewComponent();
        POCDMT000040StructuredBody structuredBody = component.addNewStructuredBody();

        if (vrdr.getCauseOfDeath() != null) {
            POCDMT000040Component3 causeOfDeathComponent = structuredBody.addNewComponent();
            POCDMT000040Section causeOfDeathSection = causeOfDeathComponent.addNewSection();
            if (vrdr.getDocumentType() == VitalRecordsDeathReport.DocumentType.CODED_CAUSE_OF_DEATH) {
                CauseOfDeath.populateCodedCauseOfDeathSection(causeOfDeathSection, vrdr.getCauseOfDeath());
            } else {
                CauseOfDeath.populateCauseOfDeathSection(causeOfDeathSection, vrdr.getCauseOfDeath());
            }
        }

        if (vrdr.getDeathAdministration() != null && vrdr.getAutopsyDetails() != null && vrdr.getDeathCertificate() != null && vrdr.getCoronerReferral() != null) {
            POCDMT000040Component3 deathAdministrationComponent = structuredBody.addNewComponent();
            POCDMT000040Section deathAdministrationSection = deathAdministrationComponent.addNewSection();
            DeathAdministration.populateDeathAdministrationSection(deathAdministrationSection, vrdr.getDeathAdministration(), vrdr.getAutopsyDetails(), vrdr.getDeathCertificate(), vrdr.getCoronerReferral(), vrdr.getDeathRegistration(), vrdr.getMethodOfDisposition());
        }
        if (vrdr.getDeathEvent() != null) {
            POCDMT000040Component3 deathEventComponent = structuredBody.addNewComponent();
            POCDMT000040Section deathEventSection = deathEventComponent.addNewSection();
            DeathEvent.populateDeathEventSection(deathEventSection, vrdr.getDeathEvent());
        }
        if(vrdr.getDecedentDemographics() != null) {
            POCDMT000040Component3 decedentDemographicsComponent = structuredBody.addNewComponent();
            POCDMT000040Section decedentDemographicsSection = decedentDemographicsComponent.addNewSection();
            DecedentDemographics.populateDecedentDemographicsSection(decedentDemographicsSection, vrdr.getDecedentDemographics());           
        }
        /*
        if (vrdr.getDeathRegistration() != null) {
            POCDMT000040Component3 deathRegistrationComponent = structuredBody.addNewComponent();
            POCDMT000040Section deathRegistrationSection = deathRegistrationComponent.addNewSection();
            DeathRegistration.populateDeathRegistrationSection(deathRegistrationSection, vrdr.getDeathRegistration());
        }

        if (vrdr.getMethodOfDisposition() != null) {
            POCDMT000040Component3 methodOfDispositionComponent = structuredBody.addNewComponent();
            POCDMT000040Section methodOfDispositionSection = methodOfDispositionComponent.addNewSection();
            MethodOfDisposition.populateMethodOfDispositionSection(methodOfDispositionSection, vrdr.getMethodOfDisposition());
        }
         */
        return cda;
    }

    public static String generateCodedCauseOfDeathDocument(String databaseID) throws SQLException {
        VitalRecordsDeathReport vrdr = VitalRecordsDeathReport.getVRDRById(databaseID);

        //System.out.println(vrdr.getCauseOfDeath().getCauseOfDeath());
        ClinicalDocumentDocument1 cda = ClinicalDocumentDocument1.Factory.newInstance();
        VitalRecordsDeathReport.populateClinicalDocument(cda, vrdr);

        XmlOptions options = new XmlOptions();
        options.setCharacterEncoding("UTF-8");
        options.setSavePrettyPrint();
        options.setUseDefaultNamespace();
        Map suggestedPrefixes = new HashMap();
        
        options.setSaveSuggestedPrefixes(suggestedPrefixes);
        
        //System.out.println(vrdr.getCauseOfDeath().getCauseOfDeath());
        //System.out.println(cda.xmlText(options).replace("type=\"urn:", "type=\""));
        return cda.xmlText(options).replace("type=\"urn:", "type=\"");
    }

    public static void main(String[] args) throws SQLException {

        VitalRecordsDeathReport vrdr = VitalRecordsDeathReport.getVRDRById("VRDR3");

//        System.out.println(vrdr.getCauseOfDeath().getCauseOfDeath());
        ClinicalDocumentDocument1 cda = ClinicalDocumentDocument1.Factory.newInstance();
        VitalRecordsDeathReport.populateClinicalDocument(cda, vrdr);

        XmlOptions options = new XmlOptions();
        options.setCharacterEncoding("UTF-8");
        options.setSavePrettyPrint();
        options.setUseDefaultNamespace();
//        System.out.println(vrdr.getCauseOfDeath().getCauseOfDeath());

        System.out.println(cda.xmlText(options).replace("type=\"urn:", "type=\""));

    }

    /**
     * @return the documentType
     */
    public DocumentType getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    /**
     * @return the deathRegistration
     */
    public DeathRegistration getDeathRegistration() {
        return deathRegistration;
    }

    /**
     * @param deathRegistration the deathRegistration to set
     */
    public void setDeathRegistration(DeathRegistration deathRegistration) {
        this.deathRegistration = deathRegistration;
    }

    /**
     * @return the methodOfDisposition
     */
    public MethodOfDisposition getMethodOfDisposition() {
        return methodOfDisposition;
    }

    /**
     * @param methodOfDisposition the methodOfDisposition to set
     */
    public void setMethodOfDisposition(MethodOfDisposition methodOfDisposition) {
        this.methodOfDisposition = methodOfDisposition;
    }

    /**
     * @return the decedentDemographics
     */
    public DecedentDemographics getDecedentDemographics() {
        return decedentDemographics;
    }

    /**
     * @param decedentDemographics the decedentDemographics to set
     */
    public void setDecedentDemographics(DecedentDemographics decedentDemographics) {
        this.decedentDemographics = decedentDemographics;
    }

}
