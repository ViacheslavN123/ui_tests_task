package helpers;

import com.codeborne.selenide.SelenideElement;

public class Utils {

    public static int getSliderValue(SelenideElement element, String attribute) {
        String attr = element.getAttribute(attribute);
        if (attr == null) {
            throw new RuntimeException("Атрибут " + attribute + " отсутствует у элемента: " + element);
        }
        return Integer.parseInt(attr);
    }

    public static int getCurrentSliderOffset(SelenideElement sliderButton, int trackWidth) {
        String style = sliderButton.getAttribute("style"); // например, "left: 25%;"
        int currentLeftPercent = 0;
        if (style != null && style.contains("left:")) {
            String percentStr = style.replaceAll(".*left:\\s*([0-9]+)%.*", "$1");
            try {
                currentLeftPercent = Integer.parseInt(percentStr);
            } catch (NumberFormatException e) {
                currentLeftPercent = 0;
            }
        }
        return (int)Math.round(currentLeftPercent / 100.0 * trackWidth);
    }
    public static int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
