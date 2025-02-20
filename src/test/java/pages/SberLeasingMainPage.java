package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class SberLeasingMainPage {
    private SelenideElement selectByParametersButton = $("a[href=\"/automall/#marketplace-horizontal-filter-title\"]");
    public void selectByParameters() {
        selectByParametersButton.click();
    }
}