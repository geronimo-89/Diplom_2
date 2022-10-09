package client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import pojo.Order;

public class OrderClient extends BaseClient {

    @Step("Заказ без авторизации")
    public Response withoutAuth(Order order) {
        Response response = getSpec()
                .and()
                .body(order)
                .when()
                .post(ORDERS);
        addToReport(order, response);
        return response;
    }

    @Step("Заказ без ингредиентов")
    public Response withoutIngr() {
        Response response = getSpec()
                .when()
                .post(ORDERS);
        addToReport(response);
        return response;
    }

    @Step("Заказ с авторизацией")
    public Response withAuth(String accessToken, Order order) {
        Response response = getSpec()
                .header("Authorization", accessToken)
                .and()
                .body(order)
                .when()
                .post(ORDERS);
         addToReport(order, response);
         return response;
    }

}
