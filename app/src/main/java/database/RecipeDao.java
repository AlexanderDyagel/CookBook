package database;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Set;

import io.reactivex.Completable;
import objects.Recipe;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    Recipe getRecipeById(int recipeId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Update
    void updateRecipe(Recipe recipe);

    @Query("DELETE FROM recipes")
    void deleteAllRecipe();
}
