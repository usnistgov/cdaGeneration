/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.hl7;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author mccaffrey
 */
public class HL7Utils {
    
    public static String convertDate(Calendar cal) {
        Date date =  cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyMMddhhmmss");
        return sdf.format(date);
        
    }
        
    
    public static final void main(String args[]) {
        
        System.out.println(HL7Utils.convertDate(Calendar.getInstance()));
        
    }
    
}
