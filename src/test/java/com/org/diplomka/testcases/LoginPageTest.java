package com.org.diplomka.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.org.diplomka.base.TestBase;
import com.org.diplomka.pages.HomePage;
import com.org.diplomka.pages.LoginPage;

public class LoginPageTest extends TestBase{
    LoginPage loginPage;
    HomePage homePage;

    public LoginPageTest(){
        super();
    }

    @BeforeMethod
    public void setUp(){
        //Inicializace driveru
        initialization();

        //Inicializace PageObjectu LoginPage
        loginPage = new LoginPage(driver);

        //Inicializace PageObjectu HomePage
        homePage = new HomePage(driver);

        //Načtení stránky www.czc.cz
        homePage.get();

    }

    @Test(priority=1) //Id = 01 - Přihlášení uživatele
    public void loginTest(){

        //Přihlášení uživatele
        loginPage.login(prop.getProperty("username"), prop.getProperty("password"));

        //Validace zdali je uživatel přihlášen
        boolean isLoggedIn = loginPage.validateLogIn();

        Assert.assertTrue(isLoggedIn, "User does not seem to be logged in");
    }

    @Test(priority = 2) //Id = 02 - Odhlášení uživatele
    public void logOutTest(){

        //Přihlášení uživatele
        loginPage.login(prop.getProperty("username"), prop.getProperty("password"));

        //Načtení stránky www.czc.cz
        loginPage.get();

        //Validace zdali je uživatel odhlášen
        boolean isLoggedOut = loginPage.validateLogOut();
        Assert.assertTrue(isLoggedOut, "User is still logged in");
    }



    @AfterMethod
    public void tearDown(){
        driver.quit();
    }





}
