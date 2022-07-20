package test;

import businessObject.CheckFilters;
import businessObject.EnterTextToSearchField;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import pages.HomePage;
import pages.SearchResultPage;
import util.JsonReader;
import util.PropertiesReader;
import java.lang.reflect.Method;
import static test.MethodsCount.writeMethodsWithAnnotationToFile;

@Listeners(CustomerTestListener.class)
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
                String destination = (String) field.get("Destination");
                String countOfNights = (String) field.get("CountOfNights");
                String startDate = (String) field.get("StartDate");
                String endDate = (String) field.get("EndDate");
                String startPrice = (String) field.get("StartPrice");
                String endPrice=(String) field.get("EndPrice");
                arr[i][0] = destination + "," + countOfNights+","+startDate+","+endDate+","+startPrice+","+endPrice;
            }
        return arr;
    }


    @org.testng.annotations.Test(dataProvider="dp")
 public void checkDestinationAndDateSearchTest(String data) {
        String[] fields = data.split(",");
        JsonReader jsonReader=new JsonReader();
        jsonReader.jsonReader();
        HomePage homePage = getHomePage();
        new EnterTextToSearchField().enterText(homePage, DEFAULT_WAITING_TIME, fields[0]);
        new CheckFilters().checkFilters(homePage, DEFAULT_WAITING_TIME);
        SearchResultPage searchResultPage = getSearchResultPage();
        Assert.assertTrue(searchResultPage.containsNeedDestination(fields[0]));
        Assert.assertTrue(searchResultPage.containsNeedNightCount(fields[1]));
        Assert.assertTrue(searchResultPage.getStartDate().contains(fields[2]));
        Assert.assertTrue(searchResultPage.getEndDate().contains(fields[3]));
    }

    @org.testng.annotations.Test(dataProvider="dp")
    public void checkIsPriceFilterCorrectWork(String data) {
        String[] fields = data.split(",");
        JsonReader jsonReader=new JsonReader();
        jsonReader.jsonReader();
        HomePage homePage = getHomePage();
        new EnterTextToSearchField().enterText(homePage, DEFAULT_WAITING_TIME, fields[0]);
        new CheckFilters().checkFilters(homePage, DEFAULT_WAITING_TIME);
        SearchResultPage searchResultPage = getSearchResultPage();
        searchResultPage.checkPriceFilter(DEFAULT_WAITING_TIME);
        writeMethodsWithAnnotation();
        Assert.assertTrue(searchResultPage.isAllItemsContainsCorrectPrice(Integer.parseInt(fields[4]), Integer.parseInt(fields[5]), Integer.parseInt(fields[1]), DEFAULT_WAITING_TIME));
    }

    public void writeMethodsWithAnnotation() {
        Test test = new Test();
        Method[] methods = test.getClass().getMethods();
        writeMethodsWithAnnotationToFile(methods);
    }
    }

