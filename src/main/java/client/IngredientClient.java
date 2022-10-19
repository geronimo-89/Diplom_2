package client;

import io.qameta.allure.Step;
import pojo.IngredientData;
import pojo.Ingredients;

import java.util.ArrayList;
import java.util.List;

public class IngredientClient extends BaseClient {


    @Step("Получение доступных id ингредиентов")
    public List<String> getIngredients() {
        List<String> indgredientIds = new ArrayList<>();
        Ingredients ingr = getSpec()
                .when()
                .get(INGREDIENTS)
                .body()
                .as(Ingredients.class);
        for (IngredientData data : ingr.getData())
            indgredientIds.add(data.getId());
        return indgredientIds;
    }
}
