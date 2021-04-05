package automation.utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

//This Class takes screenshot for confirmation of the whole operation
//It is saved in another folder Screenshot
public class screenShot {
	
    //Taking the screenshot before maximization of browser window
    public void takeScreenshot(WebDriver driver) throws Exception{
    	//Storing the image file 
    	String filename =  new SimpleDateFormat("yyyyMMddhhmmss'.png'").format(new Date());
        takeSnapShot(driver, System.getProperty("user.dir")+"\\Screenshot\\"+filename) ;     

    }
    
    //Takes the screenshot and send it to given location
    public static void takeSnapShot(WebDriver webdriver,String fileWithPath) throws Exception{

        //Convert web driver object to TakeScreenshot
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);

        //Move image file to new destination
        File DestFile=new File(fileWithPath);

        //Copy file at destination
        FileUtils.copyFile(SrcFile, DestFile);

    }

}
