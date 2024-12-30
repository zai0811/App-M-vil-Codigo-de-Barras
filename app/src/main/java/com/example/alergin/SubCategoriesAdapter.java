package com.example.alergin;
/*
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.SubCategoryViewHolder> {
    private List<SubCategory> subCategories;
    private OnClickListener listener;

    public SubCategoriesAdapter(List<SubCategory> subCategories, OnClickListener listener) {
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
*/


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriesAdapter extends RecyclerView.Adapter<SubCategoriesAdapter.SubCategoryViewHolder> {

    private List<SubCategory> subCategories;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(SubCategory subCategory);
    }

    public SubCategoriesAdapter(List<SubCategory> subCategories, Context context, OnItemClickListener onItemClickListener) {
        this.subCategories = subCategories;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        ImageView imageViewIcon;
        ImageButton btnPlayVideo;

        public SubCategoryViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textSubCategoryName);
            imageViewIcon = itemView.findViewById(R.id.imageViewComidaIcon);
            btnPlayVideo = itemView.findViewById(R.id.btnPlayVideo);

            btnPlayVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(subCategories.get(getAdapterPosition()));
                }
            });
        }
    }

    @Override
    public SubCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubCategoryViewHolder holder, int position) {
        SubCategory subCategory = subCategories.get(position);
        holder.textViewName.setText(subCategory.getName());

        String imageCode = subCategory.getImageCode();
        Log.d("SubCategoriesAdapter", "ImageCode: " + imageCode);

        int imageResourceId = context.getResources().getIdentifier(imageCode, "drawable", context.getPackageName());
        Log.d("SubCategoriesAdapter", "ImageResourceID: " + imageResourceId);

        if (imageResourceId != 0) {
            holder.imageViewIcon.setImageResource(imageResourceId);
        } else {
            holder.imageViewIcon.setImageResource(R.drawable.logo);
        }
    }

    @Override
    public int getItemCount() {

        return subCategories.size();
    }
    // Método para actualizar la lista de subcategorías
    public void setSubCategories(ArrayList<SubCategory> subCategories) {
        this.subCategories = subCategories;
        notifyDataSetChanged();  // Notificar al adaptador que los datos han cambiado
    }
}
