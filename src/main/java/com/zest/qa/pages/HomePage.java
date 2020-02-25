package com.zest.qa.pages;
import java.net.SocketOption;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zest.qa.base.TestBase;

public class HomePage extends TestBase{
	 
	public static String AmazonPrice;
	public static String FlipkartPrice;
    public static int FinalAmazonPrice,FinalFlipkartPrice;
	
	//Page factory - OR(Object Repository) of Amazon
	@FindBy(name="field-keywords")
	WebElement searchProductInAmazon;

    @FindBy(xpath="//input[@type='submit']")
    WebElement clickSearchBtInAmazon;
	    
    @FindBy(xpath="//*[@id=\"search\"]/div[1]/div[2]/div/span[4]/div[1]/div[1]/div/span/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/h2/a/span")
    WebElement selectProductInAmazon;
	    
    @FindBy(xpath="//*[@id=\"priceblock_ourprice\"]")
    WebElement getPriceInAmazon;
	
    
    //Page factory - OR(Object Repository) of Flipkart
    @FindBy(xpath="/html/body/div[2]/div/div/button")
	WebElement cancelLoginInFlipkart;
	
	@FindBy(className="LM6RPg")
	WebElement searchProductInFlipkart;
	  
	@FindBy(className="vh79eN")
	WebElement clickSearchBtnInFlipkart;
	  
	@FindBy(className="_3wU53n")
	WebElement selectProductInFlipkart;
	  
	@FindBy(xpath="//*[@id=\"container\"]/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div[3]/div[1]/div/div[1]")
	WebElement getPriceInFlipkart;
    
    
	 public HomePage() 
	 {
	    	PageFactory.initElements(driver, this);
	 }
	 
	 
	 
	 public void searchProductGetPriceInAmazon(String sp) throws InterruptedException
	 {
	   driver.get(prop.getProperty("urlAmazon"));	 
	   searchProductInAmazon.sendKeys(sp);
	   clickSearchBtInAmazon.click();
	   selectProductInAmazon.click();
	    
	   ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
	   System.out.println(newTab.size());
	   Thread.sleep(5000);
	   
	   //Switch from one window to another
	   driver.switchTo().window(newTab.get(1));
       driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
    
       AmazonPrice=getPriceInAmazon.getText();

       //Using StringBuilder to eliminate the special characters from received price 
       String str = AmazonPrice;
       str= str.substring(0, str.indexOf("."));
       int QuePos = str.indexOf("?");
      
       String res1 = removeByIndex(str, QuePos + 1);
    
       res1=res1.replaceAll("[?.,]","");
       res1=res1.replaceAll("\\s", "");
 		   
    	//Converting retrieved string value to int    
       FinalAmazonPrice=Integer.parseInt(res1);
	   System.out.println("Amazon Price for iPhone XR (64GB)-Yellow:" + FinalAmazonPrice);	
	 }
	 
	 public void searchProductAndPriceInFlipkart(String sp) throws InterruptedException
	    {
		 driver.get(prop.getProperty("urlFlipkart"));
		 cancelLoginInFlipkart.click();
		 searchProductInFlipkart.sendKeys(sp);
		 clickSearchBtnInFlipkart.click(); 
		 selectProductInFlipkart.click();
	      
	     ArrayList<String> childTab = new ArrayList<String>(driver.getWindowHandles());
		 driver.switchTo().window(childTab.get(1));
		 Thread.sleep(5000);
		 WebDriverWait wait = new WebDriverWait(driver, 500);
	 	 
		  FlipkartPrice=getPriceInFlipkart.getText();
		  
		  //Using StringBuilder to eliminate the special characters from received price 
	      String str = FlipkartPrice;       
	      int QuePos = str.indexOf("?");


	      String res1 = removeByIndex(str, QuePos + 1);
	      res1=res1.replaceAll("[?.,]","");
	      FinalFlipkartPrice=Integer.parseInt(res1);
	      System.out.println("Flipkart Price for iPhone XR (64GB)-Yellow:" + FinalFlipkartPrice);
	
	    }
	 
	    //Comparing received prices from Amazon and Flipkart
	    public void comparePrice() {
	      if( FinalAmazonPrice < FinalFlipkartPrice) {
		     System.out.println("Amazon Offers lesser price then flipkart at Rs" + FinalAmazonPrice);
		    }else {
		        	 System.out.println("Flipkart Offers lesser price then Amazon at Rs" + FinalFlipkartPrice);
		           }
	  
	      }
	 
	    //Method to remove special characters using StringBuilder
	    private static String removeByIndex(String str, int index) {
	        return new StringBuilder(str).deleteCharAt(index).toString();
	    }
}
