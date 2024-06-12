import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class First {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
System.setProperty("webdriver.chrome.driver", "D:\\My workspace\\chrome-win64\\chromedriver-win64\\chromedriver.exe");
WebDriver driver = new ChromeDriver();
driver.navigate().to("https://airtel.in");
driver.manage().window().maximize();
Thread.sleep(5000);
Actions actions = new Actions(driver);
WebElement prepaidMenu = driver.findElement(By.xpath("//*[@id=\"nav-dropdowns-1\"]"));
actions.moveToElement(prepaidMenu).perform();

driver.findElement(By.xpath("//a[@class=\"wt-header-nav-l-it\"]//label[text()=\"Recharge\"]")).click();
	}

}
