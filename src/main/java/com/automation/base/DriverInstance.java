package com.automation.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.automation.utils.PropertiesFileUtils;
import io.github.bonigarcia.wdm.WebDriverManager;
public class DriverInstance {
		protected WebDriver driver;
		String URL = PropertiesFileUtils.getProperty("URL");
		@BeforeClass
		public void initDriverInstance() {
	            // khởi tạo chrome driver 
	            //và set max size cho cửa sổ trình duyệt
	    	System.out.println("ini: open browser");
	    	WebDriverManager.chromedriver().setup();
	    	driver = new ChromeDriver();
	    	driver.manage().window().maximize();
	    	driver.get(URL);

		}

		@AfterClass
		public void closeDriverInstance() {
			//đóng trình duyệt
			System.out.println("finish: close browser");
			driver.close();
		}
}

