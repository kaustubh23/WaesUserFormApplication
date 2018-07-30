package mywebapp.app.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import mywebapp.app.data.UserData;
import mywebapp.app.data.UserInfo;
//selenium test case for User Application form: check README for instructions
public class WaesUserFormTest {

	private static final String URL = "http://localhost:8080/com.waes.user/";
	public WebDriver driver=null;
	
	@Test
	public void fillDetails() {
		filldata();

		
	}
	@Test
	public void searchDetailsUser(){
		filldata();
		search();
	}

//Method to search customer by username	
public void search(){
	WebElement search_value = driver.findElement(By.name("searchBox"));
	search_value.clear();
	search_value.click();
	search_value.sendKeys("kaustubh");
	WebElement	search_button=driver.findElement(By.xpath("//button[text()='Filter User' and @type='button']"));
	search_button.click();
}

// Method to enter data into customer form and load the information into the listbox
public void filldata(){
	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");

	driver = new ChromeDriver();
	// launch the Chrome browser and open the application url
	driver.get(URL);

	// maximize the browser window
	driver.manage().window().maximize();
	WebElement username = driver.findElement(By.name("userName"));
	WebElement postTile = driver.findElement(By.name("postTitle"));
	
	WebElement	submit=driver.findElement(By.xpath("//button[text()='Submit' and @type='button']"));
	
	for(UserInfo info: new UserData().getAllFoods()){
		username.clear();
		postTile.clear();
		username.click();
		username.sendKeys(info.getUserName());
	    
		postTile.click();
		
		postTile.sendKeys(info.getPostTitle());
	    
		submit.click();
		
	}
	
}
	@AfterTest
	public void done(){
	//	driver.close();
	}

}
