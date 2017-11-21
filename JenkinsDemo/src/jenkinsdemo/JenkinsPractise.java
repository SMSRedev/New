package jenkinsdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class JenkinsPractise {

	String username,password,url;
	WebDriver driver;
	
	
	@Test(priority='0')
	public void ExcelSheet() throws Exception{
		
		File file= new File("E:\\Jenkins.xlsx");
		FileInputStream fis= new FileInputStream(file);
		XSSFWorkbook book= new XSSFWorkbook(fis);
		XSSFSheet sheet= book.getSheetAt(1);
		username=sheet.getRow(0).getCell(0).getStringCellValue();
		System.out.println("The Username is "+username);
		password=sheet.getRow(0).getCell(1).getStringCellValue();
		System.out.println("The Password is "+password);
		book.close();
		
		
	}
	@Test(priority='1')
	public void Login() throws Exception{
		
		driver=new FirefoxDriver();
		driver.get("http://www.ultimatix.net");
		driver.manage().window().maximize();
		driver.findElement(By.id("USER")).sendKeys(username);
		driver.findElement(By.id("PASSWORD")).sendKeys(password);
		driver.findElement(By.id("login_button")).click();
		Thread.sleep(50000);
		driver.findElement(By.id("imh_3")).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.id("uam_modal")).click();
		url=driver.getCurrentUrl();
		System.out.println("The current url is "+url);
		Assert.assertTrue(driver.getCurrentUrl().contains(url), "Success");
	}
	
	/*@Test(priority='2')
	public void ReadExcel() throws Exception{
		
		FileInputStream fi= new FileInputStream(file);
		XSSFWorkbook book1=new XSSFWorkbook(fi);
		XSSFSheet sheet1= book1.getSheetAt(1);
		sheet1.getRow(2).getCell(2).setCellValue(url);
		FileOutputStream fout= new FileOutputStream(file);
		book1.write(fout);
		book1.close();
		
	}*/
	@AfterTest()
	public void Logout(){
		
		driver.close();
		driver.quit();
	}
}
