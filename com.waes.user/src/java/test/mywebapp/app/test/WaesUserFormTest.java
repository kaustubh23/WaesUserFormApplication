package mywebapp.app.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import mywebapp.app.data.UserData;
import mywebapp.app.data.UserInfo;

public class WaesUserFormTest {

	private static final String URL = "http://localhost:8080/com.waes.user/";
	public WebDriver driver=null;
	
	@Test
	public void fillDetails() {
		filldata();

		
	}
	@Test
	public void searchDetailsUser(){
	//	File file = new File("/zkdemo.app.form/src/main/chromedriver/chromedriver.exe");
		filldata();
		search();
	}

	
public void search(){
	WebElement search_value = driver.findElement(By.name("searchBox"));
	search_value.clear();
	search_value.click();
	search_value.sendKeys("kaustubh");
	WebElement	search_button=driver.findElement(By.xpath("//button[text()='Filter User' and @type='button']"));
	search_button.click();
}
public void filldata(){
	System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\chromedriver.exe");

	driver = new ChromeDriver();
	// launch the Chrome browser and open the application url
	driver.get(URL);

	// maximize the browser window
	driver.manage().window().maximize();
	WebElement username = driver.findElement(By.name("userName"));
	WebElement postTile = driver.findElement(By.name("postTitle"));
	
	//WebElement views = driver.findElement(By.name("views"));
	//WebElement Likes = driver.findElement(By.name("likes"));
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
