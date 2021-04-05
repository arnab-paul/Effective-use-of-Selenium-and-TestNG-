package automation.testing;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
//import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class AutomatedTests extends TestScripts {
	public static WebDriver driver;
	
	@BeforeSuite(alwaysRun=true)
	public static void launchBrowser() {
	
		driver=driver();
		
	}
	@Test(groups ="Regression Suite")
	public void TC_01() throws Exception
	{
		tc_01_verifyPageTitle();
	}
	@Test(priority =1,groups ="Regression Suite")
	public void TC_02() throws Exception
	{
		
		tc_02_SearchBoxValidator("Bookshelves");
	}
	@Test(priority =2,groups = {"Regression Suite","Smoke Suite"})
	public void TC_03() throws Exception
	{
		
		tc_03_SearchResult("Bookshelves");  
		
	}
	@Test(priority =3,groups ="Regression Suite")
	public void TC_04() throws Exception
	{
		
		
		tc_04_getProductCount("Bookshelves");
	}
	@Test(priority =4,groups ="Regression Suite")
	public void TC_05() throws Exception
	{
		
		tc_05_primaryCategory();
		
	}
	
	@Test(priority =5,groups ="Regression Suite")
	public void TC_06() throws Exception
	{
		
		tc_06_filterPrice();
	
    }
	
	@Test(priority =6,groups ="Regression Suite")
	public void TC_07() throws Exception
	{
		
		tc_07_storageType();
	
    }
	@Test(priority =7,groups ="Regression Suite")
	public void TC_08() throws Exception
	{
		
		tc_08_outOfStockProducts();
	
    }
	@Test(priority =8,groups ={"Regression Suite","Smoke Suite"})
	public void TC_09() throws Exception
	{
		
		tc_09_sortByRecommended();
	
    }
	@Test(priority =9,groups ="Regression Suite")
	public void TC_10() throws Exception
	{
		
		tc_10_sortByLowToHigh();
	
    }
	@Test(priority =10,groups ={"Regression Suite","Smoke Suite"})
	public void TC_11()
	{
		tc_11_collectionMenuValidation();
	}
	@Test(priority =11,groups ="Regression Suite")
	public void TC_12()
	{
		tc_12_linkCheck();
	}
	@Test(priority =12,groups ="Regression Suite")
	public void TC_13()
	{
		tc_13_giftCardLink();
	}
	@Test(priority =13,groups ={"Regression Suite","Smoke Suite"})
	public void TC_14()
	{
		tc_14_giftCategory();
	}
	@Test(priority =14,groups ="Regression Suite")
	public void TC_15() throws IOException
	{
		tc_15_giftCategoryNext();
	}
	@Test(priority =15,groups ="Regression Suite")
	public void TC_16() throws IOException
	{
		tc_16_giftCategoryAmount();
	}
	@Test(priority =16,groups ="Regression Suite")
	public void TC_17() throws IOException
	{
		tc_17_giftCategoryDate();
	}
	@Test(priority =17,groups ={"Regression Suite","Smoke Suite"})
	public void TC_18() throws IOException
	{
		tc_18_ConfirmGiftCardWithValidData();
	}
	@Test(priority =18,groups ="Regression Suite")
	public void TC_19() throws IOException
	{
		tc_19_SkipMessage();
	}
	@Test(priority =20,groups ="Regression Suite")
	public void TC_20() throws IOException
	{
		tc_20_BlankRecipientName();
	}
	@Test(priority =21,groups ="Regression Suite")
	public void TC_21() throws IOException
	{
		tc_21_BlankRecipientEmail();
	}
	@Test(priority =22,groups ="Regression Suite")
	public void TC_22() throws IOException
	{
		tc_22_BlankYourName();
	}
	@Test(priority =23,groups ="Regression Suite")
	public void TC_23() throws IOException
	{
		tc_23_BlankYourEmail();
	}
	@Test(priority =24,groups ="Regression Suite")
	public void TC_24() throws IOException
	{
		tc_24_BlankYourMobile();
	}
	@Test(priority =25,groups ="Regression Suite")
	public void TC_25() throws IOException
	{
		tc_25_InvalidRecipientEmail();
	}
	@Test(priority =26,groups ="Regression Suite")
	public void TC_26() throws IOException
	{
		tc_26_InvalidYourEmail();
	}
	@Test(priority =27,groups ="Regression Suite")
	public void TC_27() throws IOException
	{
		tc_27_InvalidMobile();
	}
	@Test(priority =28,groups ="Regression Suite")
	public void TC_28() throws IOException
	{
		tc_28_InvalidRecipientName();
	}
	@Test(priority =29,groups ="Regression Suite")
	public void TC_29() throws IOException
	{
		tc_29_InvalidYourName();
	}
	@AfterSuite(alwaysRun=true)
	public void teardown()
	{
		driver.quit();
	}


}
