package com.org.diplomka.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.org.diplomka.driver.DriverManager;
import com.org.diplomka.driver.DriverManagerFactory;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.org.diplomka.util.TestUtil;
import com.org.diplomka.util.WebEventListener;
import org.openqa.selenium.support.ui.LoadableComponent;

public class TestBase extends LoadableComponent<TestBase> {

    public static WebDriver driver;
    public static Properties prop;
    public  static EventFiringWebDriver e_driver;
    public static WebEventListener eventListener;
    public static DriverManager driverManager;

    public TestBase(){
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/main/java/com/org"
                    + "/diplomka/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {

    }


    public static void initialization(){
        String browserName = prop.getProperty("browser");

        if(browserName.equals("chrome")){
            System.setProperty("webdriver.chrome.driver", "/Users/Michal/IdeaProjects/diplomka");
            driverManager = DriverManagerFactory.getManager(DriverManagerFactory.DriverType.CHROME);
            driver = driverManager.getDriver();
        }
        else if(browserName.equals("firefox")){
            System.setProperty("webdriver.firefox.driver", "/Users/Techmeyer/Downloads/firefoxdriver");
            driver = new FirefoxDriver();
        }



        e_driver = new EventFiringWebDriver(driver);
        // Now create object of EventListerHandler to register it with EventFiringWebDriver
        eventListener = new WebEventListener();
        e_driver.register(eventListener);
        driver = e_driver;

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);

        driver.get(prop.getProperty("url"));

    }

}
