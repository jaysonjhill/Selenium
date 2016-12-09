package solutioningweb.automate;

import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumDriver {

	private static final long ELEMENT_APPEAR = 60L;
	private WebDriverWait wait = null;
	public static WebDriver driver;
	private static WebDriver augmentedDriver;
	// Action Performer
	@SuppressWarnings("unused")
	private static Actions performer;
	public void initializeDriver(String hubURL, String browser,	String workspace, String excel) throws Exception{
		try {
			driver = new ExtendedFirefoxDriver(getCapability(browser));
			performer = new Actions(driver);
			driver.manage().window().setSize(new Dimension(1380,768));
			//driver.manage().window().setSize(new Dimension(1020, 737));
			System.out.println("Browser size: "	+ driver.manage().window().getSize());
			augmentedDriver = new Augmenter().augment(driver);
			wait = new WebDriverWait(driver, ELEMENT_APPEAR);
			
		} catch (Exception e) {
			//e.printStackTrace();
			dispose();
			throw e;
		}
	}

	protected DesiredCapabilities getCapability(String browser) {
		try {
			if (browser.contentEquals("firefox"))
				return DesiredCapabilities.firefox();
			else
				return DesiredCapabilities.firefox();

		} catch (Exception e) {

		}
		return null;
	}
	public void pageload()
	{
		driver.get("http://52.54.0.28:443/index.php");
		System.out.println("http://52.54.0.28:443/index.php");
		System.out.println("Loading MyEstiMate...");
		takeScreenShot("Index Page");
	}
	public void login() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Proceed to Estimate']"))).click();
		System.out.println("Loading login interface...");
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='username']")));
		takeScreenShot("Login Page");
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin");
		System.out.println("Username: admin entered.");
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("password");
		System.out.println("Password ******* entered.");
		takeScreenShot("Logging In");
		driver.findElement(By.xpath("//input[@value='LOGIN']")).click();
		System.out.println("Logging in...");
	}
	public void projectdetails()
	{
		takeScreenShot("Project Details Page");
	}

	// Screenshot
	public void takeScreenShot(String caseName) {
		String datePrefix = new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date());
		String path = "/var/jenkins_home/jobs/SampleWorkspace/jobs/SampleProject/jobs/SampleMavenJob/workspace/screenshots";
		String ssPath = "/var/jenkins_home/jobs/SampleWorkspace/jobs/SampleProject/jobs/SampleMavenJob/workspace/screenshots";
		try {
			File ssDir = new File(ssPath);
			if (!ssDir.exists())
				ssDir.mkdir();
			File dir = new File(path);
			if (!dir.exists()) {
				System.out.println("The location " + path + " does not exist.");
				dir.mkdir();
				System.out.println("A directory " + path + " is created.");
			}

			byte[] screenshot;

			screenshot = ((org.openqa.selenium.TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.BYTES);

			File screenshotFile = new File(MessageFormat.format("{0}/{1}-{2}",path, datePrefix, caseName.replace(" ", "_") + ".png"));

			FileOutputStream outputStream = new FileOutputStream(screenshotFile);
			try {
				outputStream.write(screenshot);
				System.out.println("Screen shot "+ screenshotFile.toString().substring(path.length() + 1) + " saved in "+ path);
			} finally {
				outputStream.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dispose() {
		driver.close();
		driver.quit();
	}

}
