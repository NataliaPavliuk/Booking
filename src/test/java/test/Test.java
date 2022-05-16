package test;

import businessObject.CheckFilters;
import businessObject.CheckPrice;
import businessObject.EnterTextToSearchField;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import pages.HomePage;
import pages.SearchResultPage;
import pages.ShoppingCartPage;
import util.JsonReader;
import util.PropertiesReader;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;

public class Test extends BaseTest {
    long DEFAULT_WAITING_TIME=new PropertiesReader().getDefaultWaitTime();

    @DataProvider(name = "dp")
    public Object[][] readJson(){
        JsonReader jsonReader=new JsonReader();
        jsonReader.jsonReader();
        JsonArray data=jsonReader.getData();
        int n=2;
        String[][] arr = new String[n][data.size()-1];
        for (int i = 0; i < data.size(); i++) {
                JsonObject field = (JsonObject) data.get(i);
                String searchField = (String) field.get("SearchField");
                String brand = (String) field.get("Brand");
                String price = (String) field.get("Price");
                arr[i][0] = searchField + "," + brand+","+price;
            }
        return arr;
    }


    @org.testng.annotations.Test(dataProvider="dp")
        public void myTest(String data){
        String[] fields = data.split(",");
        JsonReader jsonReader=new JsonReader();
            jsonReader.jsonReader();
            HomePage homePage = getHomePage();
            new EnterTextToSearchField().enterText(homePage, DEFAULT_WAITING_TIME, fields[0]);
            SearchResultPage searchResultPage = getSearchResultPage();
            CheckFilters checkFilters = new CheckFilters();
            checkFilters.takeCheckBox(searchResultPage, DEFAULT_WAITING_TIME, fields[1]);
            checkFilters.takeExpensiveItem(searchResultPage, DEFAULT_WAITING_TIME);
            checkFilters.clickFirstItem(homePage, DEFAULT_WAITING_TIME);
            ShoppingCartPage shoppingCartPage = getShoppingCartPage();
            Assert.assertTrue(new CheckPrice().checkPrice(shoppingCartPage, DEFAULT_WAITING_TIME) >Integer.parseInt(fields[2]));
        }

    }

