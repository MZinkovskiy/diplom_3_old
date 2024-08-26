package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private WebDriver driver;

    private By logo = By.xpath("//div[contains(@class, 'logo')]");
    private By headOrder = By.xpath("//a[text()='Профиль']");
    private By logoutButton = By.xpath("//button[text()='Выход']");
    private By constructorButton = By.xpath("//p[text()='Конструктор']");

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Нажимаем на логотип")
    public void clickLogo() {
        driver.findElement(logo).click();
    }

    @Step("Нажимаем на кнопку выхода")
    public void clickLogoutButton() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
        driver.findElement(logoutButton).click();
    }

    @Step("Нажимаем на конструктор")
    public void clickConstructorButton() {
        driver.findElement(constructorButton).click();
    }

    @Step("Проверка наличия элемента «Профиль»")
    public boolean findElementHeadOrder() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(headOrder));
        WebElement element = driver.findElement(headOrder);
        if (element != null) {
            return true;
        } else {
            return false;
        }
    }
}
