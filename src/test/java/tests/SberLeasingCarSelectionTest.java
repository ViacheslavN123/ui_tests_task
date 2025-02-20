package tests;

import models.FilterStore;
import pages.*;
import configuration.ConfigurationTest;
import helpers.CookieUtils;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SberLeasingCarSelectionTest extends ConfigurationTest {
    private SberLeasingMainPage mainPage;
    private AutoMallPage autoMallPage;
    private AggregatedCarsListPage aggregatedCarsListPage;
    private CarDetailsPage carDetailsPage;
    private CarModelPage carModelPage;

    @BeforeEach
    public void setupPages() {
        mainPage = new SberLeasingMainPage();
        autoMallPage = new AutoMallPage();
        aggregatedCarsListPage = new AggregatedCarsListPage();
        carDetailsPage = new CarDetailsPage();
        carModelPage = new CarModelPage();
        carDetailsPage = new CarDetailsPage();
    }

    @Test
    public void testCarSelectionInSberLeasing(){

        open("https://www.sberleasing.ru");
        CookieUtils.acceptCookiesIfPresent();

        mainPage.clickSelectByParameters();
        autoMallPage.selectCity();
        autoMallPage.selectBrand();
        autoMallPage.selectModel();
        autoMallPage.selectColor();
        autoMallPage.setRandomPrice();
        autoMallPage.selectFilterOption("Привод");
        autoMallPage.selectNumericFilterOption("Объём двигателя");
        autoMallPage.clickShowAllOffers();

        aggregatedCarsListPage.clickFirstAggregatedCard();
        sleep(2000);
        carModelPage.clickCarCard();

        FilterStore actualFilters = carDetailsPage.getAppliedFilters();
        FilterStore expectedFilters = autoMallPage.getFilterStore();

        String actualBrand = actualFilters.getBrand();
        String expectedBrand = expectedFilters.getBrand();

        assertTrue(actualBrand.contains(expectedBrand),
                "Фактическая марка '" + actualBrand + "' не содержит ожидаемую марку '" + expectedBrand + "'");
    }
}
