package state;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class World {

    private WebDriver driver ;



    private JavascriptExecutor js;

    int timeoutInSeconds = 30;

    private WebDriverWait wait;

    public World(){
        System.out.println("Mapping Gecko Driver to included driver file");
        System.setProperty("webdriver.gecko.driver", "src/test/resources/FirefoxDriver/geckodriver.exe");

        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, timeoutInSeconds);

    }


    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public JavascriptExecutor getJs() {
        return js;
    }

    public void setJs(JavascriptExecutor js) {
        this.js = js;
    }

    public boolean waitForPageToLoad(String targetURL) {
        targetURL = targetURL.toLowerCase(Locale.ROOT);
        targetURL = targetURL.replace("http://","");
        targetURL = targetURL.replace("https://","");
        targetURL = targetURL.replace("www.","");

        getWait().until(ExpectedConditions.urlContains(targetURL));

        return true;
    }
}
