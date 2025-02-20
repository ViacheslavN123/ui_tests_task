package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class CarModelPage {
    public void clickCarCard() {

        SelenideElement carCard = $("div[class='car-card__item-section']");
        carCard.scrollIntoView(true);
        carCard.shouldBe(Condition.visible, Condition.enabled);
        carCard.click();
    }
}