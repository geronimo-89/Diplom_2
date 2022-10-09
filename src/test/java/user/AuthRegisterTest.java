/*Создание пользователя:
создать уникального пользователя;
создать пользователя, который уже зарегистрирован;
создать пользователя и не заполнить одно из обязательных полей.*/

package user;

import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;
import setup.Setup;

import static org.hamcrest.Matchers.is;

@DisplayName("Создание пользователя")
public class AuthRegisterTest extends Setup {

    private static final String EXISTS_403 = "User already exists";
    private static final String REQUIRED_403 = "Email, password and name are required fields";

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Можно создать нового уникального пользователя")
    public void shouldRegisterUniqueUser() {
        expStatusCode = 200;
        user = User.createRandom();
        userClient.register(user)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("success", is(true));
        accessToken = userClient.getAccessToken(user);
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя, который уже зарегистрирован")
    public void shouldNotRegisterExistingUser() {
        expStatusCode = 403;
        user = User.createRandom();
        userClient.register(user)
                .then()
                .statusCode(200);
        accessToken = userClient.getAccessToken(user);
        userClient.register(user)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(EXISTS_403));
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя, если не заполнить email")
    public void shouldNotRegisterWithoutEmail() {
        expStatusCode = 403;
        user = User.withoutEmail();
        userClient.register(user)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(REQUIRED_403));
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя, если не заполнить password")
    public void shouldNotRegisterWithoutPassword() {
        expStatusCode = 403;
        user = User.withoutPassword();
        userClient.register(user)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(REQUIRED_403));
    }

    @Test
    @DisplayName("Нельзя зарегистрировать пользователя, если не заполнить name")
    public void shouldNotRegisterWithoutName() {
        expStatusCode = 403;
        user = User.withoutName();
        userClient.register(user)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(REQUIRED_403));
    }

    @After
    public void deleteData() {
        deleteUser();
    }

}
