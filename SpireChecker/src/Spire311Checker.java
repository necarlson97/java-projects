import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Spire311Checker {

	private static WebDriver driver;
	private static String baseUrl;
	private boolean acceptNextAlert = true;
	private static StringBuffer verificationErrors = new StringBuffer();
	
	public static void run() throws Exception{
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
	
		setUp();
		testSpire311Checker();
		tearDown();
	}

	public static void setUp() throws Exception {
		driver = new ChromeDriver();
		baseUrl = "https://www.spire.umass.edu";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public static void testSpire311Checker() throws Exception {
		driver.get(baseUrl + "/psp/heproda/?cmd=login&languageCd=ENG&");
		driver.findElement(By.id("userid")).clear();
		driver.findElement(By.id("userid")).sendKeys("necarlson");
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys("19971997Nn");
		driver.findElement(By.name("Submit")).click();
		System.out.println("Logged in");
		
		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.id("pthnavbca_PORTAL_ROOT_OBJECT")));
		driver.findElement(By.id("pthnavbca_PORTAL_ROOT_OBJECT")).click();
		//driver.findElement(By.id("DERIVED_SSS_SCL_SSS_ENRL_CART$276$")).click();
		driver.findElement(By.id("HCCC_ENROLLMENT")).click();
		driver.findElement(By.id("crefli_HC_SSR_SSENRL_CART_GBL2")).click();
		System.out.println("Going to shopping cart");
		
//		driver.switchTo().frame(driver.findElement(By.id("ptifrmtgtframe")));
//		new WebDriverWait(driver, 3).until(ExpectedConditions.visibilityOfElementLocated(By.id("SSR_DUMMY_RECV1$sels$1$$0")));
//		driver.findElement(By.id("SSR_DUMMY_RECV1$sels$1$$0")).click();
//		driver.findElement(By.id("DERIVED_SSS_SCT_SSR_PB_GO")).click();
//		System.out.println("Selected semester");
		
		driver.findElement(By.id("win0divP_SELECT$0")).click();
		//driver.findElement(By.id("P_SELECT$0")).click();
		System.out.println("Selected class");
		
		driver.findElement(By.id("DERIVED_REGFRM1_LINK_ADD_ENRL$291$")).click();
		System.out.println("Enrolled");
		
		driver.findElement(By.id("DERIVED_REGFRM1_SSR_PB_SUBMIT")).click();
		System.out.println("Submitted");
		
		WebElement result = driver.findElement(By.id("win0divDERIVED_REGFRM1_SS_MESSAGE_LONG$0"));
		System.out.println(result.getText());
		
		if(result.getText().contains("Error:")){
			System.out.println("No dice. Trying again in 30mins");
		}
		else {
			System.out.println("Success! Congradulations. Exiting program.");
			System.exit(0);
		}
	}

	public static void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}


