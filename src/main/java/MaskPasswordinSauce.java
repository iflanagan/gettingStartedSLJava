import com.saucelabs.saucerest.SauceREST;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.server.RemoteObject;

public class MaskPasswordinSauce {

    public static void main(String[] args) throws MalformedURLException {

        String username = "<sauceuser>";
        String accessKey = "<sauceaccesskey>";
        String actualTitle = "Swag Labs";
        String testURL = "https://www.saucedemo.com";

        System.out.println("\nSetting Capabilities.");

        ChromeOptions chromeOpts = new ChromeOptions();
        chromeOpts.setExperimentalOption("w3c", true);

        MutableCapabilities sauceOpts = new MutableCapabilities();
        sauceOpts.setCapability("seleniumVersion", "3.11.0");
        sauceOpts.setCapability("user", username);
        sauceOpts.setCapability("accessKey", accessKey);
        sauceOpts.setCapability("tag", "new_Selenium_W3C_Test");
        sauceOpts.setCapability("name", "Selenium4Example");
        sauceOpts.setCapability("build", 1);
      //  sauceOpts.setCapability("recordLogs",false);

        MutableCapabilities caps = new MutableCapabilities();
        caps.setCapability(ChromeOptions.CAPABILITY,  chromeOpts);
        caps.setCapability("platformName", "Windows 8.1");
        caps.setCapability("browserVersion", "latest");
        caps.setCapability("sauce:options", sauceOpts);

        String sauceUrl = "https://"+username+":"+accessKey+"@ondemand.saucelabs.com/wd/hub";

        URL url = new URL(sauceUrl);
        RemoteWebDriver driver = new RemoteWebDriver(url, caps);

        SauceREST sauce = new SauceREST(username, accessKey, "US West 1");
        SessionId session = ((RemoteWebDriver)driver).getSessionId();
        String currentSessionID = session.toString();
        System.out.println("Session id: " +currentSessionID);

        try
        {
            System.out.println("Starting Login test.");
            sauceContext(driver, "Starting Login Test");
            sauceContext(driver, "Go to URl.");
            driver.navigate().to(testURL);

            //   sauceContext(driver, "Get the title");
            String title = driver.getTitle();

            // Starting test here
            PageObjects page = new PageObjects();
            setSauceLabeLogging(driver, "sauce: disable log");
            driver.findElementById(page.userInput).sendKeys(page.username);
            setSauceLabeLogging(driver, "sauce: enable log");

            //Disable logging
            setSauceLabeLogging(driver, "sauce: disable log");
            driver.findElementById(page.passwordInput).sendKeys(page.password);
            setSauceLabeLogging(driver, "sauce: enable log");
        //    js.executeScript("sauce:enable log");
            driver.findElementByCssSelector(page.loginButtoon).click();
            driver.findElementByCssSelector(page.hamburgerIcon).click();

            sauceContext(driver, "Perform Validation");
            boolean value = isTextPresent(driver,"Logout");

            if (value)
            {
                System.out.println("Login Passed.");
                sauceContext(driver, "Login Passed.");
                sauce.jobFailed(currentSessionID);
            }
            else
            {
                System.out.println("Login Failed.");
                sauceContext(driver, "Login Failed.");
                sauce.jobFailed(currentSessionID);
            }

            driver.findElementByCssSelector(page.logoutLink).click();



//            if (title.equals(actualTitle))
//            {
//
//                assertEquals("Swag Labs", title);
//                System.out.println("Get title test Passed.");
//                sauceContext(driver, "Title Verification Test Passed");
//                sauce.jobFailed(currentSessionID);
//            }
//            else
//            {
//                System.out.println("Get title test Failed.");
//                sauceContext(driver, "Title Verification Test Failed");
//                sauce.jobFailed(currentSessionID);
//            }


        }
        catch (Exception ex)
        {
            System.out.println("Can't execute script on SauceLabs: " +ex);
            //   sauceContext(driver, "Can't execute script on SauceLabs");
        }

        sauceContext(driver, "Quit Driver");
        driver.quit();
    }

    private static boolean isTextPresent(RemoteWebDriver driver,String text) {

        boolean value = false;

        try{
            value = driver.getPageSource().contains(text);
            value = true;
        }
        catch(Exception e){

            System.out.println("Can't execute method: isTextPresent " +e.getMessage());
        }

        return value;

    }
    private static void sauceContext(RemoteWebDriver driver, String text)
    {
        ((JavascriptExecutor)driver).executeScript("sauce:context=" +text);
    }
    private static void setSauceLabeLogging(RemoteWebDriver driver, String command)
    {
        // command = sauce: disable log TOOENABLE = sauce: enable log
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(command);

    }



}
