package com.org.diplomka.pages;

import com.org.diplomka.util.TestUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;


public class LoginPage extends LoadableComponent<LoginPage> {
    TestUtil testUtil;
    WebDriver driver;
    LoadableComponent<LoginPage> parent;

    //Page Factory - OR:
    @FindBy(name = "name")
    WebElement username;

    @FindBy(name = "password")
    WebElement password;

    @FindBy(className = "usr-logout")
    WebElement logoutBtn;

    @FindBy(id = "logged-user")
    WebElement loggedUserLink;

    @FindBy(xpath = "//*[@id='login-form']/div[3]/button")
    WebElement loginBtn;

    @FindBy(xpath = "//button[contains(text(),'Sign Up')]")
    WebElement signUpBtn;

    @FindBy(id = "logged-user")
    WebElement userLoggedIn;


    HomePage homePage;

    //Initializing the Page Objects:
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        homePage = PageFactory.initElements(driver, HomePage.class);

    }

    //Actions:
    public String validateLoginPageTitle() {
        return driver.getTitle();
    }

    public void clickOnLogOutButton() {


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", logoutBtn);

    }

    public boolean validateLogIn() {

        boolean isDisplayed = userLoggedIn.isDisplayed();
        if (isDisplayed == true) {
            System.out.println("User is logged in!");
        }
        return isDisplayed;
    }

    public HomePage login(String un, String pwd) {
        homePage.clickOnLoginButton();
        username.sendKeys(un);
        password.sendKeys(pwd);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", loginBtn);

        return new HomePage(driver);
    }

    public void logout() {
        clickOnLogOutButton();

    }

    public boolean validateLogOut() {
        logout();
        boolean isDisplayed = homePage.loginLink.isDisplayed();
        if (isDisplayed == true) {
            System.out.println("User is logged out!");
        }
        return isDisplayed;
    }


    @Override
    protected void load() {
        PageFactory.initElements(driver, this);
        driver.get("https://www.czc.cz/uzivatel");
    }

    @Override
    protected void isLoaded() throws Error {
        if (!testUtil.myElementIsClickable(this.driver, logoutBtn))
        {
            throw new Error("LoginPage was not successfully loaded");
        } else{
            System.out.println("LoginPage is loaded!");
        }
    }
}
