package pages;

import com.codeborne.selenide.*;
import static com.codeborne.selenide.Selenide.*;

public class AggregatedCarsListPage {
    public void clickFirstAggregatedCard() {
        ElementsCollection cards = $$("div.car-card__item-order-buttons");

        if (cards.isEmpty()) {
            System.out.println("По заданным параметрам отсутствуют автомобили");
            return;
        }

        SelenideElement firstCard = cards.first();
        firstCard.shouldBe(Condition.visible, Condition.enabled).click();
    }
}
