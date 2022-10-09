/*Логин пользователя:
логин под существующим пользователем,
логин с неверным логином и паролем.*/

package user;

import client.UserClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;

import static org.hamcrest.Matchers.is;

@DisplayName("Логин пользователя")
public class AuthLoginTest {

    private User user;
    private String accessToken;
    private UserClient userClient;
    private int expStatusCode;
    private static final String INCORRECT_202 = "email or password are incorrect";

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.createRandom();
        userClient.register(user)
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Можно залогиниться существующим пользователем")
    public void shouldLoginValidUser() {
        expStatusCode = 200;
        userClient.login(user)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("success", is(true));
        accessToken = userClient.getAccessToken(user);
    }

    @Test
    @DisplayName("Нельзя залогиниться с неверным логином и паролем")
    public void shouldNotLoginIncorrectCredentials() {
        expStatusCode = 401;
        accessToken = userClient.getAccessToken(user);
        user.setPassword(User.generateRandomString());
        userClient.login(user)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(INCORRECT_202));
    }

    @After
    public void deleteUser() {
        userClient.delete(accessToken)
                    .then()
                    .statusCode(202);
        }

}
