package com.zest.qa.testcases;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.zest.qa.base.TestBase;
import com.zest.qa.pages.HomePage;

public class CommonTest extends TestBase{

	HomePage HP;
	
	public CommonTest() {
		super();
	}
	
	@BeforeMethod
	public void setup() {
		initialization();
		
		HP = new HomePage();

	}
	
	@Test(priority=1)
	public void searchProductAndPriceInAmazon() throws InterruptedException {
		HP.searchProductGetPriceInAmazon(prop.getProperty("product"));
		Thread.sleep(2000);
	}
	
	@Test(priority=2)
	public void searchProductAndPriceInFlipkart() throws InterruptedException {
		HP.searchProductAndPriceInFlipkart(prop.getProperty("product"));
	}
			
	@Test(priority=3)
	public void comparePrice() {
		HP.comparePrice();
	}
	
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
}
