package helpers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import java.time.Duration;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CookieUtils {

    public static void acceptCookiesIfPresent() {

        SelenideElement cookieWindow = $("div.container.cookie-warning")
                .shouldBe(Condition.visible, Duration.ofSeconds(10));


        SelenideElement acceptButton = $$("div.cookie-warning__buttons button")
                .findBy(Condition.text("Принять всё"));

        if (acceptButton.exists() && acceptButton.is(Condition.visible)) {
            acceptButton.click();

            cookieWindow.should(Condition.disappear, Duration.ofSeconds(5));
        }
    }
}
