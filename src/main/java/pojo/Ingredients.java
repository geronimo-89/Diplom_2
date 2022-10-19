package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Ingredients {

    @Getter @Setter
    private Boolean success;
    @Getter @Setter
    private List<IngredientData> data;

    public Ingredients() {
    }

    public Ingredients(Boolean success, List<IngredientData> data) {
        this.success = success;
        this.data = data;
    }

}
