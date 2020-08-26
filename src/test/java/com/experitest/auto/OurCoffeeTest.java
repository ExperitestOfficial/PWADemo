package com.experitest.auto;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

import com.experitest.appium.SeeTestClient;


public class OurCoffeeTest extends BaseTest {
//	protected AndroidDriver<AndroidElement> driver = null;
	protected final String DEFAULT_QUERY = "desktop:chrome:85.0.4183.48:any";
		//"android:@os='android'";

	@BeforeMethod
	@Parameters({ "devicequery" })
	public void setUp1(@Optional(DEFAULT_QUERY) String devicequery) throws Exception {
		init(devicequery);
	}
	
	@Test
	public void navigate() throws Exception {
		dc.setCapability("testName", "OurCoffeeNavigate");
		getDriver(null);
		seetest = new SeeTestClient(driver);

		driver.findElement(By.xpath("//*[text()='About Us']")).click();
		driver.findElement(By.xpath("//*[text()='Our Coffee']")).click();
	}

	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	
}
