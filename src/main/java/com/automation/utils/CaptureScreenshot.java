package com.automation.utils;

import org.openqa.selenium.WebDriver;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;
public class CaptureScreenshot {
	public static String takeScreenshot(WebDriver driver, String testcaseName) {
		String filePath = null;
		try {
	        //Tạo tên ảnh trùng với email đăng nhập, kiểu ảnh là png
	    	
	    	
	        String imageName = testcaseName + ".png";
	        
	        

	        // Thực hiện chụp ảnh màn hình, lấy ra đối tượng file ảnh source.
	        TakesScreenshot screenshot = (TakesScreenshot) driver;
	        File source = screenshot.getScreenshotAs(OutputType.FILE);

	        //Tạo đối tượng file với tên đã đặt bên trên tại thư mục /ScreenShots,
	        // Sau đó thực viện cope file ảnh nguồn vào file đích đó.
	        File destiny = new File("./ScreenShots/" + imageName);
	        FileHandler.copy(source, destiny);

	        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	        filePath = "./ScreenShots/" + testcaseName + ".png";
	        File desFile = new File(filePath);
	        ImageIO.write(image, "png", desFile);
		    
	        
	    } catch (Exception ex) {
	        System.out.println("Đã xảy ra lỗi khi chụp màn hình!");
	        ex.printStackTrace();
	    } return filePath;
	}
	
	public static void attachScreenshot(String filePath) {
		try {
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			File file = new File(filePath);
			Reporter.log("<br> <a tilte = \"ScreenShots\"href=\" " + file.getAbsolutePath() + "\">" + "<img alt= '" + file.getName() + "'src='" + file + "' height='243' width='418'</a><br>");
			} catch (Exception e) {
				//TODO: handle exception
			}
	}
}

