/*Получение заказов конкретного пользователя:
авторизованный пользователь,
неавторизованный пользователь.*/

package order;

import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import setup.Setup;

import static org.hamcrest.Matchers.*;

@DisplayName("Получение заказов конкретного пользователя")
public class OrdersGetTest extends Setup {

    private static final String UNAUTHORIZED_401 = "You should be authorised";

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void shouldGetWithAuth() {
        registerTestUser();
        accessToken = userClient.getAccessToken(user);
        setUpOrder(accessToken);
        expStatusCode = 200;
        orderClient.getWithAuth(accessToken)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("orders", hasSize(greaterThan(0)));
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void shouldNotGetWithoutAuth() {
        expStatusCode = 401;
        orderClient = new OrderClient();
        orderClient.getWithoutAuth()
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(UNAUTHORIZED_401));
    }

    @After
    public void deleteData() {
        deleteUser();
        order = null;
    }

}
