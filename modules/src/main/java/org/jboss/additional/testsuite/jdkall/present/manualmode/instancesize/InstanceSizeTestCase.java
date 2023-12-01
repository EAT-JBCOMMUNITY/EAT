package org.jboss.additional.testsuite.jdkall.present.manualmode.instancesize;

import java.io.*;
import java.util.*;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.junit.Test;

@EAT({"modules/testcases/jdkAll/Eap7Plus/manualmode/src/main/java","modules/testcases/jdkAll/WildflyJakarta/manualmode/src/main/java","modules/testcases/jdkAll/EapJakarta/manualmode/src/main/java"})
public class InstanceSizeTestCase {

    /**
     * ./standalone.sh
     * if using continuous restart of the server : watch -n 1 "./jboss-cli.sh -c :reload"
     * if using app redeployment : watch -n 1 touch app.war.dodeploy
     * wget   https://github.com/check-leak/check-leak/releases/download/0.12/check-leak-0.12.jar
     * java -jar check-leak-0.11.jar remote --pid <PID_OF_SERVER> --report /tmp/report --sleep 5000 > out.txt
     * IFILE is the Property that contains the path to the check-leak-012 output : https://github.com/check-leak/check-leak.git
    **/
    @Test
    public void instanceSize() throws Exception {

        if (System.getProperty("IFILE")!=null) {
		File file = new File(System.getProperty("IFILE"));
		HashMap<String,ArrayList<Integer>> log = new HashMap<>();

		BufferedReader br
		    = new BufferedReader(new FileReader(file));
	 
		String st;

		while (((st = br.readLine()) != null)) {
		    if (st.contains(":")) {	
		        String[] s = st.split("[(),\\s]+");  
		        if(s.length>=4) 
				if(log.get(s[4])!=null)
				    log.get(s[4]).add(Integer.valueOf(s[2]));
				else
				    log.put(s[4], new ArrayList(Integer.valueOf(s[2])));
		            
		  //      System.out.println(st + " " + Arrays.toString(s) + " " + s.length);
		    }
		}
		
		for(String s : log.keySet()) {
		    if(log.get(s).size()!=0 && log.get(s).get(0)<log.get(s).get(log.get(s).size()-1)) {
		        System.out.println("+++++" + s + " " + log.get(s).get(0) + " " + log.get(s).get(log.get(s).size()-1));
		    }
		}
	}
    }

}
