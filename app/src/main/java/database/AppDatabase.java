package database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

import objects.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "app_recipes_db.db";
    private static AppDatabase database;
    private static Recipe recipe = new Recipe();

    public static AppDatabase getInstance(final Context context){
        synchronized (AppDatabase.class) {
            if (database == null) {
                database = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return database;
    }

    public abstract RecipeDao recipeDao();
}
