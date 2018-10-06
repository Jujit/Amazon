package testcases;

import java.io.IOException;

import jxl.read.biff.BiffException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import testscripts.DriverScript;
import testscripts.FunctionLibrary;
import testscripts.AmazonLibrary;

/**
 * @author Jujit
 * 
 */

public class verifyWatch extends DriverScript {			
		// Run test
	      public static String navigateToAmazonPage() throws InterruptedException,
	      IOException, BiffException {

		APPLICATION_LOGS
		.debug("Executing test case : Navigate to Amazon page");

		System.out.println("Executing test case : Navigate to Amazon page");
		
		
		// Navigate to Amazon
		methodReturnResult = AmazonLibrary.navigateToAmazon();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		// Search for titan wrist watch
		methodReturnResult = AmazonLibrary.wristWatch();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}

		// Filter the watch
		methodReturnResult = AmazonLibrary.filterCriteria();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		return "Pass : Successfully Filtered watch and printed the product details in xls sheet";
		}
		
}