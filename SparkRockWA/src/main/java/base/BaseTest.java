package base;

import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {
	WebDriver driver;
	Actions actions;
	WebDriverWait wait;

	@BeforeMethod
	public void setup() {
		driver = new ChromeDriver();
		driver.get("http://airtel.in");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2000));

	}

	@Test(priority = 1)
//Verify the REcharge page - Positive flow till Payments page
	public void rechargePage() throws InterruptedException {
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement prepaidMenu = driver.findElement(By.xpath("//*[@id='nav-dropdowns-1']"));
		// hover on the element
		actions.moveToElement(prepaidMenu).perform();
		// Click on REcharge option
		driver.findElement(By.xpath("//*[@class = 'wt-header-nav-l-it']//*[text()='Recharge']")).click();

		// enter mobile number
		driver.findElement(By.tagName("input")).sendKeys("9962334890");
		Thread.sleep(5000);

		List<WebElement> headerScroll = driver
				.findElements(By.xpath("//*[@class = 'wt-tabs-header-scroll-section']//*[@class='wt-tab']"));
		int size = headerScroll.size();
		Assert.assertEquals(size, 11);
		System.out.println("Page loaded successfully with all the header options");
		List<WebElement> benAmount = driver.findElements(By.xpath("//*[@class = 'wt-typography benefit-title']"));
		String firstBen = benAmount.get(0).getText();
		System.out.println(firstBen);
		driver.findElements(By.xpath("//*[text()='VIEW DETAILS']")).get(0).click();
		Thread.sleep(5000);
		WebElement modal = driver
				.findElement(By.xpath("//*[@class = 'wt-card-cntnr wt-modal-card pack-details-modal']"));
		wait.until(ExpectedConditions.visibilityOf(modal));
		WebElement amount = driver.findElement(By.tagName("h2"));
		String amt = amount.getText();
		System.out.println(amt);
		Assert.assertEquals(firstBen.replaceAll("\\s+", ""), amt.replaceAll("\\s+", ""));
		driver.findElement(By.xpath("//button[@data-testid = 'rechargeNowButton']")).click();

		Assert.assertEquals(true, (driver.findElement(By.xpath("//h3[text()='Payment options']"))).isDisplayed());

	}

	@Test(priority = 2)
	public void invalidMobileNumber() throws InterruptedException {
		actions = new Actions(driver);
		WebElement prepaidMenu = driver.findElement(By.xpath("//*[@id='nav-dropdowns-1']"));
		// hover on the element
		actions.moveToElement(prepaidMenu).perform();
		// Click on REcharge option
		driver.findElement(By.xpath("//*[@class = 'wt-header-nav-l-it']//*[text()='Recharge']")).click();

		// enter mobile number
		driver.findElement(By.tagName("input")).sendKeys("1234567890");
		Thread.sleep(5000);
		try {
			if (driver.findElement(By.xpath("//*text()='Invalid mobile number")).isDisplayed()) {
				System.out.println("Error message displayed as expected");
			} else {
				System.out.println("Error message is not displayed as expected");
			}
		} catch (Exception e) {
		}

	}

	@Test(priority = 3)
	public void intRoamingCountry() throws InterruptedException {
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		WebElement prepaidMenu = driver.findElement(By.xpath("//*[@id='nav-dropdowns-1']"));
		// hover on the element
		actions.moveToElement(prepaidMenu).perform();
		driver.findElement(By.xpath("//*[@class = 'wt-header-nav-l-it']//*[text()='International Roaming']")).click();
		driver.findElement(By.tagName("input")).sendKeys("9962334890");
		Thread.sleep(5000);
		try {
			if (driver.findElement(By.xpath("//*[@data-testid='packListContainer']")).isDisplayed()) {
				System.out.println("Available packs are displayed as expected");
			} else {
				System.out.println("Available packs are not displayed as expected");

			}
		} catch (Exception e) {
		}

		String parent_handle = driver.getWindowHandle();
		driver.findElement(By.xpath("//*[@data-testid='packListContainer']//*[@id='ir-view-covered-0']")).click();
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		Set<String> handles = driver.getWindowHandles();
		for (String handle1 : handles) {
			if (!parent_handle.equals(handle1)) {
				driver.switchTo().window(handle1);
			}
			Thread.sleep(5000);

			driver.findElement(By.xpath("//*[text()='COVERED COUNTRIES ']//span")).click();
			WebElement country = driver.findElement(By.xpath("//*[text()='Switzerland']"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true)", country);
			if (country.isDisplayed()) {
				System.out.println("Switzerland is present in the list");
			} else {
				System.out.println("Test failed. Switzerland not in the list");
			}
		}
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
