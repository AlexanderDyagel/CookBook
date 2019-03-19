package objects;

import java.util.List;
import java.util.Set;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String uriImage;
    @TypeConverters({IngredientConvert.class})
    private List<String> ingredients;
    private String body;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
