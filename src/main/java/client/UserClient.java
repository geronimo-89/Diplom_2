package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.User;

import static pojo.User.*;

public class UserClient extends BaseClient {

    private String accessToken;

    @Step("Регистрация нового пользователя")
    public Response register(User user) {
        Response response = getSpec()
                .and()
                .body(user)
                .when()
                .post(REGISTER);
        addToReport(user, response);
        return response;
    }

    @Step("Логин пользователя")
    public Response login(User user) {
        Response response = getSpec()
                .and()
                .body(loginData(user))
                .when()
                .post(LOGIN);
        addToReport(loginData(user), response);
        return response;
    }

    @Step("Получение access token (bearer) существующего пользователя")
    public String getAccessToken(User user) {
        return login(user)
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Удаление пользователя")
    public Response delete(User user) {
        accessToken = getAccessToken(user);
        return getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
    }

    @Step("Удаление пользователя по токену")
    public Response delete(String accessToken) {
        return getSpec()
                .header("Authorization", accessToken)
                .when()
                .delete(USER);
    }

    @Step("Изменение данных пользователя без авторизации")
    public Response updateWithoutAuth(User user) {
        Response response = getSpec()
                .and()
                .body(user)
                .when()
                .patch(USER);
        addToReport(user, response);
        return response;
    }

    @Step("Изменение данных пользователя с авторизацией")
    public Response update(User user, String accessToken) {
        Response response = getSpec()
                .header("Authorization", accessToken)
                .and()
                .body(user)
                .when()
                .patch(USER);
        addToReport(user, response);
        return response;
    }


}
