package solutioningweb.automate;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class SolutioningWeb
{
    public static void main( String[] args ) throws Exception{
    	
    	Options options = new Options();
    	options.addOption("r", true, "service request number");
    	options.addOption("w", true, "current workspace");
    	options.addOption("e", true, "path to excel file");
    	@SuppressWarnings("unused")
		String dataObject = null;
    	String workspace = null;
		String excel = null;
		
    	CommandLineParser parser = new DefaultParser();
    	try {
			CommandLine cmd = parser.parse(options, args);
			
			if(cmd.hasOption("r")) {
				dataObject = cmd.getOptionValue("r");
			}
			
			if(cmd.hasOption("w")) workspace = cmd.getOptionValue("w");
			if(cmd.hasOption("e")) excel = cmd.getOptionValue("e");
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
    	//String businessObject = "Manage Data Role and Security Profiles";
    	
    	SeleniumDriver sw = new SeleniumDriver();
    	System.out.println("Initializing drivers...");
    	sw.initializeDriver("http://selenium-hub:4444/wd/hub", "firefox", workspace, excel);
    	//sel.initializeDriver("http://10.251.120.49:4444/wd/hub", "firefox", workspace, excel);
		System.out.println("Running Test in FireFox");
		
    	//sel.initializeDriver("http://192.168.1.11:4444/wd/hub", "firefox", workspace, excel);
    	System.out.println("Drivers Initialized.");
    	sw.pageload();
    	sw.login();
    	sw.projectdetails();
    	Thread.sleep(10000);    	  	
    	try{  	
	        	sw.dispose();
	        	System.out.println("--------------------------------");
	        	System.out.println("|Testing successfully executed.|");
	        	System.out.println("--------------------------------");
	    	} catch(Exception e){
	    		e.printStackTrace();
	    		sw.takeScreenShot("Error");
	    		sw.dispose();
	    		System.out.println("A problem has been encountered while testing.");
	    	}
    }
}