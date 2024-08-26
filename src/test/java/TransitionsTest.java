import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.MainPage;
import pages.ProfilePage;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

public class TransitionsTest {
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
        accessToken = accessToken.substring(7, accessToken.length());
    }

    @Step("Удаление пользователя")
    public void deleteUser() {
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

    @Step("Заполняем поля и жмем «Войти» на странице авторизации")
    public void login() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillingLoginForm(emailUser, passwordUser);
    }

    @Step("Жмем «Личный кабинет» на главной странице")
    public void clickPersonalAccount() {
        mainPage = new MainPage(driver);
        mainPage.clickPersonalAccountButton();
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    public void transitionToPersonalAccount() {
        clickLoginButton();
        login();
        clickPersonalAccount();

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue(profilePage.findElementHeadOrder());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void transitionFromPersonalAccountToConstructor() {
        clickLoginButton();
        login();
        clickPersonalAccount();

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickConstructorButton();

        assertTrue(mainPage.findConstructorHead());
    }

    @Test
    @DisplayName("Переход из личного кабинета на логотип Stellar Burgers")
    public void transitionFromPersonalAccountOnLogo() {
        clickLoginButton();
        login();
        clickPersonalAccount();

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogo();

        assertTrue(mainPage.findConstructorHead());
    }

    @Test
    @DisplayName("Переход к разделу «Булки»")
    public void transitionToRolls() {
        mainPage = new MainPage(driver);
        // Поскольку вкладка «Булки» активна по дефолту, перейдем сначала на другую вкладку, а потому вернемся к Булкам
        mainPage.clickSaucesButton();
        mainPage.clickRollsButton();
        assertTrue(mainPage.activeTab().equals("Булки"));
    }

    @Test
    @DisplayName("Переход к разделу «Соусы»")
    public void transitionToSauces() {
        mainPage = new MainPage(driver);
        mainPage.clickSaucesButton();
        assertTrue(mainPage.activeTab().equals("Соусы"));
    }

    @Test
    @DisplayName("Переход к разделу «Начинки»")
    public void transitionToFillings() {
        mainPage = new MainPage(driver);
        mainPage.clickFillingsButton();
        assertTrue(mainPage.activeTab().equals("Начинки"));
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
