package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private WebDriver driver;

    private By constructorHead = By.xpath("//h1[text() = 'Соберите бургер']");
    private By personalAccountButton = By.xpath("//p[text()='Личный Кабинет']");
    private By loginButton = By.xpath("//button[text()='Войти в аккаунт']");
    private By rollsButton = By.xpath("//div/span[text()='Булки']");
    private By saucesButton = By.xpath("//div/span[text()='Соусы']");
    private By fillingsButton = By.xpath("//div/span[text()='Начинки']");
    private By activeTab = By.xpath("//div[contains(@class, 'current')]/span");
    private By placeOrderButton = By.xpath("//button[text() = 'Оформить заказ']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickPersonalAccountButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(personalAccountButton));
        driver.findElement(personalAccountButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Нажимаем на вкладку «Булки»")
    public void clickRollsButton() {
        driver.findElement(rollsButton).click();
    }

    @Step("Нажимаем на вкладку «Соусы»")
    public void clickSaucesButton() {
        driver.findElement(saucesButton).click();
    }

    @Step("Нажимаем на вкладку «Начинки»")
    public void clickFillingsButton() {
        driver.findElement(fillingsButton).click();
    }

    @Step("Проверка наличия кнопки «Оформить заказ»")
    // Кнопка «Оформить заказ» есть только у авторизованного пользователя
    public boolean findButtonPlaceOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(placeOrderButton));
        WebElement element = driver.findElement(placeOrderButton);
        if (element != null) {
            return true;
        } else {
            return false;
        }
    }

    @Step("Проверка наличия заголовка «Соберите бургер» для конструктора")
    public boolean findConstructorHead() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(constructorHead));
        WebElement element = driver.findElement(constructorHead);
        if (element != null) {
            return true;
        } else {
            return false;
        }
    }

    @Step("Получение названия активной вкладки конструктора")
    public String activeTab() {
        String element = driver.findElement(activeTab).getText();
        return element;
    }
}
