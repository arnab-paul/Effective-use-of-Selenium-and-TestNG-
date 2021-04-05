package automation.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

//This class creates properties file with browser name and base Url
//Properties file is created and default browser is set to Chrome, can be changed to Firefox from properties file
public class propertiesFile {
	public static String baseUrl;
	public static String SearchBoxLocator;
	public static String ProductName;
	public static String productCountLocator;
	public static String submitLocator;
	public static String titleLocator;
	public static String priceLocator;
	public static String browser;
	
	//Confirms if the properties file exist or not
    public void getProperties() {
    	Properties pr = new Properties();
        File propFile = new File(System.getProperty("user.dir")+"\\properties\\config.properties");
        
        //if the properties file exists with data, it does not re create the properties file
        if (propFile.exists() && propertiesExist(propFile)) {
            InputStream readFile = null;
            try{
            	readFile = new FileInputStream(System.getProperty("user.dir")+"\\properties\\config.properties");
            	pr.load(readFile);
            	browser = pr.getProperty("browser");
            	baseUrl = pr.getProperty("baseUrl");
            	SearchBoxLocator=pr.getProperty("SearchBoxLocator");
            	submitLocator=pr.getProperty("submit");
            	ProductName=pr.getProperty("ProductName");
            	productCountLocator=pr.getProperty("productCountLocator");
            	titleLocator=pr.getProperty("TitleLocator");
            	priceLocator=pr.getProperty("priceLocator");
            	
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
         //if properties file do not exist sends an error
        }else {
            System.out.println("Properties file not found.");
        }
    }
    //Returns browser value in properties file
    public static String browserName(){
    	return browser;
    }
    
  //Returns baseUrl value in properties file
    public static String baseURL(){
    	return baseUrl;
    }
    public String getUrl() throws Exception
    {
    	//confirm();
		return baseUrl;
    }
    public static String getStoragelocator()
    {
    	return SearchBoxLocator;
    }
    public static String getProduct()
    {
    	return ProductName;
    }
    public static String getProductCountLocator()
    {
    	return  productCountLocator;
    }
    public static String getSubmitLocator()
    {
    	return submitLocator;
    }
    public static String getTitleLocator()
    {
    	return titleLocator;
    }
    public static String getPriceLocator()
    {
		return priceLocator;
    }
    //Validate if the values exists in properties file
    public static boolean propertiesExist(File propFile) {
        Properties prop = new Properties();
        InputStream input = null;
        boolean exists = false;

        try {
            input = new FileInputStream(propFile);

            prop.load(input);

            exists = prop.getProperty("baseUrl") != null
                    && prop.getProperty("browser") != null;

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return exists;
    }
}