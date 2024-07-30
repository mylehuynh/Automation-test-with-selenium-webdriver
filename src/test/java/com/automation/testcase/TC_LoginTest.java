package com.automation.testcase;

import java.io.IOException;
import static org.testng.Assert.assertEquals;
import java.io.FileInputStream;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.automation.base.DriverInstance;
import com.automation.utils.CaptureScreenshot;
import com.automation.pom.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TC_LoginTest extends DriverInstance  {
    @Test(dataProvider = "Excel")
    public void TC01_LoginFirstAccount(String email, String password) throws InterruptedException {
        //Lấy URL từ properties file và tải trang.
   
        WebDriverWait wait = new WebDriverWait(driver, 30);

        //Lấy định danh iconSignin từ properties file và tìm kiếm, click
        WebElement iconSignIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@href='/login']")));
        iconSignIn.click();
        LoginPage loginpage = new LoginPage(driver);
        PageFactory.initElements(driver, loginpage);
        
        //Thực hiện đăng nhập qua loginPage.
        loginpage.enterEmail(email);
        loginpage.enterPassword(password);
        loginpage.clickSignIn();

        //Lấy định danh iconSignout từ properties,
        //  kiểm tra SignOut có hiển thị ko, nếu hiển thị thì click
        WebElement iconSignOut = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@href='/logout']")));
        assertEquals(true, iconSignOut.isDisplayed(), "Can't Sign out");
        iconSignOut.click();
        Thread.sleep(2000);
    }
    

   @DataProvider(name="Excel")
    public Object[][] testDataGenerator() throws IOException {
      //đọc dữ liệu đầu vào từ file excel		 
	   FileInputStream file = new FileInputStream("./data/assignment2_data_test.xlsx");
	   XSSFWorkbook workbook = new XSSFWorkbook(file);
	   XSSFSheet loginSheet = workbook.getSheet("Login");
	   int numberOfRowData = loginSheet.getPhysicalNumberOfRows();
	   
	   Object[][] data = new Object[numberOfRowData][2];
	   
	   for (int i = 0; i < numberOfRowData; i++) {
		   XSSFRow row = loginSheet.getRow(i);
		   XSSFCell username = row.getCell(0);
		   XSSFCell password = row.getCell(1);
		   data[i][0] = username.getStringCellValue();
		   data[i][1] = password.getStringCellValue();
	   }
	   return data;
   	  	
    }   


    @AfterMethod
    public void takeScreenshot(ITestResult result) throws InterruptedException {
        //ITestResult để lấy trạng thái và tên và tham số của từng Test Case
        // phương thức này sẽ được gọi mỗi khi @Test thực thi xong,
        // ta có thể kiểm tra kết quả testcase tại đây.
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
               //1. Lấy tham số (parameters) đầu vào của TC vừa chạy
               //email:0, password:1
               String email = (String)result.getParameters()[0];
                   
         //2. Lấy ra phần tên trong email để làm tên của screenshot
         // Tìm vị trí(int index) của ký tự ‘@’ và lấy ra chuỗi con
         // đứng trước @  qua thư viện của String là: indexOf() và subString()

              int index = email.indexOf("@");
              String accountName = email.substring(0,index);
              CaptureScreenshot.takeScreenshot(driver, accountName);
            } catch (Exception e) {
                System.out.println("Lỗi xảy ra screenshot " + e.getMessage());
            }
        }  
    }
}
