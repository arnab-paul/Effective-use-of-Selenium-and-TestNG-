package automation.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

//Sets the driver and returns it
public class driverSetup {
	public static WebDriver driver;
	public static String browser;
	public static String baseUrl ;
	public static WebDriver getWebDriver(){
		propertiesFile pf = new propertiesFile();
		pf.getProperties();
		browser = propertiesFile.browserName();
		baseUrl = propertiesFile.baseURL();	
		
		//Invoking the chrome browser
		if(browser.equalsIgnoreCase("chrome")){
			//To set system property
			String path = System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver",path+"\\drivers\\chromedriver.exe");
		
			ChromeOptions options = new ChromeOptions();
			//To disable infobars
			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); 
			//To change Page load strategy to avoid infinite loading
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			driver = new ChromeDriver(options);
			}
		//Invoking the Edge browser
				if(browser.equalsIgnoreCase("edge")){
					//To set system property
					
					String path = System.getProperty("user.dir");
					System.setProperty("webdriver.edge.driver", path+"\\drivers\\msedgedriver.exe");
					
					EdgeOptions Edgeoption = new EdgeOptions();
					//To change Page load strategy to avoid infinite loading
					//Edgeoption.setPageLoadStrategy(PageLoadStrategy.NONE);
					
					//options.setPageLoadStrategy(PageLoadStrategy.NONE);
					
					//To create Edge driver
					driver = new EdgeDriver();
					
					}
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS );
		//Launch the browser and open the url
		driver.get(baseUrl); 
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//Screenshot Object Creation
		screenShot sh = new screenShot();
		
		//Maximize the window
		driver.manage().window().maximize();
		
		//Taking screenshot 
		try {
			sh.takeScreenshot(driver);
			} 
		catch (Exception e) {
				e.printStackTrace();
			}
		
		return driver;
	}
	public static WebDriver reload(){
		driver.get(baseUrl);
		return driver;
	}
}
