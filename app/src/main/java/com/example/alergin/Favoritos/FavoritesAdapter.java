package com.example.alergin.Favoritos;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alergin.Producto.Product;
import com.example.alergin.R;

import java.util.List;


public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private final List<Product> favoritesList;

    public FavoritesAdapter(List<Product> favoritesList) {
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = favoritesList.get(position);
        holder.productName.setText(product.getName());
        holder.productIngredients.setText(product.getIngredients());
        holder.storeName.setText(product.getStore());
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productIngredients;
        TextView storeName;

        ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productIngredients = itemView.findViewById(R.id.product_ingredients);
            storeName = itemView.findViewById(R.id.store);
        }
    }
}
