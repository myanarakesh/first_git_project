package googleAutomation;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(googleAutomation.TestControl.class)
public class SearchEngine {
	Properties prop = new Properties();
	FileReader reader = null;
	int count = 0;
	WebElement searchInput = null;
	WebElement verifyResult = null;
	WebDriverWait wait = null;
	Actions action = null;
	
	@Test(enabled = false, priority = 3)
  public void search() {
	  searchInput.sendKeys(prop.getProperty("searchKey"));
	  searchInput.submit();
	  List<WebElement> anchorTags = TestControl.driver.findElements(By.tagName("a"));
	  for(WebElement ele : anchorTags){
		  count = count+1;
	  }
	  System.out.println("Total number of anchor tags are "+count);
  }

	@Test(enabled = false, priority = 1)
	public void predectiveSearch() throws Exception{
		searchInput.sendKeys(prop.getProperty("searchKey"));
		searchText(prop.getProperty("predectiveSearch"));
	}

	public void searchText(String text) throws Exception{
		List<WebElement> predectiveEle = TestControl.driver.findElements(By.tagName("b"));
		predectiveEle = 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("b")));
		for(WebElement ele : predectiveEle){
			if(ele.getText().equals("essay")){
				ele.click();
			break;
			}
			else if(!ele.getText().equals("essay"))
				continue;
		}
	}
	
	@Test(enabled = false,priority = 2)
	public void verifySearch(){
		searchInput.sendKeys(prop.getProperty("searchKey"));
		searchInput.submit();
		verifyResult = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//h3[contains(text(),'I love my india')]")));
		verifyResult = TestControl.driver.findElement(By.xpath(".//h3[contains(text(),'I love my india')]"));
		Assert.assertTrue("Search results is not as per expected ",verifyResult.isEnabled());
	}
	
	@Test(alwaysRun = false,priority = 4)
	public void verifyTitle(){
		Assert.assertTrue("Title of the page is not as per requirement",
				prop.getProperty("googleTitle").trim().equals(TestControl.driver.getTitle()));
	}
  @BeforeMethod
  public void beforeTest() throws IOException {
	  reader =new FileReader("C:\\Users\\Admin\\git\\CICD_jenkins\\CICD\\src\\test\\resources\\config.properties");
	  prop.load(reader);
	  if(prop.getProperty("chromeBrowser").equals("true")){
		  System.setProperty("webdriver.chrome.driver", "E:\\java neon workspace\\chrome browser\\chromedriver.exe");
		  TestControl.driver = new ChromeDriver();		  
	  }
	  else if(prop.getProperty("firefoxBrowser").equals("true")){
		  TestControl.driver = new FirefoxDriver();
		  
	  }
	  TestControl.driver.get("https://www.google.com/");
	  TestControl.driver.manage().window().maximize();
	  wait = new WebDriverWait(TestControl.driver,20);
	  searchInput = TestControl.driver.findElement(By.xpath(".//input[@type='text']"));
	  searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//input[@type='text']")));
	  }

  @AfterMethod
  public void afterTest() {
	  TestControl.driver.manage().deleteAllCookies();
	  TestControl.driver.close();
	  TestControl.driver.quit();
  }
}
