package com.org.diplomka.driver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;



import java.io.File;


public class FirefoxDriverManager extends DriverManager {

    private GeckoDriverService fFService;

    @Override
    public void startService() {
        if (null == fFService) {
            try {
                fFService = new GeckoDriverService.Builder()
                        .usingDriverExecutable(new File("geckodriver.exe"))
                        .usingAnyFreePort()
                        .build();
                fFService.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stopService() {
        if (null != fFService && fFService.isRunning())
            fFService.stop();
    }

    @Override
    public void createDriver() {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("test-type");
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
        driver = new FirefoxDriver(fFService, capabilities);
    }

}
