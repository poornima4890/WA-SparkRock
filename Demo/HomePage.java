import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.*;

public class HomePage {
WebDriver driver;
By prepaidMenu = By.xpath("//*[@id=\"nav-dropdowns-1\"]");
By recharge = By.xpath("//a[@class=\"wt-header-nav-l-it\"]//label[text()=\"Recharge\"]");

public HomePage(WebDriver driver) {
	this.driver=driver;
    PageFactory.initElements(driver, this);

}
}
