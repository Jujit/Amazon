package testscripts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;



public class AmazonLibrary extends DriverScript {

	// Stores current window handle
	public static String currentWindowHandle;

	// Store method return result
	public static String methodReturnResult = null;

	// Site name
	public static String testSiteName = "Amazon";

	// User name
	public static String userName = null;


	/*
	 * .............. Name of the WebElements present on the WebPage
	 * .................
	 */
	
	public static String nameAnalogCheckBox = "'Analog' CheckBox";
	public static String nameBandMaterialCheckBox = "'Band Material' CheckBox";
	public static String nameSeeMoreWatch= "'See More' link";
	public static String nameSearchBox = "'Seach' Box";
	public static String nameTitanCheckBox = "'Titan' CheckBox";
	public static String nameSearchButton = "'Search' Button";
	public static String nameAnalogButton = "'Analog' Button";
	public static String nameTIMERCheckBox = "'TIMER' CheckBox";
	public static String name25Percent = "'25' Percent";

	/* .............. Locators for the test ................. */
	
	public static By LocatorSearchBox = By.xpath("//*[@id='twotabsearchtextbox']");
	public static By LocatorAnalogCheckBox = By.xpath("//*[@id='leftNavContainer']/ul[9]/div/li[1]/span/span/div/label/input");
	public static By LocatorBandMaterialCheckBox = By.xpath("//*[@id='leftNavContainer']/ul[11]/div/li[1]/span/span/div/label/input");
	public static By LocatorTIMERWatch = By.partialLinkText("IIk Collection Watches");
	public static By LocatorTitanCheckBox = By.xpath("//*[@id='leftNavContainer']/ul[6]/div/div/li[123]/span/span/div/label/input");
	public static By LocatorSearchButton = By.xpath("//*[@id='nav-search']/form/div[2]/div/input");
	public static By LocatorAnalogButton = By.xpath("//*[@id='centerPlus']/div[6]/span/a[1]");
	public static By LocatorCheckBox = By.xpath("//input[@name='s-ref-checkbox-IIk Collection Watches']");
	public static By Locator25Percent = By.xpath("//span[contains(text(),'25% Off or more')]");
	public static By Locator1Product = By.xpath("//*[@id='result_0']/div/div[3]/div[1]/a/h2");
	public static By Locator2Product = By.xpath("//*[@id='result_9']/div/div[3]/div[1]/a/h2");
	public static By Locator1Cost = By.xpath("//*[@id='result_0']/div/div[3]/div[2]/a/span[2]");
	public static By Locator2Cost = By.xpath("//*[@id='result_9']/div/div[3]/div[2]/a/span[2]");

	
	// Create a browser instance and navigate to the test site
	public static String navigate() throws MalformedURLException,
	InterruptedException {

		System.out.println("Navigating to the test site - " + testSiteName
				+ " ...");
		APPLICATION_LOGS.debug("Navigating to the test site - " + testSiteName
				+ " ...");

		// Open a driver instance if not opened already

		try {

			if (wbdv == null) {

				if (CONFIG.getProperty("is_remote").equals("true")) {

					// Generate Remote address
					String remote_address = "http://"
							+ CONFIG.getProperty("remote_ip") + ":4444/wd/hub";
					remote_url = new URL(remote_address);

					if (CONFIG.getProperty("test_browser").equals(
							"InternetExplorer"))
						dc = DesiredCapabilities.internetExplorer();

					else if (CONFIG.getProperty("test_browser").equals(
							"Firefox"))
						dc = DesiredCapabilities.firefox();

					else if (CONFIG.getProperty("test_browser")
							.equals("Chrome"))
						dc = DesiredCapabilities.chrome();

					// Initiate Remote Webdriver instance
					wbdv = new RemoteWebDriver(remote_url, dc);

				}

				else {

					if (CONFIG.getProperty("test_browser").equals(
							"InternetExplorer"))
						wbdv = new InternetExplorerDriver();

					else if (CONFIG.getProperty("test_browser").equals(
							"Firefox"))
						wbdv = new FirefoxDriver();

					else if (CONFIG.getProperty("test_browser")
							.equals("Chrome")) {
					//Create prefs map to store all preferences 
					Map<String, Object> prefs = new HashMap<String, Object>();

					//Put this into prefs map to switch off browser notification
					prefs.put("profile.default_content_setting_values.notifications", 2);

					//Create chrome options to set this prefs
					ChromeOptions options = new ChromeOptions();
					options.setExperimentalOption("prefs", prefs);

					//Now initialize chrome driver with chrome options which will switch off this browser notification on the chrome browser
					wbdv = new ChromeDriver(options);

					//Now do your further steps

					}
				}
			}
		}

		catch (Throwable initException) {

			APPLICATION_LOGS.debug("Error came while initiating driver : "
					+ initException.getMessage());
			System.err.println("Error came while initiating driver : "
					+ initException.getMessage());

		}

		// Initiate Event Firing Web Driver instance
		driver = new EventFiringWebDriver(wbdv);

		// Implicitly wait for 30 seconds for browser to open
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Delete all browser cookies
		driver.manage().deleteAllCookies();

		// Navigate to amazon application
		driver.navigate().to(CONFIG.getProperty("test_site_url"));

		// Maximize browser window
		driver.manage().window().maximize();

		
		Thread.sleep(3000);
		
		return "Pass : Navigated to the test site - " + testSiteName;

	}

