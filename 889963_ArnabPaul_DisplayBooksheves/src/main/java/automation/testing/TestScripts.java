package automation.testing;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.Datadriven.Framework.DriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automation.utilities.driverSetup;
import automation.utilities.isClickable;
import automation.utilities.propertiesFile;
import automation.utilities.readExcelFile;

public class TestScripts {
	public static WebDriver driver;
	public static String baseUrl = propertiesFile.baseURL();
	//creating object of ExcelUtils class
	static readExcelFile ex = new readExcelFile();
	static boolean flag = false;
	//String product = propertiesFile.getProduct();
	
	
	public static WebDriver driver() {
		driver=driverSetup.getWebDriver();
		return driver;
	}
	//Verify that the website is Up and running
	public static void tc_01_verifyPageTitle() {
			
			//To validate Page Title of the site
		driver=driverSetup.reload();
	        String Actual = driver.getTitle();
	        String Expected = "Furniture Online: @Upto 40% Off on Wooden Furniture Online in India at Best Price - Urban Ladder";
	
	        if (Actual.equals(Expected)) {
	                   System.out.println("\nTest case passed!\n");
	        } else {
	                   System.out.println("\nTest case failed!\n");
	        }
	        Assert.assertEquals(Actual, Expected);
 
	    }
	//To validate the Search Functionality
	public void tc_02_SearchBoxValidator(String productName) throws Exception
	{	
		driver=driverSetup.reload();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
		ele.sendKeys(productName);
		driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
	}
	//To validate search results against product name
	public void tc_03_SearchResult(String productName) throws Exception
	{
		driver=driverSetup.reload();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
		ele.sendKeys(productName);
		driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
		WebDriverWait wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.urlToBe("https://www.urbanladder.com/products/search?utf8=%E2%9C%93&keywords=Bookshelves"));
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		String SearchResultitle=driver.findElement(By.xpath(propertiesFile.getTitleLocator())).getText();
		if(SearchResultitle.contains("Bookshelves"))
		{
			System.out.println("\nTest case passed.\nThe Search result title is "+SearchResultitle);
		}
		else
		{
			throw new NoSuchElementException("\nTest failed.\nDidn't landed on Bookshelves Page.");
		}
		Assert.assertTrue(SearchResultitle.contains("Bookshelves"));
		
		
	}
	//To validate product count remains same
	public void tc_04_getProductCount(String productName)
	{
		driver=driverSetup.reload();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
		ele.sendKeys(productName);
		driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String count=driver.findElement(By.xpath(propertiesFile.getProductCountLocator())).getText();
		List<WebElement> productlist=driver.findElements(By.cssSelector("img.product-img-default"));
		if((count!=null)&&(productlist.size()==Integer.parseInt(count)))
		{
			System.out.println("\n"+count+" of results appeared\n");
		}
		else
		{
			System.out.println("\nTest failed:(\nSearch results didn't appeared");
		}
		Assert.assertTrue(count!=null);
	}
	//To validate user can choose product in deserved price range
	 public void tc_06_filterPrice() throws InterruptedException
	   {
		 driver=driverSetup.reload();
		 String product = propertiesFile.getProduct();
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		 WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
			ele.sendKeys(product);
			driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
			
			  driver.findElement(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[2]/div[1]")).click();
			    TimeUnit.SECONDS.sleep(2);
			    WebElement slide = driver.findElement(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[2]/div[2]/div/div/ul/li[1]/div/div[2]/div[2]/div/div[2]/div"));
			    int width = slide.getSize().width;
			    Actions act = new Actions(driver);//241,422
			    act.dragAndDropBy(slide, (int)(-width*(12.15)), 0).release().build().perform();
			    Assert.assertTrue(width>0);
	   }
	   //To validate user is able to select primary category as "bookshelves"
	    public void tc_05_primaryCategory() 
	    {   
	    	driver=driverSetup.reload();
	    	String product = propertiesFile.getProduct();
	    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    	WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
			ele.sendKeys(product);
			driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		    driver.findElement(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[2]/div[1]")).click();
	    	//To select primary category as "bookshelves"
	    	new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[1]/div[1]"))).click();
	    	driver.findElement(By.id("filters_primary_category_Bookshelves")).click();
	    	String msg=driver.findElement(By.id("filters_primary_category_Bookshelves")).getAttribute("value");	 
	    	Assert.assertTrue(msg.equals("Bookshelves"));
	    }	
	   //To validate user is able to select storage type as "open"
	    public void tc_07_storageType() 
	    {
	    	driver=driverSetup.reload();
	    	String product = propertiesFile.getProduct();
	    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    	//To select storage type as "open"
	    	WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
			ele.sendKeys(product);
			driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[3]/div[1]"))).click();
	    	driver.findElement(By.id("filters_storage_type_Open")).click();
	    	String msg=driver.findElement(By.id("filters_storage_type_Open")).getAttribute("value");
	    	Assert.assertEquals(msg,"Open");
	    	
	    }	
	   //To validate user is able to exclude out of stock products
	    public void tc_08_outOfStockProducts()
	    {
	    	driver=driverSetup.reload();
	    	String product = propertiesFile.getProduct();
	    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    	//To exclude out of stock products
	    	WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
			ele.sendKeys(product);
			driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[3]/div[1]"))).click();
	    	driver.findElement(By.id("filters_storage_type_Open")).click();
	    	new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"filters-form\"]/div[1]/div/div/ul/li[3]/div[1]")));
	    	driver.findElement(By.id("filters_availability_In_Stock_Only")).click();
	 
	    	String msg=driver.findElement(By.id("filters_availability_In_Stock_Only")).getAttribute("value");
	    	Assert.assertEquals(msg,"In Stock Only");
	    }
	    //To validate user is getting bookshelves based on site recommendation
	    public void tc_09_sortByRecommended()
	    {
	    	driver=driverSetup.reload();
	    	String product = propertiesFile.getProduct();
	    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    	//To sort bookshelves based on site recommendation
	    	WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
			ele.sendKeys(product);
			driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		   
		  //To sort bookshelves based on site recommendation
	    	new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Recommended')]"))).click();
	    	driver.findElement(By.xpath("//li[contains(text(),'Recommended')]")).click();
	    	String msg=driver.findElement(By.xpath("//li[contains(text(),'Recommended')]")).getText();
	    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    	Assert.assertEquals(msg,"Recommended");
	    }
	    //To validate user is getting bookshelves based on prices from low to high
	    public void tc_10_sortByLowToHigh()
	    {
	    	driver=driverSetup.reload();
	    	String product = propertiesFile.getProduct();
	    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    	WebElement ele=driver.findElement(By.cssSelector(propertiesFile.getStoragelocator()));
			ele.sendKeys(product);
			driver.findElement(By.cssSelector(propertiesFile.getSubmitLocator())).click();
			driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
			//Sorting the products based on prices from low to high
			new WebDriverWait(driver,60).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Recommended')]"))).click();
			driver.findElement(By.xpath("//li[contains(text(),'Price: Low to High')]")).click();
	    	String msg=driver.findElement(By.xpath("//li[contains(text(),'Price: Low to High')]")).getText();
	    	if((msg.equals("Price: Low to High")))
	    	{
	    		System.out.println("\nProducts are sorted from low to high price and the number of products remained same\n");
	    	}
	    	else
	    	{
	    		System.out.println("\nTest case failed\n");
	    	}
	    	Assert.assertEquals(msg,"Price: Low to High");
	    }
	    //To validate user is able to fetch sub-menus under collection
	    public void tc_11_collectionMenuValidation()
	    {
	    	driver=driverSetup.reload();
	    	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    	Actions act=new Actions(driver);
	    	act.moveToElement(driver.findElement(By.xpath("//header/div[2]/div[1]/nav[1]/div[1]/ul[1]/li[10]/span"))).build().perform();
	    	WebDriverWait wait=new WebDriverWait(driver,10);
	    	wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/header[1]/div[2]/div[1]/nav[1]/div[1]/ul[1]/li[10]/div[1]/div[1]/ul[1]/li[1]/ul[1]/li[2]/a[1]/span[1]")));
	    	List<WebElement> collectionproducts=driver.findElements(By.xpath("//header/div[2]/div[1]/nav[1]/div[1]/ul[1]/li[10]/div[1]/div[1]/ul[1]/li[1]/ul[1]/li"));
	    	System.out.println("sub-menu items under Being-At-home:\n");
	    	for(WebElement element:collectionproducts)
	    	{
	    		System.out.println(element.getText());
	    	}
	    }
	    //To validate sub-menu links aren't broken
	    public void tc_12_linkCheck()
	    {
	    	driver=driverSetup.reload();
	    	String url ;
	    	WebDriverWait wait=new WebDriverWait(driver,10);
	    	//wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//body[1]/div[1]/header[1]/div[2]/div[1]/nav[1]/div[1]/ul[1]/li[10]/div[1]/div[1]/ul[1]/li[1]/ul[1]/li[2]/a[1]/span[1]")));
	    	List<WebElement> collectionproducts=driver.findElements(By.xpath("//header/div[2]/div[1]/nav[1]/div[1]/ul[1]/li[10]/div[1]/div[1]/ul[1]/li[1]/ul[1]/li/a"));
	    	for(WebElement ele:collectionproducts)
	    	{
	    		url=ele.getAttribute("href");
	    		if(url==null||url.isEmpty())
	    		{
	    			System.out.println(url+": is either not configured for anchor tag or it is empty");
	    		}	
	    	}
	    	Assert.assertTrue(collectionproducts.size()>=13);
	    }
	   

		//verify that Gift Card link is not broken.
		public void tc_13_giftCardLink() {
			
			driver=driverSetup.reload();
			SoftAssert softAssert = new SoftAssert();
			
			driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
			WebDriverWait wait=new WebDriverWait(driver, 20);
			String expectedTitle = "Gift Card - Buy Gift Cards & Vouchers Online for Wedding, Birthday | Urban Ladder";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/main/section/h1")));
			String actualResult = driver.getTitle();
			softAssert.assertEquals(actualResult,expectedTitle);
			softAssert.assertAll();
		}
	
		//verify that user is able to choose from Gift Category.
		public static void tc_14_giftCategory() {
			
			driver=driverSetup.reload();
			//driver.get(baseUrl);
			SoftAssert softAssert = new SoftAssert();
			WebDriverWait wait = new WebDriverWait(driver, 20);
			//driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS );
			driver.findElement(By.xpath("//*[@id='header']/section/div/ul[2]/li[3]/a")).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
			Actions action = new Actions(driver);
			action.moveToElement(hoverMouse).build().perform();
			//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img")).click();
			String expectedText = "2. Now, customise your gift card";
			
			String actualText = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/h2")).getText();
			softAssert.assertEquals(actualText,expectedText);
			softAssert.assertAll();
		}
		
		//verify that "Next" button is available when correct Gift Card amount(in range 1000-500000) is selected.
			public static void tc_15_giftCategoryNext() throws IOException {
				
				driver=driverSetup.reload();
				
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				WebDriverWait wait = new WebDriverWait(driver, 20);
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
				softAssert.assertTrue(isClickable.confirm(button, driver));
				softAssert.assertAll();
			
			}
			//verify that Next button is not available when incorrect Gift Card amount(in range 1000-500000) is selected.
			public static void tc_16_giftCategoryAmount() throws IOException {
				driver=driverSetup.reload();
				
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				
				WebDriverWait wait = new WebDriverWait(driver, 20);
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","negative_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
				softAssert.assertFalse(isClickable.confirm(button, driver));
				softAssert.assertAll();
			}
			
		
			//verify that user is able to select preferred date.
			public static void tc_17_giftCategoryDate() throws IOException {
				driver=driverSetup.reload();
				
			//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				
				WebDriverWait wait = new WebDriverWait(driver, 20);
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
				softAssert.assertTrue(isClickable.confirm(button, driver));
				softAssert.assertAll();
			}
			
			//Verify that user is able to confirm gift card with all the valid data input.
			public static void tc_18_ConfirmGiftCardWithValidData() throws IOException {
				driver = driverSetup.reload();
				WebDriverWait wait = new WebDriverWait(driver, 10);
				SoftAssert softAssert = new SoftAssert();
				
				
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
				
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
				
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
				
				rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));
				
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[4]/div[2]/button/span[1]")).click();
				
				driver.switchTo().frame(0);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"amount\"]/span[2]")));
				WebElement ele = driver.findElement(By.xpath("//*[@id=\"amount\"]/span[2]"));
				boolean visible =ele.isDisplayed();
				
				softAssert.assertTrue(visible);
				softAssert.assertAll();
			}
			

			//Veriy that user is able to skip the optional field
			public static void tc_19_SkipMessage() throws IOException {
				driverSetup.reload();
				WebDriverWait wait = new WebDriverWait(driver, 10);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
				
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
				
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				
				rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				
				
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[4]/div[2]/button/span[1]")).click();
				
				driver.switchTo().frame(0);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"amount\"]/span[2]")));
				WebElement ele = driver.findElement(By.xpath("//*[@id=\"amount\"]/span[2]"));
				boolean visible =ele.isDisplayed();
				
				softAssert.assertTrue(visible);
				softAssert.assertAll();
			}
			
			//Verify that user gets error message when leaving "Recipient's Name" field blank  and filling the rest fields with valid input.
			public static void tc_20_BlankRecipientName() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 10);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
				
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
				
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
				
				//WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
				
				//rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));
				
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
				
				String mess = driver.findElement(By.id("ip_4036288348")).getAttribute("validationMessage");  
				if(mess.equalsIgnoreCase("Please fill out this field.")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag);
				softAssert.assertAll();
			}
			
			//Verify that user gets error message when leaving "Recipient's Email" field blank and filling the rest fields with valid input.
			public static void tc_21_BlankRecipientEmail() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 10);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				//WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				//rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));
						
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
						
				String mess = driver.findElement(By.id("ip_137656023")).getAttribute("validationMessage");  
				if(mess.equalsIgnoreCase("Please fill out this field.")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag);
				softAssert.assertAll();
			}		
			
			//Verify that user gets error message when leaving "Your Name" field blank  and filling the rest fields with valid input.
			public static void tc_22_BlankYourName() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 5);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				//WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				rName.sendKeys(ex.getCellData(1, 3));
				//yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));
						
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
						
				String mess = driver.findElement(By.id("ip_1082986083")).getAttribute("validationMessage");  
		
				if(mess.equalsIgnoreCase("Please fill out this field.")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag);
				softAssert.assertAll();
			}	
			//Verify that user gets error message when leaving "Your Email" field blank  and filling the rest fields with valid input.
			public static void tc_23_BlankYourEmail() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 5);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				//WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				//yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));
						
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
						
				String mess = driver.findElement(By.id("ip_4081352456")).getAttribute("validationMessage");  
				
				if(mess.equalsIgnoreCase("Please fill out this field.")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag);
				softAssert.assertAll();
			}
			//Verify that user gets error message when leaving "Your Mobile" field blank.
			public static void tc_24_BlankYourMobile() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 5);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				//WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				//yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));
						
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
						
				String mess = driver.findElement(By.id("ip_2121573464")).getAttribute("validationMessage");  
			
				if(mess.equalsIgnoreCase("Please fill out this field.")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag);
				softAssert.assertAll();
			}		
			//Verify that user gets error message for filling invalid "Recipient's Email"
			public static void tc_25_InvalidRecipientEmail() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 10);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				//rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));
				ex.setExcelFile("testData.xls","negative_tc");		
				rEmail.sendKeys(ex.getCellData(1, 4));
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
						
				String mess = driver.findElement(By.id("ip_137656023")).getAttribute("validationMessage"); 
				String txt = ex.getCellData(1, 4);
				if(mess.equalsIgnoreCase("Please include an '.' in the email address.'"+txt+"'" + " is missing a '.'.")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag,"The user didn't get any error message for filling invalid \"Recipient's Email\"");
				softAssert.assertAll();
			}	
		  //Verify that user gets error message for filling invalid "Your Email"
			public static void tc_26_InvalidYourEmail() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 10);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				//yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));
				ex.setExcelFile("testData.xls","negative_tc");		
				yEmail.sendKeys(ex.getCellData(1, 6));
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
						
				String mess = driver.findElement(By.id("ip_4081352456")).getAttribute("validationMessage"); 
				String txt = ex.getCellData(1, 6);
				if(mess.equalsIgnoreCase("Please include an '@' in the email address. '"+txt+"'" + " is missing an '@'.")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag);
				softAssert.assertAll();
			}	
			//Verify that user gets error message for filling invalid mobile number.
			public static void tc_27_InvalidMobile() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 10);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				//yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));  
				ex.setExcelFile("testData.xls","negative_tc");		
				yMobile.sendKeys(ex.getCellData(1, 7));
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[4]/div[2]/button")).click();		
				String mess = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[4]/div[2]/ul/li")).getText(); 
				
				if(mess.equalsIgnoreCase("Customer mobile number is not a valid Indian mobile number")) {
					flag = true;
				}		
				softAssert.assertTrue(flag);
				softAssert.assertAll();
			}
			//Verify that user gets error message for filling invalid Recipient's Name.
			public static void tc_28_InvalidRecipientName() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 10);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				//rName.sendKeys(ex.getCellData(1, 3));
				yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));  
				ex.setExcelFile("testData.xls","negative_tc");		
				rName.sendKeys(ex.getCellData(1, 3));
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();
			
				String mess = driver.findElement(By.id("ip_4036288348")).getAttribute("validationMessage"); 
				
				if(mess.equalsIgnoreCase("Invalid Recipient's Name, Name should not contain numeric or special character")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag,"The user didn't get any error message for filling invalid \"Recipient's Name\"");
				softAssert.assertAll();
			}	
			//Verify that user gets error message for filling invalid Your Name.
			public static void tc_29_InvalidYourName() throws IOException {
				driver = driverSetup.reload();
				flag=false;
				WebDriverWait wait = new WebDriverWait(driver, 10);
				//driver.get(baseUrl);
				SoftAssert softAssert = new SoftAssert();
				//calling the readExcelFile class method to initialise the workbook and sheet
						
				//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.findElement(By.xpath("//*[@id=\"header\"]/section/div/ul[2]/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app-container\"]/div/footer/section/div[2]/section[2]/ul/li[1]/a/img")));
				WebElement hoverMouse = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/img"));
				Actions action = new Actions(driver);
				action.moveToElement(hoverMouse).build().perform();
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[1]/ul/li[3]/div/h3")).click();
				//Identify the WebElements for the Gift
				ex.setExcelFile("testData.xls","positive_tc");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				WebElement amount = driver.findElement(By.xpath("//*[@id=\"ip_2251506436\"]"));
				//WebElement button = driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/button"));
				Select month = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[1]")));
				Select day = new Select(driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[2]/div/section[2]/div[4]/select[2]")));
				amount.sendKeys(ex.getCellData(1, 0)); 
				month.selectByVisibleText(ex.getCellData(1, 1));
				day.selectByVisibleText(ex.getCellData(1, 2));
					
				driver.findElement(By.xpath("//*[@id='app-container']/div/main/section/section[2]/div/section[2]/button")).click();
						
				WebElement rName = driver.findElement(By.id("ip_4036288348"));
				WebElement yName = driver.findElement(By.id("ip_1082986083"));
				WebElement rEmail = driver.findElement(By.id("ip_137656023"));
				WebElement yEmail = driver.findElement(By.id("ip_4081352456"));
				WebElement yMobile = driver.findElement(By.id("ip_2121573464"));
				WebElement message = driver.findElement(By.id("ip_582840596"));
						
				rName.sendKeys(ex.getCellData(1, 3));
				//yName.sendKeys(ex.getCellData(1, 5));
				rEmail.sendKeys(ex.getCellData(1, 4));
				yEmail.sendKeys(ex.getCellData(1, 6));
				yMobile.sendKeys(ex.getCellData(1, 7));
				message.sendKeys(ex.getCellData(1, 8));  
				ex.setExcelFile("testData.xls","negative_tc");		
				yName.sendKeys(ex.getCellData(1, 5));
				driver.findElement(By.xpath("//*[@id=\"app-container\"]/div/main/section/section[3]/form/button")).click();		
				
				String mess = driver.findElement(By.id("ip_1082986083")).getAttribute("validationMessage"); 
				
				if(mess.equalsIgnoreCase("Invalid Recipient's Name, Name should not contain numeric or special character")) {
					flag = true;
				}
						
				softAssert.assertTrue(flag,"The user didn't get any error message for filling invalid \"Your Name\"");
				softAssert.assertAll();
			}					
		public void closeDriver()
		{
			
			driver.close();
		}
}

