package pages;

import models.FilterStore;
import com.codeborne.selenide.*;

import java.util.function.Consumer;

import static helpers.Utils.*;
import static com.codeborne.selenide.Selenide.*;

public class AutoMallPage {

    // Выпадающие меню
    private SelenideElement citySelect = $("label[id=filter-city] > input");    // выпадающее меню "Город"
    private SelenideElement brandSelect = $("label[id=filter-mark] > input");  // выпадающее меню "Марка"
    private SelenideElement modelSelect = $("label[id=filter-model] > input");  // выпадающее меню "Модель"
    private SelenideElement colorSelect = $("label[aria-label='Цвет'] > input");  // выпадающее меню "Модель"
    private SelenideElement showAllOffersButton = $("a[href*='/automall/filter']");
    ;

    private FilterStore filterStore = new FilterStore();

    public void selectCity() {
        selectFirstElementFromDropdownMenu(citySelect, filterStore::setCity);
    }

    public void selectBrand() {
        selectFirstElementFromDropdownMenu(brandSelect, filterStore::setBrand);
    }

    public void selectModel() {
        selectFirstElementFromDropdownMenu(modelSelect, filterStore::setModel);
    }

    public void selectColor() {
        selectFirstElementFromDropdownMenu(colorSelect, filterStore::setColor);
    }

