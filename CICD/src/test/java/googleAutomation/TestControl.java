package googleAutomation;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;

public class TestControl implements ITestListener {

	TestNG runner = new TestNG();
	List<String> list = new ArrayList<String>();
	public static WebDriver driver;
	public void onFinish(ITestContext result) {
		System.out.println("From finish Host name is"+result.getHost());
	}

	public void onStart(ITestContext arg0) {}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("Test failed methods "+result.getMethod());
//		for(int i =0;i<1;i++){
//			list.add("E:\\java neon workspace\\CICD\\test-output\\testng-failed.xml");
//			runner.setTestSuites(list);
//			runner.run();
//			continue;
//		}
		
//		TakesScreenshot screenshot = ((TakesScreenshot)driver);
//		File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
//		File destFile = new File("E:\\java neon workspace\\CICD\\screenshot");
//        try {
//			FileUtils.copyFile(srcFile, destFile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public void onTestSkipped(ITestResult arg0) {
		
	}

	public void onTestStart(ITestResult arg0) {
		
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Test passed methods "+result.getMethod());
	}

}
