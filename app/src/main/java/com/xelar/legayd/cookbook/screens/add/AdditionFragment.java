package com.xelar.legayd.cookbook.screens.add;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xelar.legayd.cookbook.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import objects.Recipe;
import utils.StorageDir;
import utils.Utils;

import static android.app.Activity.RESULT_OK;


public class AdditionFragment extends Fragment {
    private static final int PERMISSION_REQUEST = 24;
    private ImageView imageMainRecipe;
    private ImageButton btnAddIngredient;
    private ImageButton btnSaveRecipe;
    private ImageButton btnCameraAction;
    private ImageButton btnStorageAction;
    private ImageButton btnDeleteDescription;
    private EditText etTitleRecipe;
    private EditText etIngredientEdit;
    private EditText etDescription;
    private LinearLayout ingredientsBlock;
    private int id = 0;
    private Uri photoURI;
    private AdditionViewModel viewModel;
    private Recipe recipe;
    private Set<String> ingredients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_addition_recipe, container, false);
        initComponents(view);
        viewModel = ViewModelProviders.of(this).get(AdditionViewModel.class);
        recipe = new Recipe();
        ingredients = new LinkedHashSet();

        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsBlock.addView(generateIngredientRow(view.getContext()));
            }
        });
        btnSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < ingredientsBlock.getChildCount(); i++) {
                    LinearLayout row = (LinearLayout) ingredientsBlock.getChildAt(i);
                    for (int j = 0; j < row.getChildCount(); j++) {
                        int idIng = row.getChildAt(j).getId();
                        if (idIng >= 0) {
                            EditText editText = (EditText) row.getChildAt(j);
                            ingredients.add(editText.getText().toString());
                        }
                    }
                }
               validateData(recipe, etTitleRecipe.getText().toString(), photoURI, ingredients, etDescription.getText().toString());
            }
        });
        btnCameraAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromCamera();
            }
        });
        btnStorageAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromStorage();
            }
        });
        btnDeleteDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etDescription.getText().clear();
            }
        });

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }
        return view;
    }

    private void initComponents(@NotNull View view) {
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);
        etTitleRecipe = view.findViewById(R.id.etTitleRecipe);
        etIngredientEdit = view.findViewById(R.id.etIngredientEdit);
        etIngredientEdit.setId(id++);
        etDescription = view.findViewById(R.id.etDescription);
        ingredientsBlock = view.findViewById(R.id.ingredientsBlock);
        btnSaveRecipe = view.findViewById(R.id.btnSaveRecipe);
        btnCameraAction = view.findViewById(R.id.btnCameraAction);
        btnStorageAction = view.findViewById(R.id.btnStorageAction);
        imageMainRecipe = view.findViewById(R.id.imageMainRecipe);
        btnDeleteDescription = view.findViewById(R.id.btnDeleteDescription);
    }

    // Генерация строки ингредиента
    private LinearLayout generateIngredientRow(Context context) {
        final LinearLayout row = new LinearLayout(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(params);
        row.setOrientation(LinearLayout.HORIZONTAL);

        ImageButton btnClearIngredient = new ImageButton(context);
        btnClearIngredient.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnClearIngredient.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_circle_outline));
        btnClearIngredient.setBackgroundColor(Color.TRANSPARENT);

        row.addView(btnClearIngredient);

        final EditText ingredient = new EditText(context);
        ingredient.setId(id++);
        ingredient.setLayoutParams(params);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ingredient.getLayoutParams();
        int start = getResources().getDimensionPixelSize(R.dimen.marginLeft_ingredient);
        marginLayoutParams.setMarginStart(start);
        ingredient.setInputType(InputType.TYPE_CLASS_TEXT);
        row.addView(ingredient);

        btnClearIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredients.remove(ingredient.getText().toString());
                ingredientsBlock.removeView(row);
            }
        });

        return row;
    }

    // Получение картинки из приложений
    private void getImageFromStorage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.stringIntentChooser)), Utils.IMAGE_REQUEST_CODE);
    }

    // Получение картинки от камеры
    private void getImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // проверяем, что есть приложение способное обработать интент
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // создать файл для фотографии
            File photoFile = null;
            StorageDir storageDir = new StorageDir();
            try {
                photoFile = storageDir.createImageFile(getActivity());
            } catch (IOException e) {
                Toast.makeText(getActivity(), getString(R.string.error_create_file_image), Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.provider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(intent, Utils.CAMERA_REQUEST_CODE);
            }
        } else {
            btnCameraAction.setEnabled(false);
            Toast.makeText(getActivity(), getString(R.string.toast_camera_not_exists), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Utils.IMAGE_REQUEST_CODE: {
                    photoURI = intent.getData();
                    imageMainRecipe.setImageURI(photoURI);
                    break;
                }
                case Utils.CAMERA_REQUEST_CODE: {
                    imageMainRecipe.setImageURI(photoURI);
                    break;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Utils.log("Есть доступ");
            } else {
                Utils.log("Нет доступа");
            }
            return;
        }
    }

    private void validateData(Recipe recipe, String title, Uri uri, Set<String> ings, String body){
        if(title.isEmpty() || body.isEmpty() || ings.isEmpty() || uri == null) {
            Toast.makeText(getContext(), "Не все данные для сохранения", Toast.LENGTH_SHORT).show();
        } else {
            recipe.setTitle(title);
            recipe.setUriImage(photoURI.toString());
            List<String> temps = new ArrayList<String>(ings);
            recipe.setIngredients(temps);
            recipe.setBody(etDescription.getText().toString());
//            viewModel.insertData(recipe);
            for(String ingr : recipe.getIngredients())
                Utils.log(ingr);
            Utils.log(recipe.getUriImage());
            Utils.log(recipe.getBody());
        }
    }
}
