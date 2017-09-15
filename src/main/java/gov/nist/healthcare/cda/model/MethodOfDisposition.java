/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model;

import gov.nist.healthcare.cda.model.jdbc.DatabaseConnection;
import hl7OrgV3.CD;
import hl7OrgV3.CE;
import hl7OrgV3.II;
import hl7OrgV3.POCDMT000040Act;
import hl7OrgV3.POCDMT000040Entry;
import hl7OrgV3.POCDMT000040Observation;
import hl7OrgV3.POCDMT000040Procedure;
import hl7OrgV3.POCDMT000040Section;
import hl7OrgV3.ST;
import hl7OrgV3.StrucDocText;
import hl7OrgV3.XActMoodDocumentObservation;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mccaffrey
 */
public class MethodOfDisposition {
    
    private String methodOfDisposition = null;

    
    
     public static MethodOfDisposition getMethodOfDispositionById(String id) throws SQLException {
        MethodOfDisposition mod = new MethodOfDisposition();

        DatabaseConnection db = new DatabaseConnection();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * ");
        sql.append("FROM " + DatabaseConnection.METHOD_OF_DISPOSITION_NAME + " ");
        sql.append("WHERE " + DatabaseConnection.METHOD_OF_DISPOSITION_ID + " = '" + id + "';");

        ResultSet result = db.executeQuery(sql.toString());

        if (result.next()) {
            
            mod.setMethodOfDisposition(result.getString(DatabaseConnection.METHOD_OF_DISPOSITION_METHOD_OF_DISPOSITION));            
        } else {
            return null;
        }
        return mod;
    }
/*
     public static POCDMT000040Section populateMethodOfDispositionSection(POCDMT000040Section section, MethodOfDisposition mod) {
        
        II templateIdSection = section.addNewTemplateId();
        templateIdSection.setRoot("2.16.840.1.113883.10.20.26.1.2.3");
        templateIdSection.setExtension("2016-12-01");

        CE code = section.addNewCode();
        code.setCode("47519-4");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("History of procedures");

        ST title = section.addNewTitle();
        title.newCursor().setTextValue("Death Administration Section");

        StrucDocText text = section.addNewText();
        text.newCursor().setTextValue("He's dead, Jim.");
        
        POCDMT000040Entry methodOfDispositionEntry = section.addNewEntry();
        POCDMT000040Observation methodOfDispositionObservation = methodOfDispositionEntry.addNewObservation();
        MethodOfDisposition.populateMethodOfDisposistionObservation(methodOfDispositionObservation, mod);

        return section;
        
    }
*/
     
    public static POCDMT000040Observation populateMethodOfDisposistionObservation(POCDMT000040Observation observation, MethodOfDisposition mod) {
        
        observation.setClassCode("OBS");
        observation.setMoodCode(XActMoodDocumentObservation.EVN);

        II templateId = observation.addNewTemplateId();
        templateId.setRoot("2.16.840.1.113883.10.20.26.1.3.42");
        templateId.setExtension("2016-12-01");

        CD code = observation.addNewCode();
        code.setCode("80905-3");
        code.setCodeSystem("2.16.840.1.113883.6.1");
        code.setCodeSystemName("LOINC");
        code.setDisplayName("Method of disposition");

        
        CD value = CD.Factory.newInstance();
        value.setCode(mod.getMethodOfDisposition());
        observation.addNewValue();
        observation.setValueArray(0, value);                
        
        return observation;
        
    }
    
    /**
     * @return the methodOfDisposition
     */
    public String getMethodOfDisposition() {
        return methodOfDisposition;
    }

    /**
     * @param methodOfDisposition the methodOfDisposition to set
     */
    public void setMethodOfDisposition(String methodOfDisposition) {
        this.methodOfDisposition = methodOfDisposition;
    }
    
}
