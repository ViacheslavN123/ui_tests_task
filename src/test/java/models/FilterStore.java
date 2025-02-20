package models;

/**
 * Класс FilterStore служит хранилищем выбранных значений фильтров и параметров автомобиля.
 */
public class FilterStore {
    // Параметры, выбранные через выпадающие меню
    private String city;          // Город
    private String brand;         // Марка
    private String model;         // Модель

    // Фильтры, выбранные через чекбоксы
    private String transmission;  // Например, "Коробка передач"
    private String driveType;     // Например, "Привод"
    private String fuelType;      // Например, "Тип топлива"
    private String bodyType;      // Например, "Кузов"
    private String color;         // Цвет автомобиля

    // Фильтр цены
    private int priceMin;         // Минимальное значение цены (из диапазона)
    private int priceMax;         // Максимальное значение цены (из диапазона)
    private int priceSelected;    // Выбранное значение цены

    // Фильтры, выбранные через чекбоксы для числовых параметров
    private String enginePower;         // Мощность двигателя (например, выбранная опция)
    private String engineDisplacement;  // Объём двигателя (например, выбранная опция)

    /**
     * Конструктор по умолчанию.
     */
    public FilterStore() {
    }

    // Геттеры и сеттеры для выпадающих меню
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    // Геттеры и сеттеры для фильтров через чекбоксы
    public String getTransmission() {
        return transmission;
    }
    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
    public String getDriveType() {
        return driveType;
    }
    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }
    public String getFuelType() {
        return fuelType;
    }
    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
    public String getBodyType() {
        return bodyType;
    }
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    // Геттеры и сеттеры для фильтра цены
    public int getPriceMin() {
        return priceMin;
    }
    public void setPriceMin(int priceMin) {
        this.priceMin = priceMin;
    }
    public int getPriceMax() {
        return priceMax;
    }
    public void setPriceMax(int priceMax) {
        this.priceMax = priceMax;
    }
    public int getPriceSelected() {
        return priceSelected;
    }
    public void setPriceSelected(int priceSelected) {
        this.priceSelected = priceSelected;
    }

    // Геттеры и сеттеры для числовых фильтров (например, мощность и объём двигателя)
    public String getEnginePower() {
        return enginePower;
    }
    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }
    public String getEngineDisplacement() {
        return engineDisplacement;
    }
    public void setEngineDisplacement(String engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    @Override
    public String toString() {
        return "FilterStore{" +
                "city='" + city + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", transmission='" + transmission + '\'' +
                ", driveType='" + driveType + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", bodyType='" + bodyType + '\'' +
                ", color='" + color + '\'' +
                ", priceMin=" + priceMin +
                ", priceMax=" + priceMax +
                ", priceSelected=" + priceSelected +
                ", enginePower='" + enginePower + '\'' +
                ", engineDisplacement='" + engineDisplacement + '\'' +
                '}';
    }
}
