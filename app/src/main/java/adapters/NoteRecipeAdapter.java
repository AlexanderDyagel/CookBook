package adapters;

import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xelar.legayd.cookbook.R;

import java.util.List;

import objects.Recipe;

public class NoteRecipeAdapter extends RecyclerView.Adapter<NoteRecipeAdapter.ViewHolder> {
    private List<Recipe> recipes;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note_recipe, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgNoteRecipe;
        private TextView tvTitleNoteRecipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNoteRecipe = itemView.findViewById(R.id.imgNoteRecipe);
            tvTitleNoteRecipe = itemView.findViewById(R.id.tvTitleNoteRecipe);
        }
        private void bind(int position){
            Recipe recipe = recipes.get(position);
            imgNoteRecipe.setImageURI(Uri.parse(recipe.getUriImage()));
            tvTitleNoteRecipe.setText(recipe.getTitle());
        }
    }
}
