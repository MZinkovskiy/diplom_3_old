import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertTrue;

public class RegistrTest {
    private WebDriver driver;

    @Before
    public void setup() {
        driver = WebDriverFactory.getWebDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
    }

    @After
    public void browserClose() {
        driver.quit();
    }

    @Step("Жмем «Личный кабинет» на главной странице")
    public void clickPersonalAccount() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();
    }

    @Step("Жмем «Зарегистрироваться» на странице «Личный кабинет»")
    public void clickRegistr() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegistrButton();
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    public void newUserTrue() {
        String nameUser = randomAlphabetic(12);
        String emailUser = randomAlphabetic(8) + "@yandex.ru";
        String passwordUser = randomAlphabetic(10);

        clickPersonalAccount();
        clickRegistr();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillingRegistrForm(nameUser, emailUser, passwordUser);
    }

    @Test
    @DisplayName("Попытка регистрации пользователя с некорректным паролем")
    public void newUserPasswordErrorFalse() {
        String nameUser = randomAlphabetic(12);
        String emailUser = randomAlphabetic(8) + "@yandex.ru";
        String passwordUser = randomAlphabetic(5);

        clickPersonalAccount();
        clickRegistr();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.fillingRegistrForm(nameUser, emailUser, passwordUser);

        assertTrue(registerPage.findElementPaswordErrorText());
    }

}
