package com.xelar.legayd.cookbook.screens.add;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xelar.legayd.cookbook.R;

import org.jetbrains.annotations.NotNull;


public class AdditionFragment extends Fragment {
    private ImageButton btnAddIngredient;
    private Button btnSaveRecipe;
    private EditText etIngredientEdit;
    private EditText etDescription;
    private LinearLayout ingredientsBlock;
    private static final String TAG = "druid";
    private int id = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_addition_recipe, container, false);
        initComponents(view);
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsBlock.addView(generateIngredientRow(view.getContext()));
            }
        });
        btnSaveRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < ingredientsBlock.getChildCount(); i++){
                    LinearLayout row = (LinearLayout) ingredientsBlock.getChildAt(i);
                    for(int j = 0; j < row.getChildCount(); j++){
                        int idIng = row.getChildAt(j).getId();
                        if(idIng >= 0){
                            EditText editText = (EditText)row.getChildAt(j);
                            Log.d(TAG,editText.getText().toString());
                        }
                    }
                }
            }
        });
        return view;
    }

    private void initComponents(@NotNull View view){
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);
        etIngredientEdit = view.findViewById(R.id.etIngredientEdit);
        etIngredientEdit.setId(id++);
        etDescription = view.findViewById(R.id.etDescription);
        ingredientsBlock = view.findViewById(R.id.ingredientsBlock);
        btnSaveRecipe = view.findViewById(R.id.btnSaveRecipe);
    }

    private LinearLayout generateIngredientRow(Context context){
        final LinearLayout row = new LinearLayout(context);
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(params);
        row.setOrientation(LinearLayout.HORIZONTAL); // Ubuntu

        ImageButton btnClearIngredient = new ImageButton(context);
        btnClearIngredient.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btnClearIngredient.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        btnClearIngredient.setImageDrawable(getResources().getDrawable(android.R.drawable.presence_busy));
        btnClearIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientsBlock.removeView(row);
            }
        });

        row.addView(btnClearIngredient);

        EditText ingredient = new EditText(context);
        ingredient.setId(id++);
        ingredient.setLayoutParams(params);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ingredient.getLayoutParams();
        int start = getResources().getDimensionPixelSize(R.dimen.marginLeft_ingredient);
        marginLayoutParams.setMarginStart(start);
        row.addView(ingredient);

        return row;
    }
}
