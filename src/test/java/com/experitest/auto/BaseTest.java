package com.experitest.auto;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.experitest.appium.SeeTestClient;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseTest {

	protected DesiredCapabilities dc = new DesiredCapabilities();
	protected Properties cloudProperties = new Properties();
	public SeeTestClient seetest;
	protected RemoteWebDriver driver = null;
	protected String deviceQuery = null;


	public void init(String deviceQuery) throws Exception {
		this.deviceQuery = deviceQuery;
		initCloudProperties();
		String adhocDeviceQuery = System.getenv("deviceQuery");
		if (adhocDeviceQuery != null) {
			System.out.println("[INFO] Redirecting test to the current device.");
			deviceQuery = adhocDeviceQuery;
		}
		dc.setCapability("reportDirectory", "reports");
		dc.setCapability("reportFormat", "xml");
		String accessKey = getProperty("accessKey", cloudProperties);
		if (accessKey != null && !accessKey.isEmpty()) {
			dc.setCapability("accessKey", accessKey);
		}
	}

	public RemoteWebDriver getDriver(String query, String testName) throws MalformedURLException {
		if(testName != null){
			dc.setCapability("testName", "OurCoffeeNavigate");
		}
		if(query == null){
			query = deviceQuery;
		}
		if(query.startsWith("desktop:")){ // desktop:chrome:85.0.4183.48:any
			dc.setCapability("deviceQuery", (String)null);
			dc.setCapability(CapabilityType.BROWSER_NAME, query.split(":")[1]);
			dc.setCapability(CapabilityType.VERSION, query.split(":")[2]);
			dc.setCapability(CapabilityType.PLATFORM_NAME, query.split(":")[3]);

			driver = new RemoteWebDriver(new URL(getProperty("url",cloudProperties) + "/wd/hub"), dc);
		} else if(query.startsWith("android:")){
			dc.setCapability("deviceQuery", query.split(":")[1]);
			driver = new AndroidDriver<>(new URL(getProperty("url",cloudProperties) + "/wd/hub"), dc);
		} else if(query.startsWith("ios:")){
			dc.setCapability("deviceQuery", query.split(":")[1]);
			driver = new IOSDriver<>(new URL(getProperty("url",cloudProperties) + "/wd/hub"), dc);
		} else {
			throw new RuntimeException("Unknown query: " + query);
		}
		driver.get("https://app.starbucks.com/");
		return driver;
	}

	protected String getProperty(String property, Properties props) {
		if (System.getProperty(property) != null) {
			return System.getProperty(property);
		} else if (System.getenv().containsKey(property)) {
			return System.getenv(property);
		} else if (props != null) {
			return props.getProperty(property);
		}
		return null;
	}

	private void initCloudProperties() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader("cloud.properties");
		cloudProperties.load(fr);
		fr.close();
	}
}
