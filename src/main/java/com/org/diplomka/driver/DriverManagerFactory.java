package com.org.diplomka.driver;


public class DriverManagerFactory {


    public enum DriverType {
        CHROME,
        FIREFOX,

    }



    public static DriverManager getManager(DriverType type) {

        DriverManager driverManager;

        switch (type) {
            case CHROME:
                driverManager = new ChromeDriverManager();
                break;
            default:
                driverManager = new FirefoxDriverManager();
                break;
        }
        return driverManager;

    }
}
