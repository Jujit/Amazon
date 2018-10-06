package testscripts;

import java.io.IOException;
import java.sql.SQLException;

import jxl.read.biff.BiffException;
import testcases.verifyWatch;

public class Keywords extends DriverScript {

    /*
     * ......................... Verify name on Amazon Page
     * ...............................
     */

    // Verify user details displayed on Amazon page
    public static String navigateToAmazonPage()
	    throws InterruptedException, IOException, BiffException, NoSuchMethodException, SQLException {

	return verifyWatch.navigateToAmazonPage();

    }

}
