package com.org.diplomka.pages;

import com.org.diplomka.util.TestUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.org.diplomka.base.TestBase;
import org.openqa.selenium.support.ui.LoadableComponent;


public class HomePage extends LoadableComponent<HomePage> {

    TestUtil testUtil;
    WebDriver driver;

    @FindBy(id = "login")
    @CacheLookup
    WebElement loginBtn;

    @FindBy(className="usr-alias")
    WebElement userNameLabel;

    @FindBy(name="fulltext")
    WebElement searchBar;

    @FindBy(xpath = "//*[@id='main-wrapper']/div[1]/form/input[2]")
    WebElement searchBtn;

    @FindBy(id= "tiles")
    WebElement anyItem;

    @FindBy(id = "login")
    @CacheLookup
    WebElement loginLink;

    @FindBy(id="logged-user")
    WebElement userLoggedIn;

    @FindBy(xpath="//*[@id='tiles']/div[1]/div[1]/div[2]/div[2]/button")
    WebElement addItemBtn;

    @FindBy(xpath="//*[@id='basket-preview']/a/span")
    WebElement emptyCartIndicator;

    @FindBy(xpath="//*[@id='basketRemoveAll']/span")
    WebElement emptyCartBtn;




    // Initializing the Page Objects:
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getUserName(){
        return userNameLabel.getAttribute("textContent");
    }

    public HomePage clickOnLoginButton(){
        loginBtn.click();
        return new HomePage(driver);
    }

    public void clickOnAddItem(){
        addItemBtn.click();
    }

    public void clickEmptyShoppingCart(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", emptyCartBtn);
    }

    public String getSearchItemName(){
        return anyItem.getAttribute("innerText");

    }

    public void searchItem(String manufacturer, String product, String storage){
        searchBar.sendKeys(manufacturer + " "+ product + " "+storage);
        searchBtn.click();
    }

    public void addItemIntoShoppingCart(){
            clickOnAddItem();
    }

    public boolean validateIsItemAdded(){
        if(emptyCartIndicator.getAttribute("title").contains("Prázdný košík")){
            return false;
        } else {
            return true;
        }
    }

    public void clearShoppingCart(){
        clickEmptyShoppingCart();
    }


    @Override
    protected void load() {
        PageFactory.initElements(driver, this);
        driver.get("https://www.czc.cz");
    }

    @Override
    protected void isLoaded() throws Error {

        if(!testUtil.myElementIsClickable(this.driver, loginLink)) {
            throw new Error("HomePage was not successfully loaded");
        }else{
            System.out.println("Homepage is loaded");
        }
    }

}
