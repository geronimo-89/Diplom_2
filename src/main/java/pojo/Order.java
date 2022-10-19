package pojo;

import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order {

    public List<String> ingredients;

    public Order() {
    }

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Step("Генерим рандомный набор ингредиентов для запроса")
    public static Order random(List<String> availableIngr, int quantity) {
        Order order = new Order();
        order.ingredients = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            order.ingredients.add(availableIngr.get(random.nextInt(availableIngr.size())));
        }
        return order;
    }

    @Step("Генерим заказ с неверным хешем")
    public static Order withHash(String hash) {
        Order order = new Order();
        order.ingredients = new ArrayList<>();
        order.ingredients.add(hash);
        return order;
    }


}
