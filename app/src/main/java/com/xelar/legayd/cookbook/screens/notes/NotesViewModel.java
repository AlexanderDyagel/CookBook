package com.xelar.legayd.cookbook.screens.notes;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import database.AppDatabase;
import objects.Recipe;

public class NotesViewModel extends AndroidViewModel {
    private static AppDatabase db;
    private LiveData<List<Recipe>> recipes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        recipes = db.recipeDao().getAllRecipes();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
