package com.example.alergin;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<Category> categories;
    private OnCategoryClickListener listener;

    public interface OnCategoryClickListener {
        void onCategoryClicked(int categoryId, String categoryName);
    }

    public CategoriesAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button button;
        private ImageView imageViewCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            button = itemView.findViewById(R.id.button);
            imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
        }

        public void bind(final Category category, final OnCategoryClickListener listener) {
            textView.setText(category.getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCategoryClicked(category.getId(), category.getName());
                }
            });

            // Get the image resource ID from the context.
            Context context = itemView.getContext();
            int imageResourceId = context.getResources().getIdentifier(category.getImageCode(), "drawable", context.getPackageName());
            if (imageResourceId != 0) {
                imageViewCategory.setImageResource(imageResourceId);
            } else {
                imageViewCategory.setImageResource(R.drawable.logo); // Default image if not found
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_block, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(categories.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
