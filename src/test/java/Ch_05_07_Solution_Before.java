import java.net.URL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ch_05_07_Solution_Before {

    private static final String APPIUM = "http://localhost:4723/wd/hub";
    private static final String SITE = "https://appiumpro.com";

    private RemoteWebDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "9");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("chromedriverExecutable", "C:\\Users\\nabey\\Documents\\webdriver\\chromedriver.exe");
        driver = new RemoteWebDriver(new URL(APPIUM), caps);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.get(SITE);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".toggleMenu"))).click();
        driver.findElement(By.linkText("Contact")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#contactEmail"))).sendKeys("foo@bar.com");
        driver.findElement(By.cssSelector("#contactText")).sendKeys("hello");
        driver.findElement(By.xpath("//input[@value='Send']")).click();
        String response = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='contactResponse error']"))).getText();
        assert(response.contains("Captcha"));
    }
}
