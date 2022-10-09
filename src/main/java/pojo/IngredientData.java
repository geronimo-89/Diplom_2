package pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientData {

    @Getter
    @Setter
    private String _id;

    public IngredientData() {
    }

    public IngredientData(String _id) {
        this._id = _id;
    }

}
