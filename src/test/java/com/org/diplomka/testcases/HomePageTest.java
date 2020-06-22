package com.org.diplomka.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.org.diplomka.base.TestBase;
import com.org.diplomka.pages.HomePage;
import com.org.diplomka.pages.LoginPage;
import com.org.diplomka.util.TestUtil;

public class HomePageTest extends TestBase {
    LoginPage loginPage;
    HomePage homePage;
    TestUtil testUtil;
    String sheetName = "product";


    @DataProvider
    public Object[][] getSearchItemData(){
        Object data[][] = TestUtil.getTestData(sheetName);
        return data;
    }

    public HomePageTest() {
        super();
    }



    @BeforeMethod
    public void setUp() {
        //Inicializace driveru
        initialization();

        //Inicializace třídy TestUtil
        testUtil = new TestUtil();

        //Inicializace PageObjectu LoginPage
        loginPage = new LoginPage(driver);

        //Inicializace PageObjectu HomePage
        homePage = new HomePage(driver);

        //Načtení stránky www.czc.cz
        homePage.get();

        //Přihlášení uživatele
        loginPage.login(prop.getProperty("username"), prop.getProperty("password")).get();

        //Načtení stránky www.czc.cz
        homePage.get();
    }


    @Test(priority=1) //Id = 03 - Ověření uživatele
    public void verifyUserNameTest(){
        //Validace uživatelova přihlašovacího jména
        String userName = homePage.getUserName();
        System.out.println("Actual username is: " + userName);
        System.out.println("Given username is: " + prop.getProperty("username"));

        Assert.assertEquals(userName, prop.getProperty("username"), "Username is not correct!");
    }



    @Test(priority=2, dataProvider="getSearchItemData") //Id = 04 - Vyhledání zboží
    public void validateItemSearch(String manufacturer, String product, String storage){
        //Vyhledání produktu
        homePage.searchItem(manufacturer, product, storage);

        //Validace zdali se produkt nachází ve výsledku vyhledání
        String searchItem = homePage.getSearchItemName();
        Assert.assertTrue(searchItem.contains(manufacturer +" "+product+", "+storage));

    }

    @Test(priority=2, dataProvider="getSearchItemData") //Id = 05 - Vložení zboží do košíku
    public void addItemIntoShoppingCart(String manufacturer, String product, String storage){
        //Vyhledání produktu
        homePage.searchItem(manufacturer, product, storage);

        //Vložení produktu do košíku
        homePage.addItemIntoShoppingCart();

        //Validace zdali byl produkt vložen do košíku
        Assert.assertTrue(homePage.validateIsItemAdded());

        //Vyprázdnění košíku pro konzistenci
        homePage.clearShoppingCart();

    }


    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

}
