package com.example.alergin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import com.example.alergin.*;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.SubCategoryViewHolder> {
    private List<SubCategory> subCategories;
    private OnCategoryClickListener listener;

    public SubCategoriesAdapter(List<SubCategory> subCategories, OnCategoryClickListener listener) {
        this.subCategories = subCategories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_block, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        SubCategory subCategory = subCategories.get(position);
        holder.textView.setText(subCategory.getName());
        Glide.with(holder.imageView.getContext()).load(subCategory.getImageUrl()).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCategoryClick(subCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public static class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageViewCategory);
        }
    }
}