	// Navigate to amazon
	public static String navigateToAmazon() throws InterruptedException,
	IOException, BiffException {

		// Navigate to amazon
		methodReturnResult = AmazonLibrary.navigate();
		if (methodReturnResult.contains(failTest)) {
			return methodReturnResult;
		}
		
		return "Pass : Navigated to Amazon";

	}
	
	//Type wrist watch and search
	public static String wristWatch() throws InterruptedException,
	IOException, BiffException {
	
		APPLICATION_LOGS.debug("Moving to wrist watch page");
		System.out.println("Moving to wrist watch page");
		
        FunctionLibrary.clickAndWait(LocatorSearchBox, nameSearchBox);
            
		FunctionLibrary.clearAndInput(LocatorSearchBox, nameSearchBox, Keys.chord("Wrist Watch")); 
		
		FunctionLibrary.clickAndWait(LocatorSearchButton, nameSearchButton);
			
			return "Pass : Search Wrist Watch";
	}
	
	// Scroll down to select options
	public static String filterCriteria() throws InterruptedException, FileNotFoundException, IOException {

		APPLICATION_LOGS.debug("Filtering Criteria");
		System.out.println("Filtering Criteria");
		
		FunctionLibrary.clickAndWait(LocatorAnalogButton, nameAnalogButton);
		
		JavascriptExecutor js = (JavascriptExecutor) driver;        		
		 js.executeScript("window.scrollBy(0,900)");
		 FunctionLibrary.clickAndWait(LocatorBandMaterialCheckBox, nameBandMaterialCheckBox);
		 
		 Thread.sleep(5000);
		 
		 js.executeScript("window.scrollBy(0,200)");
		FunctionLibrary.clickAndWait(LocatorCheckBox, "Brand Checkbox");
		
		 Thread.sleep(5000);
		 
		 
		js.executeScript("window.scrollBy(0,600)");
		FunctionLibrary.checkCheckBox1(Locator25Percent, name25Percent);
		
		Thread.sleep(2000);

		WebElement TxtBoxContent1 = driver.findElement(Locator1Product);
		String nameFirstProduct1 = TxtBoxContent1.getText();
		

		WebElement TxtBoxContent2 = driver.findElement(Locator2Product);
		String nameFirstProduct2 = TxtBoxContent2.getText();
		
		WebElement TxtBoxContent3 = driver.findElement(Locator1Cost);
		String nameFirstCost1 = TxtBoxContent3.getText();
		
		WebElement TxtBoxContent4 = driver.findElement(Locator2Cost);
		String nameFirstCost2 = TxtBoxContent4.getText();
		
		System.out.println(nameFirstProduct2+"  "+nameFirstProduct1+"  "+nameFirstCost1+"  "+nameFirstCost2);
		
		
		   XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("ProductInfo");
	         
	        Object[][] bookData = {
	        		{"Product Name", "Price"},
	                {nameFirstProduct1, nameFirstCost1},
	                {nameFirstProduct2, nameFirstCost2},
	                
	        };
	 
	        int rowCount = 0;
	         
	        for (Object[] aBook : bookData) {
	            Row row = sheet.createRow(++rowCount);
	             
	            int columnCount = 0;
	             
	            for (Object field : aBook) {
	                Cell cell = row.createCell(++columnCount);
	                if (field instanceof String) {
	                    cell.setCellValue((String) field);
	                } else if (field instanceof Integer) {
	                    cell.setCellValue((Integer) field);
	                }
	            }
	             
	        }
	         
	        try (FileOutputStream outputStream = new FileOutputStream("ProductInfo.xlsx")) {
	            workbook.write(outputStream);
	        }
		
		
		return "Pass : Filtered the wrist watch successfully";

        }
    }