    private void selectFirstElementFromDropdownMenu(SelenideElement dropdown, Consumer<String> consumer) {
        if (!dropdown.exists()) {
            System.out.println("Фильтр недоступен, пропускаем его.");
            return;
        }

        // Скроллим с учётом фиксированного хедера
        scrollToElementWithOffset(dropdown, 100);

        // После прокрутки убеждаемся, что элемент видим и доступен
        if (!dropdown.is(Condition.visible) || !dropdown.is(Condition.enabled)) {
            System.out.println("Элемент выпадающего меню не доступен после прокрутки, пропускаем фильтр.");
            return;
        }
        // Открываем выпадающее меню
        dropdown.shouldBe(Condition.clickable).click();

        // Ищем контейнер выпадающего меню
        SelenideElement container = $$("div.sbl-filter-block__selector-values").filter(Condition.visible).first();
        if (container == null || !container.exists() || !container.is(Condition.visible)) {
            System.out.println("Контейнер выпадающего меню не найден, пропускаем фильтр.");
            return;
        }

        // Дополнительно проверяем кликабельность дропдауна
        if (!dropdown.is(Condition.clickable)) {
            System.out.println("Выпадающее меню заблокировано, пропускаем фильтр.");
            return;
        }

        // Фокусируем контейнер для внутренней прокрутки
        executeJavaScript("arguments[0].focus();", container);

        // Собираем коллекцию всех опций внутри контейнера
        ElementsCollection options = container.$$("label.sbl-filter-checkbox__title");
        if (options.isEmpty()) {
            System.out.println("Опции не найдены по селектору: label.sbl-filter-checkbox__title, пропускаем фильтр.");
            return;
        }

        // Фильтруем коллекцию, оставляя только видимые элементы
        ElementsCollection visibleOptions = options.filter(Condition.visible);
        if (visibleOptions.isEmpty()) {
            System.out.println("Ни одна из опций не отображается, пропускаем фильтр.");
            return;
        }

        // Случайным образом выбираем индекс
        int randomIndex = (visibleOptions.size() > 1) ? getRandomNumber(0, visibleOptions.size() - 1) : 0;
        SelenideElement randomOption = visibleOptions.get(randomIndex);

        // Прокручиваем контейнер до выбранной опции
        int containerTop = container.getLocation().getY();
        int optionTop = randomOption.getLocation().getY();
        int offset = optionTop - containerTop;
        executeJavaScript("arguments[0].scrollTop = arguments[1];", container, offset);
        sleep(500);

        // Запоминаем текст выбранной опции
        String selectedText = randomOption.getText().trim();

        // Кликаем по выбранной опции
        randomOption.click();

        // Сбрасываем прокрутку контейнера
        //executeJavaScript("arguments[0].scrollTop = 0;", container);
        
        consumer.accept(selectedText);
        sleep(6000);
    }


    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Метод для выбора фильтров по именам. Он принимает переменное число названий фильтров.
    public void selectFilterOption(String... filterTitles) {
        // Для каждого переданного названия фильтра выполняем следующие действия:
        for (String filterTitle : filterTitles) {
            // Находим элемент-заголовок фильтра, который содержит нужный текст (например, "Коробка передач")
            SelenideElement titleElement = $$("div.horizontal-filter-block__property-title").findBy(Condition.text(filterTitle));

            // Получаем родительский контейнер заголовка, в котором, как предполагается, находится и блок чекбоксов
            SelenideElement filterContainer = titleElement.parent();

            // Из контейнера получаем коллекцию всех активных (не disabled) чекбоксов.
            // Селектор выбирает элементы input с типом "checkbox", у которых отсутствует атрибут disabled.
            ElementsCollection activeInputs = filterContainer.$$("[type='checkbox']:not([disabled])");

            // Если активных чекбоксов нет, выводим сообщение и переходим к следующему фильтру
            if (activeInputs.isEmpty()) {
                System.out.println("Нет доступных опций для фильтра: " + filterTitle);
                continue;
            }

            // Выбираем случайный индекс из доступных чекбоксов
            int randomIndex = getRandomNumber(0, activeInputs.size() - 1);
            // Получаем выбранный чекбокс по случайному индексу
            SelenideElement chosenInput = activeInputs.get(randomIndex);

            // Извлекаем значение атрибута "id" выбранного чекбокса
            String id = chosenInput.getAttribute("id");
            // Находим соответствующий label по селектору, используя значение id (label с for='id')
            SelenideElement chosenLabel = filterContainer.$("label[for='" + id + "']");

            // Проверяем, что найденный label видимый, и кликаем по нему для выбора чекбокса
            chosenLabel.shouldBe(Condition.visible).click();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setRandomPrice() {
        // Находим элемент слайдера (родительский контейнер кнопки слайдера)
        SelenideElement sliderButton = $("div.el-slider__button-wrapper");

        // Получаем минимальное значение слайдера из атрибута aria-valuemin
        int sliderMin = getSliderValue(sliderButton, "aria-valuemin");

        // Получаем максимальное значение слайдера из атрибута aria-valuemax
        int sliderMax = getSliderValue(sliderButton, "aria-valuemax");

        // Генерируем случайное число между sliderMin и sliderMax
        int randomValue = getRandomNumber(sliderMin, sliderMax);

        // Вычисляем отношение выбранного значения к диапазону слайдера
        double ratio = (randomValue - sliderMin) / (double) (sliderMax - sliderMin);

        // Находим трек слайдера по селектору "div.el-slider"
        SelenideElement sliderTrack = $("div.el-slider");

        // Получаем ширину трека слайдера в пикселях
        int trackWidth = sliderTrack.getSize().getWidth();

        // Вычисляем целевое смещение в пикселях, которое соответствует выбранному значению
        int targetOffset = (int) Math.round(ratio * trackWidth);

        // Вычисляем текущее смещение ползунка, используя вспомогательный метод
        int currentOffset = getCurrentSliderOffset(sliderButton, trackWidth);

        // Вычисляем разницу между целевым смещением и текущим смещением
        int deltaOffset = targetOffset - currentOffset;

        // Перемещаем ползунок слайдера с использованием Selenium Actions:
        // Перемещаемся к sliderButton, зажимаем его, перемещаем на deltaOffset по оси X, затем отпускаем
        actions().moveToElement(sliderButton).clickAndHold().moveByOffset(deltaOffset, 0).release().build().perform();

        filterStore.setPriceSelected(randomValue);
        sleep(1000);
    }

    public void selectNumericFilterOption(String... filterTitles) {
        for (String filterTitle : filterTitles) {

            // Находим элемент-заголовок фильтра по тексту (например, "Мощность двигателя" или "Объём двигателя")
            SelenideElement titleElement = $$("div.horizontal-filter-block__property-title").findBy(Condition.text(filterTitle));

            // Получаем родительский контейнер, в котором находятся заголовок и слайдер
            SelenideElement filterContainer = titleElement.parent();

            // Находим контейнер со значениями диапазона (например, "от 100 л.с." и "до 200 л.с." или "от 1498 см3" и "до 1598 см3")
            SelenideElement sliderValuesContainer = filterContainer.$("div.range-slider-values");

            // Извлекаем минимальное значение (левый блок)
            SelenideElement leftValueElement = sliderValuesContainer.$("div.range-slider-values__left");

            // Извлекаем максимальное значение (правый блок)
            SelenideElement rightValueElement = sliderValuesContainer.$("div.range-slider-values__right");

            // Получаем текст (например, "от 100 л.с.") и оставляем только цифры
            int minVal = Integer.parseInt(leftValueElement.getText().replaceAll("\\D+", ""));
            int maxVal = Integer.parseInt(rightValueElement.getText().replaceAll("\\D+", ""));

            // Генерируем случайное значение в диапазоне [minVal, maxVal]
            int randomValue = getRandomNumber(minVal, maxVal);

            // Находим элемент-слайдер (ползунок) внутри контейнера фильтра
            SelenideElement sliderButton = filterContainer.$("div.el-slider__button-wrapper .el-slider__button");

            // Находим трек слайдера, чтобы узнать его ширину
            SelenideElement sliderTrack = filterContainer.$("div.el-slider");
            int trackWidth = sliderTrack.getSize().getWidth();

            // Вычисляем, какую часть диапазона занимает randomValue
            double ratio = (randomValue - minVal) / (double) (maxVal - minVal);

            // Рассчитываем целевое смещение ползунка в пикселях от левого края трека
            int targetOffset = (int) Math.round(ratio * trackWidth);

            // Определяем текущее смещение ползунка (из атрибута style, например "left: 25%;")
            int currentOffset = getCurrentSliderOffset(sliderButton, trackWidth);

            // Вычисляем разницу между целевым и текущим смещением
            int deltaOffset = targetOffset - currentOffset;

            // Перемещаем ползунок с использованием Selenium Actions
            actions().moveToElement(sliderButton).clickAndHold().moveByOffset(deltaOffset, 0).release().build().perform();

            // Ждем обновления интерфейса
            sleep(1000);

            // Сохраняем выбранное случайное значение в хранилище FilterStore
            if (filterTitle.equals("Мощность двигателя")) {
                filterStore.setEnginePower(String.valueOf(randomValue));
            } else if (filterTitle.equals("Объём двигателя")) {
                filterStore.setEngineDisplacement(String.valueOf(randomValue));
            } else {
                System.out.println("Неизвестный числовой фильтр: " + filterTitle);
            }
        }
    }
    private int getRandomNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }


    public void clickShowAllOffers() {
        // Прокручиваем страницу так, чтобы нижняя часть кнопки оказалась видна
        showAllOffersButton.scrollIntoView(false);
        showAllOffersButton.shouldBe(Condition.visible, Condition.enabled).click();
    }

    public FilterStore getFilterStore() {
        return filterStore;
    }
    private void scrollToElementWithOffset(SelenideElement element, int offset) {
        int yCoordinate = element.getLocation().getY() - offset;
        // На всякий случай не скроллим в отрицательные координаты
        if (yCoordinate < 0) {
            yCoordinate = 0;
        }
        executeJavaScript("window.scroll(0, arguments[0]);", yCoordinate);
    }
}
