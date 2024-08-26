package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;

    private By nameField = By.xpath("//fieldset[1]/div/div/input");
    private By emailField = By.xpath("//fieldset[2]/div/div/input");
    private By passwordField = By.xpath("//fieldset[3]/div/div/input");
    private By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private By loginButton = By.xpath("//a[text() = 'Войти']");
    private By paswordErrorText = By.xpath("//p[text()='Некорректный пароль']");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickRegisterButton() {
        driver.findElement(registerButton).click();
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step("Заполнение полей регистрации пользователя")
    public void fillingRegistrForm(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    @Step("Проверка наличия элемента «Некорректный пароль»")
    public boolean findElementPaswordErrorText() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(paswordErrorText));
        WebElement element = driver.findElement(paswordErrorText);
        if (element != null) {
            return true;
        } else {
            return false;
        }
    }
}
