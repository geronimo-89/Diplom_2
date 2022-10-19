package pojo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientData {

    @Getter
    @Setter
    @JsonProperty("_id")
    private String id;

    public IngredientData() {
    }

    public IngredientData(String id) {
        this.id = id;
    }

}
