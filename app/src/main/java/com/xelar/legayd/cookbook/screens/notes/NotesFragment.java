package com.xelar.legayd.cookbook.screens.notes;

import android.os.Bundle;

import adapters.NoteRecipeAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import objects.Recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xelar.legayd.cookbook.R;
import com.xelar.legayd.cookbook.screens.add.AdditionFragment;

import java.util.List;

public class NotesFragment extends Fragment {
    private static final String TAG = "add";
    private FloatingActionButton fabAddRecipe;
    private NotesViewModel viewModel;
    private RecyclerView recyclerView;
    private NoteRecipeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_recipes,container, false);
        initComponents(view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NoteRecipeAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(NotesViewModel.class);

        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                adapter.setRecipes(recipes);
            }
        });

        fabAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdditionFragment additionFragment = new AdditionFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, additionFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        //.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right)
                        .commit();
            }
        });
        return view;
    }

    private void initComponents(View view){
        recyclerView = view.findViewById(R.id.recyclerNotes);
        fabAddRecipe = view.findViewById(R.id.fabAddRecipe);
    }
}
