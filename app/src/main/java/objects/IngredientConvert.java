package objects;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;

public class IngredientConvert {
    @TypeConverter
    public String fromIngredients(List<String> ingredients){
        StringBuilder sb = new StringBuilder();
        for(String ingredient : ingredients)
            sb.append(ingredient + "|&|");
        return sb.toString();
    }
    @TypeConverter
    public List<String> toIngredients(String ingredientsString){
        return Arrays.asList(ingredientsString.split("|&|"));
    }
}
