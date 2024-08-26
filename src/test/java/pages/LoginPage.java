package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    private By headOrder = By.xpath("//h2[text()='Вход']");

    private By emailField = By.xpath("//div[contains(@class,'input_type_text')]/input");
    private By passwordField = By.xpath("//div[contains(@class,'input_type_password')]/input");
    private By loginButton = By.xpath("//button[text()='Войти']");
    private By registrButton = By.xpath("//a[text()='Зарегистрироваться']");
    private By recoverPasswordButton = By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickRegistrButton() {
        driver.findElement(registrButton).click();
    }

    public void clickRecoverPasswordButton() {
        driver.findElement(recoverPasswordButton).click();
    }

    @Step("Заполнение полей авторизации пользователя")
    public void fillingLoginForm(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();
    }

    @Step("Проверка наличия элемента «Вход»")
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
