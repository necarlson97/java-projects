import java.awt.Canvas;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.*;
import org.junit.Assert.*;

public class WurtzPlayer extends Canvas implements Runnable{
	
	private static WebDriver driver;
	private static String baseUrl;
	private boolean acceptNextAlert = true;
	private static StringBuffer verificationErrors = new StringBuffer();
	
	public static boolean done = false;
	
	static Thread wurtzThread;
	
	public static void main(String[] args){
		new WurtzPlayer();
	}
	
	public WurtzPlayer(){
		//this.addKeyListener(new KeyInput());
		this.run();
		wurtzThread = new Thread(this);
        wurtzThread.start();
	}
	
	public void run(){
		System.setProperty("webdriver.chrome.driver", "Drivers/chromedriver");
		
		try {
			setUp();
			playWurtz();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
		
	}

	public static void setUp() throws Exception {
		driver = new ChromeDriver();
		String uploadsURL = "https://www.youtube.com/watch?v=g02WKrWjUgA&list=UUq6aw03lNILzV96UvEAASfQ&index=1";
		baseUrl = uploadsURL;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public static void playWurtz() throws Exception {
		driver.get(baseUrl + "/psp/heproda/?cmd=login&languageCd=ENG&");
		
		//new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@type='button'])[13]")));
		wurtzThread.sleep(2000);
		driver.findElement(By.xpath("(//button[@type='button'])[13]")).click();
		//driver.findElement(By.xpath("(//button[@type='button'])[13]")).sendKeys(Keys.SPACE);
		//driver.findElement(By.xpath("(//button[@type='button'])[13]")).sendKeys(Keys.RETURN);
		driver.findElement(By.cssSelector("button.ytp-fullscreen-button.ytp-button")).click();
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
