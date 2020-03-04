import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class WebTest {

    private ThreadLocal<String> sessionId = new ThreadLocal<String>();
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();

    public static void main(String[] args) throws IOException {


        String SAUCE_USERNAME = "<sauce_user>";
        String SAUCE_ACCESS_KEY = "<sauce_access_key>";
        String TestURL = "https://www.saucedemo.com/";
        String actualTitle = "Swag Labs";
        int delay = 5000;

        

        // RDC on sauce: ondemand.us-west-1.saucelabs.com/wd/hub VDC ondemand.saucelabs.com:443/wd/hub
        String UNIFIED_PLATFORM_URL = "https://" +SAUCE_USERNAME+ ":" +SAUCE_ACCESS_KEY+ "@ondemand.saucelabs.com:443/wd/hub";

        URL url = new URL(UNIFIED_PLATFORM_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platform", "Windows 10");
     //   capabilities.setCapability("tag", "DesktopWebExample");
     //   capabilities.setCapability("name", "Desktop_Web_test_SL");
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("version", "latest");
      //  capabilities.setCapability("extendedDebugging", true);
      //  capabilities.setCapability("build", 1);

        SauceREST sauce = new SauceREST(SAUCE_USERNAME, SAUCE_ACCESS_KEY, "US West 1");

        RemoteWebDriver driver = new RemoteWebDriver(url, capabilities);

        SessionId session = ((RemoteWebDriver)driver).getSessionId();
        String currentSessionID = session.toString();
        System.out.println("Session id: " +currentSessionID);


        try
        {
        //    sauceContext(driver, "Starting web Test Now");
        //    sauceContext(driver, "Loading URL");
            driver.get(TestURL);
       //     sauceContext(driver, "Attempting to get the title");
            String currentTitle = driver.getTitle();
        //    sauceContext(driver, "Perform validation and determine if current title equals title");

            if (currentTitle.equals(actualTitle))
            {
          //      sauceContext(driver, "Title Verification Test Passed");
                System.out.println("Test Passed.");
         //       sauce.jobPassed(currentSessionID);
            }
            else
            {
            //    sauceContext(driver, "Title Verification Test Failed");
                System.out.println("Test Failed");
           //     sauce.jobFailed(currentSessionID);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Can't execute desktoop web test on sauceLabs " +ex.getMessage());
        }

     //   sauceContext(driver, "Quitting Driver");
        driver.quit();
    }

    private static void sauceContext(RemoteWebDriver driver, String text)
    {
        ((JavascriptExecutor)driver).executeScript("sauce:context=" +text);
    }
}

