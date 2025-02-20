package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class GoogleSearchPage {

    private final SelenideElement searchBox = $("textarea[name='q']");
    private final SelenideElement searchButton = $$("input[name='btnK']").findBy(visible);

}
