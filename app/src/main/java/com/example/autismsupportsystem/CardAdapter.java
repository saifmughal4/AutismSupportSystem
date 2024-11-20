package com.example.autismsupportsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.autismsupportsystem.CardItem;
import com.example.autismsupportsystem.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<CardItem> cardItemList;

    public CardAdapter(List<CardItem> cardItemList) {
        this.cardItemList = cardItemList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.container_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem item = cardItemList.get(position);

        holder.headerTitle.setText(item.getHeaderTitle());
        holder.bodyText.setText(item.getBodyText());
        holder.headerIcon.setImageResource(item.getHeaderImageResId());
        holder.bodyImage.setImageResource(item.getBodyImageResId());
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitle, bodyText;
        ImageView headerIcon, bodyImage;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            headerTitle = itemView.findViewById(R.id.headerTitle);
            bodyText = itemView.findViewById(R.id.bodyText);
            headerIcon = itemView.findViewById(R.id.headerIcon);
            bodyImage = itemView.findViewById(R.id.bodyImage);
        }
    }
}
