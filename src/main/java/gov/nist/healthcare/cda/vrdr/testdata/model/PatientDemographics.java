package gov.nist.healthcare.cda.vrdr.testdata.model;

import gov.nist.healthcare.cda.model.Address;
import hl7OrgV3.AD;
import hl7OrgV3.AdxpState;
import hl7OrgV3.AdxpStreetAddressLine;
import hl7OrgV3.ClinicalDocumentDocument1;
import hl7OrgV3.II;
import hl7OrgV3.INT;
import hl7OrgV3.PN;
import hl7OrgV3.POCDMT000040ClinicalDocument1;
import hl7OrgV3.POCDMT000040Patient;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Node;

/**
 *
 * @author mccaffrey
 */
public class PatientDemographics {
    
    public static AD populateAD(AD ad, Address addr) {
                
        Collection<String> streetAddresses = addr.getStreetAddress();
        Iterator<String> it = streetAddresses.iterator();
        while(it.hasNext()) {
            AdxpStreetAddressLine line = ad.addNewStreetAddressLine();
            line.newCursor().setTextValue(it.next());            
        }
        if(addr.getState() != null) {
            AdxpState state = ad.addNewState();
            state.newCursor().setTextValue(addr.getState());
        }
        return ad;
    }
    
    
    public static void main(String[] args) throws XmlException {
        
        ClinicalDocumentDocument1 cdaRoot = ClinicalDocumentDocument1.Factory.newInstance();        
        POCDMT000040ClinicalDocument1 cda = POCDMT000040ClinicalDocument1.Factory.newInstance();
       
        II id = II.Factory.newInstance();
        id.setRoot(UUID.randomUUID().toString());
        cda.setId(id);
        cda.addNewId().setRoot(UUID.randomUUID().toString());
        cda.addNewRealmCode().setCode("US");
        cda.addNewLanguageCode();
        POCDMT000040Patient patient = cda.addNewRecordTarget().addNewPatientRole().addNewPatient();
        PN name = patient.addNewName();
        XmlObject o = XmlObject.Factory.newInstance();
        Node node = o.newDomNode();        
        name.addNewFamily().newCursor().setTextValue("Smith");
        name.addNewGiven().newCursor().setTextValue("Betty");
          XmlOptions options = new XmlOptions();
               options.setCharacterEncoding("UTF-8");
               options.setSavePrettyPrint();
        
               
        //       cda.getRecordTargetArray(0).getPatientRole().
               
        INT i = INT.Factory.newInstance();
        i.setValue(BigInteger.ONE);
        cda.setVersionNumber(i);
        cdaRoot.setClinicalDocument(cda);
        //System.out.println(cdaRoot.xmlText(options));
        
           Address addr = new Address();
        addr.getStreetAddress().add("123 Main St.");
        addr.setState("MD");
        addr.setCounty("Montgomery");
        addr.setPostalCode("2000");
        addr.setCountry("US");
        
        
        
        AD ad = cda.getRecordTargetArray(0).addNewPatientRole().addNewAddr();
        ad.setStateArray();
        System.out.println(cdaRoot.xmlText(options));
    }
    
}
