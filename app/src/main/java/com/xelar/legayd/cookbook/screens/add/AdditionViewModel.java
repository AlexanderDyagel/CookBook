package com.xelar.legayd.cookbook.screens.add;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import android.widget.Toast;

import database.AppDatabase;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import objects.Recipe;

public class AdditionViewModel extends AndroidViewModel {
    private static AppDatabase db;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public AdditionViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
    }

    public void insertData(Recipe recipe){
        insertRecipe(recipe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(getApplication(), "Данные сохранены в БД", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication(), "Ошибка сохранения в БД", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Completable insertRecipe(Recipe recipe) {
         return db.recipeDao().insertRecipe(recipe);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
