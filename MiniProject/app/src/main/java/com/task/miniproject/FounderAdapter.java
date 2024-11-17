package com.task.miniproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FounderAdapter extends RecyclerView.Adapter<FounderAdapter.FounderViewHolder> {

    private final List<Founder> founderList;

    public FounderAdapter(List<Founder> founderList) {
        this.founderList = founderList;
    }

    @NonNull
    @Override
    public FounderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.founders, parent, false);
        return new FounderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FounderViewHolder holder, int position) {
        Founder founder = founderList.get(position);
        holder.name.setText(founder.getName());
        holder.role.setText(founder.getRole());
        holder.image.setImageResource(founder.getImageResId());
    }

    @Override
    public int getItemCount() {
        return founderList.size();
    }

    static class FounderViewHolder extends RecyclerView.ViewHolder {
        TextView name, role;
        ImageView image;

        public FounderViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.founderName);
            role = itemView.findViewById(R.id.founderRole);
            image = itemView.findViewById(R.id.founderImage);
        }
    }
}
