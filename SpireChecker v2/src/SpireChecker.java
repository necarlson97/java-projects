import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SpireChecker {
	
	Driver guiDriver;
	WebDriver driver;
	
	String baseUrl = "https://www.spire.umass.edu";
	
	public SpireChecker(String user, String pass, Boolean quiet, int cartIndex, Driver d) {
		
		guiDriver = d;
		
		if(quiet) {
			Capabilities caps = new DesiredCapabilities();
	        ((DesiredCapabilities) caps).setJavascriptEnabled(true);                
	        ((DesiredCapabilities) caps).setCapability("takesScreenshot", true);  
	        ((DesiredCapabilities) caps).setCapability(
	                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
	                "Drivers/phantomjs"
	            );
			System.setProperty("phantomjs.binary.path", "Drivers/phantomjs");
			driver = new PhantomJSDriver();
			System.out.println("Quiet");
		}
		else {
			System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
			driver = new ChromeDriver();
			System.out.println("Loud");
		}
		
	    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		login(user, pass);
		shoppingCart();
		selectClass(cartIndex);
		
		String result = submit();
		if(result.toLowerCase().contains("error"))
			guiDriver.changeGui("Not completed, trying again later");
		else guiDriver.changeGui("Success! Congrats");
		
		driver.quit();
	}
	
	public void login(String user, String pass) {
		driver.get(baseUrl + "/psp/heproda/?cmd=login&languageCd=ENG&");
		driver.findElement(By.id("userid")).clear();
		driver.findElement(By.id("userid")).sendKeys(user);
		driver.findElement(By.id("pwd")).clear();
		driver.findElement(By.id("pwd")).sendKeys(pass);
		driver.findElement(By.name("Submit")).click();
		
		if(driver.getCurrentUrl().contains("error"))
			System.out.println("Error logging in");
		else 
			System.out.println("Logged in");
		
		guiDriver.changeGui("Logged in");
		rest(2);
	}
	
	public void shoppingCart() {
		driver.findElement(By.id("pthnavbca_PORTAL_ROOT_OBJECT")).click();
		driver.findElement(By.id("HCCC_ENROLLMENT")).click();
		driver.findElement(By.id("crefli_HC_SSR_SSENRL_CART_GBL2")).click();
		System.out.println("At shopping cart");
		guiDriver.changeGui("At shopping cart");
	}
	
	public void selectClass(int cartIndex) {
		rest(2);
		driver.switchTo().frame(driver.findElement(By.id("ptifrmtgtframe")));
		driver.findElement(By.id("win0divP_SELECT$"+cartIndex)).click();
		System.out.println("Selected class");
		guiDriver.changeGui("Selected class");
	}
	
	public String submit() {
		driver.findElement(By.id("DERIVED_REGFRM1_LINK_ADD_ENRL$291$")).click();		
		driver.findElement(By.id("DERIVED_REGFRM1_SSR_PB_SUBMIT")).click();
		System.out.println("Submitted");
		guiDriver.changeGui("Attempting submit");
		
		WebElement result = driver.findElement(By.id("win0divDERIVED_REGFRM1_SS_MESSAGE_LONG$0"));
		return result.getText();
	}
	
	public void rest(int seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
