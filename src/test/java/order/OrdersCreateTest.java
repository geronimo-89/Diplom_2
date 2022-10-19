package order;

import client.IngredientClient;
import client.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Order;
import setup.Setup;

import static org.hamcrest.Matchers.*;

@DisplayName("Создание заказа")
public class OrdersCreateTest extends Setup {
    private static final String NO_ID_400 = "Ingredient ids must be provided";
    private static final String WRONG_ID_400 = "One or more ids provided are incorrect";
    private String hash;

    @Before
    public void setUp() {
        ingrClient = new IngredientClient();
        orderClient = new OrderClient();
        order = Order.random(ingrClient.getIngredients(), 3);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией, с ингредиентами")
    public void shouldOrderWithAuth() {
        registerTestUser();
        accessToken = userClient.getAccessToken(user);
        expStatusCode = 200;
        orderClient.createWithAuth(accessToken, order)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("order.number", notNullValue())
                .and()
                .body("order.owner.email", is(user.getEmail().toLowerCase()));
    }

    @Test
    @DisplayName("Создание заказа без авторизации, с ингредиентами")
    public void shouldOrderWithoutAuth() {
        expStatusCode = 200;
        orderClient.createWithoutAuth(order)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("success", is(true))
                .and()
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void shouldNotOrderWithoutIngr() {
        expStatusCode = 400;
        orderClient.createWithoutIngr()
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(NO_ID_400));
    }

    @Test
    @DisplayName("Создание заказа с невалидным хешем ингредиентов")
    public void shouldNotCreateWithInvalidHash() {
        expStatusCode = 400;
        hash = "000000000000000000000000";
        order = Order.withHash(hash);
        orderClient.createWithoutAuth(order)
                .then()
                .assertThat()
                .statusCode(expStatusCode)
                .and()
                .body("message", is(WRONG_ID_400));
    }

    @Test
    @DisplayName("Создание заказа с неверным форматом хеша ингредиентов")
    public void shouldNotCreateWithWrongHash() {
        expStatusCode = 500;
        hash = "abcde";
        order = Order.withHash(hash);
        orderClient.createWithoutAuth(order)
                .then()
                .assertThat()
                .statusCode(expStatusCode);
    }

    @After
    public void deleteData() {
        //документация не описывает способа удаления тестового заказа из базы
        order = null;
    }


}
