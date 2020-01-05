package testPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class Assignment3 {

    public WebDriver driver;
    public String browser;

    @Parameters({"browser"})
    @BeforeMethod
    public void setBrowserName(String browserName) {
        browser = browserName;

        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver_64.exe");

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }
    }

    @Test
    public void searchInvoice() throws InterruptedException, AWTException {

        driver.get("https://acme-test.uipath.com/account/login");
        driver.findElement(By.id("email")).sendKeys("kumar.testleaf@gmail.com");

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        driver.findElement(By.id("password")).sendKeys("leaf@12");
        driver.findElement(By.id("buttonLogin")).click();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement searchForInvoices = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[text()=' Invoices']")));
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[text()=' Invoices']"))));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", searchForInvoices);

        Actions actions = new Actions(driver);
        actions.moveToElement(searchForInvoices).click(driver.findElement(By.xpath("//*[text()='Search for Invoice']"))).build().perform();

        WebElement vendorTaxID = wait.until(ExpectedConditions.elementToBeClickable(By.id("vendorTaxID")));
        vendorTaxID.sendKeys("DE763212");
        driver.findElement(By.id("buttonSearch")).click();

        driver.navigate().back();

        driver.findElement(By.id("buttonShowAll")).click();

        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        List<WebElement> elements = driver.findElements(RelativeLocator.withTagName("tr"));
        for (WebElement element : elements) {
            if (element.getText().contains("IT Support"))
                System.out.println(element.getText().substring(0, 6));
        }
        driver.close();
    }
}
