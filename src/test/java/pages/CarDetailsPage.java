package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import models.FilterStore;

import static com.codeborne.selenide.Selenide.$;

public class CarDetailsPage {
    private SelenideElement brandElement = $("div[class*='car-card__detail-mark'] > h1");
    public FilterStore getAppliedFilters() {
        FilterStore store = new FilterStore();
        store.setBrand(brandElement.shouldBe(Condition.visible).getText().trim());
        return store;
    }
}