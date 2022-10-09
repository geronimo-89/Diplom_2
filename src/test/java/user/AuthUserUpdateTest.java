/*Изменение данных пользователя:
с авторизацией,
без авторизации,
Для обеих ситуаций нужно проверить, что любое поле можно изменить. Для неавторизованного пользователя — ещё и то, что система вернёт ошибку.*/

package user;

import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;
import setup.Setup;

import static org.hamcrest.Matchers.is;

@DisplayName("Изменение данных пользователя")
public class AuthUserUpdateTest extends Setup {

    private User userUpdData;
    private String accessToken;
    private static final String AUTHORIZED_401 = "You should be authorised";

    @Before
    public void setUp() {
        registerTestUser();
        accessToken = userClient.getAccessToken(user);
    }

    @Test
    @DisplayName("Можно изменить name, email пользователя с авторизацией по токену")
    public void shouldUpdateUserData() {
        expStatusCode = 200;
        userUpdData = User.withoutPassword();
        String expEmail = userUpdData.getEmail().toLowerCase();
        String expName = userUpdData.getName();
        userClient.update(userUpdData, accessToken)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("user.email", is(expEmail))
                .and()
                .body("user.name", is(expName));
    }

    @Test
    @DisplayName("Можно изменить password пользователя с авторизацией по токену")
    public void shouldUpdatePassword() {
        expStatusCode = 200;
        user.setPassword(User.generateRandomString());
        userClient.update(user, accessToken)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("success", is(true));
        userClient.login(user)
                .then()
                .statusCode(200);
        accessToken = userClient.getAccessToken(user);
    }

    @Test
    @DisplayName("Нельзя изменить данные пользователя без авторизации")
    public void shouldNotUpdateWithoutAuth() {
        expStatusCode = 401;
        userClient.updateWithoutAuth(user)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(AUTHORIZED_401));

    }

    @After
    public void deleteData() {
        deleteUser();
    }
}
