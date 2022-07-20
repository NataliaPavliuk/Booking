package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchResultPage extends BasePage{
    private final By destinationFieldBy = By.xpath("//span[@data-testid='address']");
    private final By nightCountsFieldBy = By.xpath("//div[@class='d8eab2cf7f dc2c6438ff']");
    private final By dateStartFieldBy = By.xpath("//button[@data-testid='date-display-field-start']");
    private final By dateEndFieldBy = By.xpath("//button[@data-testid='date-display-field-end']");
    private final By priceFilterListBy = By.xpath("//div[@data-filters-group='pri']//span[@class='bbdb949247']");
    private final By priceFieldBy = By.xpath("//span[@class='fcab3ed991 bd73d13072']");
    private final By filterPercentBy =By.xpath("//button[@aria-label='Close banner']");
    private final WebDriver driver;

    public SearchResultPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public WebElement getFirstPriceFilter(){
       return driver.findElements(priceFilterListBy).get(0);
    }

    public void checkPriceFilter(long DEFAULT_TIME){
        getFirstPriceFilter().click();
        waitForPageLoadComplete(DEFAULT_TIME);
    }

    public WebElement getFilterPercent(){
        return driver.findElement(filterPercentBy);
    }

    public boolean isAllItemsContainsCorrectPrice(int startPrice, int endPrice, int countOfNights, long DEFAULT_TIME) {
        waitForPageLoadComplete(DEFAULT_TIME);
        waitForElementIsClickable(DEFAULT_TIME, getFilterPercent());
        List<WebElement> priceList = getAllSearchPrice();
        boolean flag=true;
        for (WebElement priceField:priceList) {
           double price = Double.parseDouble( priceField.getText().substring(3).replaceAll(",",""));
           if(price<startPrice*countOfNights||price>endPrice*countOfNights){
                flag=false;
            }
        }
        return flag;
    }

    public List<WebElement> getAllSearchDestination(){
        return driver.findElements(destinationFieldBy);
    }

    public List<WebElement> getAllSearchPrice(){
        return driver.findElements(priceFieldBy);
    }

    public List<WebElement> getAllSearchNightCount(){
        return driver.findElements(nightCountsFieldBy);
    }

    public boolean containsNeedDestination(String city){
        List<WebElement> destinationList = getAllSearchDestination();
        boolean flag=true;
        for (WebElement destination:destinationList) {
         if(!(destination.getText().contains(city))){
             flag=false;
         }
        }
        return flag;
    }

    public boolean containsNeedNightCount(String night){
        List<WebElement> destinationList = getAllSearchNightCount();
        boolean flag=true;
        for (WebElement destination:destinationList) {
            if(!(destination.getText().contains(night))){
                flag=false;
            }
        }
        return flag;
    }

    public String getStartDate(){
        return driver.findElement(dateStartFieldBy).getText();
    }

    public String getEndDate(){
        return driver.findElement(dateEndFieldBy).getText();
    }
  }

