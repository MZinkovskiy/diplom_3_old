import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.*;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class LoginLogoutTest {
    private WebDriver driver;
    public final String BASE_URI = "https://stellarburgers.nomoreparties.site";

    String nameUser = randomAlphabetic(12);
    String emailUser = randomAlphabetic(8) + "@yandex.ru";
    String passwordUser = randomAlphabetic(10);
    String accessToken;

    MainPage mainPage;

    @Step("Создание пользователя")
    public void createUser() {
        accessToken = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .body("{\n" +
                        "    \"name\": \"" + nameUser + "\",\n" +
                        "    \"email\": \"" + emailUser + "\",\n" +
                        "    \"password\": \"" + passwordUser + "\"\n" +
                        "}")
                .when()
                .post("/api/auth/register")
                .path("accessToken");
    }

    @Step("Удаление пользователя")
    public void deleteUser() {
        accessToken = accessToken.substring(7, accessToken.length());

        given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .auth()
                .oauth2(accessToken)
                .when()
                .delete("/api/auth/user")
                .then()
                .body("success", is(true));
    }

    @Step("Жмем «Войти в аккаунт» на главной странице")
    public void clickLoginButton() {
        mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
    }

    @Step("Жмем «Личный кабинет» на главной странице")
    public void clickPersonalAccount() {
        mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();
    }

    @Step("Заполняем поля и жмем «Войти» на странице авторизации")
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillingLoginForm(emailUser, passwordUser);
    }

    @Test
    @DisplayName("Вход по кнопке «Войти в аккаунт» на главной")
    public void loginUserMainPage() {
        clickLoginButton();
        login();

        //Если пользователь авторизовался успешно - у него будет доступна кнопка «Оформить заказ»
        assertTrue(mainPage.findButtonPlaceOrder());
    }

    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    public void loginUserButtonPersonalAccount() {
        clickPersonalAccount();
        login();

        assertTrue(mainPage.findButtonPlaceOrder());
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    public void loginUserOnFormRegistr() {
        clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegistrButton();

        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginButton();

        login();

        assertTrue(mainPage.findButtonPlaceOrder());
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    public void loginUserOnFormRecoveryPassword() {
        clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRecoverPasswordButton();

        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage(driver);
        forgotPasswordPage.clickLoginButton();

        login();

        assertTrue(mainPage.findButtonPlaceOrder());
    }

    @Test
    @DisplayName("Выход по кнопке «Выйти» в личном кабинете")
    public void logoutUserFromPersonalAccount() {
        clickLoginButton();
        login();
        clickPersonalAccount();

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoutButton();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.findElementHeadOrder());
    }

    @Before
    public void setup() {
        driver = WebDriverFactory.getWebDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        createUser();
    }

    @After
    public void browserClose() {
        deleteUser();
        driver.quit();
    }

}
