package setup;

import client.IngredientClient;
import client.OrderClient;
import client.UserClient;
import lombok.SneakyThrows;
import pojo.Order;
import pojo.User;

public class Setup {

    protected OrderClient orderClient;
    protected IngredientClient ingrClient;
    protected Order order;
    protected static UserClient userClient;
    protected static User user;
    protected static String accessToken;
    protected int expStatusCode;

    @SneakyThrows
    public static void registerTestUser() {
        userClient = new UserClient();
        user = User.createRandom();
        userClient.register(user)
                .then()
                .statusCode(200);
        Thread.sleep(2000); //пришлось добавить из-за постоянных 429 Too Many Requests
    }

    public void setUpOrder(String accessToken) {
        ingrClient = new IngredientClient();
        orderClient = new OrderClient();
        order = Order.random(ingrClient.getIngredients(), 3);
        orderClient.createWithAuth(accessToken, order)
                .then()
                .statusCode(200);
    }

    public void deleteUser() {
        if (accessToken != null) {
            userClient.delete(accessToken)
                    .then()
                    .statusCode(202);
        }
        accessToken = null;
    }
}
