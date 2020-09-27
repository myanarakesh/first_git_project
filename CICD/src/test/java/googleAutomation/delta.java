package googleAutomation;

import static org.testng.Assert.assertTrue;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(googleAutomation.TestControl.class)
public class delta {
	Properties prop = new Properties();
	FileReader reader = null;
	WebDriverWait wait = null;
	
  @Test(enabled=false)
  public void vrifyPageTitle() {
	 System.out.println("delta title "+TestControl.driver.getTitle()); 
  }
  
  @Test(enabled=true)
  public void oneWayTicketBook(){
      WebElement DestinationBtn = TestControl.driver.findElement(By.id("toAirportName"));
	  Assert.assertTrue(DestinationBtn.isEnabled(), "Destination button is not enabled");
	  DestinationBtn.click();
	  
	  WebElement DestinationInputEle = TestControl.driver.findElement(By.id("search_input"));
	  Assert.assertTrue(DestinationInputEle.isEnabled(),"Destination input box is not enabled");
	  DestinationInputEle.sendKeys("CPH");
	  
	  WebElement selectDestintionEle = wait.until
			  (ExpectedConditions.presenceOfElementLocated(By.xpath(".//li[@class='airport-list ng-star-inserted']//span[1]")));
	  selectDestintionEle.click();
	  TestControl.driver.findElement(By.id("selectTripType-val")).click();
	  
	  WebElement oneWayEle = TestControl.driver.findElement(By.id("ui-list-selectTripType1"));
	  oneWayEle.click();
	  
	  WebElement selectedTypeValEle = TestControl.driver.findElement(By.id("selectTripType-val"));
	  Assert.assertEquals(selectedTypeValEle.getText(), "One Way");
	  
	  WebElement datePickerEle = TestControl.driver.findElement(By.id("input_departureDate_1"));
	  datePickerEle.click();
	  
	  WebElement verifyDateEnableEle = wait.until
			  (ExpectedConditions.presenceOfElementLocated(By.className("calenderContainer")));
	  Assert.assertTrue(verifyDateEnableEle.isEnabled(), "Date is not enabled");

	  LocalDate systemDate = LocalDate.now();
	  LocalDate addedDate = systemDate.plusDays(185);
	  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMMM yyyy");
	  String DateToBeSelected = addedDate.format(dtf);
	  System.out.println("DateToBeSelected : "+DateToBeSelected);
	  
//	  String DateToBeSelected = "19 March 2021";
	  String[] separtedDateToBeSelected = DateToBeSelected.split(" ");
	  String yearToSelect = separtedDateToBeSelected[2];
	  String monthToSelect = separtedDateToBeSelected[1];
	  String dateToSelect = separtedDateToBeSelected[0];
	  
	  List<WebElement> monthEle = TestControl.driver.findElements
			  (By.xpath(".//span[starts-with(@class,'dl-datepicker-month-')]"));
	  
	  List<WebElement> yearEle = TestControl.driver.findElements
			  (By.xpath(".//span[starts-with(@class,'dl-datepicker-year dl-datepicker-year')]"));
	  
	  WebElement firstCalenderYearEle = 
			  TestControl.driver.findElement(By.cssSelector("span.dl-datepicker-year.dl-datepicker-year-0"));

	  WebElement firstCalenderMonthEle = wait.until(
			  ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.dl-datepicker-month-0"))); 
	  
	  WebElement secondCalenderMonthEle = wait.until(
			  ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.dl-datepicker-month-1"))); 

	  WebElement doneBtn = TestControl.driver.findElement(By.className("donebutton"));
	  WebElement nxtBtnEle = TestControl.driver.findElement(By.xpath(".//a[@title='To select next month']//span"));
	  
	  //select year
	  for(int i=1;i<=2;i++){
		  for(int y=0;y<12;y++){
			  if(!yearToSelect.equals(firstCalenderYearEle.getText())){
				  nxtBtnEle.click();
			  }
		  }
		  
		  //select month
		  for(int m=1;m<12;m++){
			  if(!monthToSelect.equals(firstCalenderMonthEle.getText()) && 
					  !monthToSelect.equals(secondCalenderMonthEle.getText())){
				  nxtBtnEle.click();
			  }
		  }
	  }
	  
	  List<WebElement> datesEle = wait.until
			  (ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(".//div[starts-with(@class,'dl-datepicker-group dl-datepicker-group-')]//a[@class='dl-state-default']")));
	  
	  //select date
	  for(int i=1;i<=datesEle.size();i++){
		  	if(datesEle.get(i).getAttribute("aria-label").contains(monthToSelect)
		  			&& datesEle.get(i).getText().equals(dateToSelect)){
				  datesEle.get(i).click();
				  doneBtn.click();
				  return;
			  }
	  }

	  String selectedDate = monthToSelect.substring(0, 3)+" "+dateToSelect;
	  System.out.println("selectedDate: "+selectedDate);
	  assertTrue(TestControl.driver.findElement(By.cssSelector("span.calenderDepartSpan")).getText().equals(selectedDate),
			  "Selected Date is not as per expected");

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
	  TestControl.driver.get("https://www.delta.com/");
	  TestControl.driver.manage().window().maximize();
	  wait = new WebDriverWait(TestControl.driver,20);
	  }

  @AfterMethod
  public void afterTest() {
	  TestControl.driver.manage().deleteAllCookies();
	  TestControl.driver.close();
	  TestControl.driver.quit();
  }
}
