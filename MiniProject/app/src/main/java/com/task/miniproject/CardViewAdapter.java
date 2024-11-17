package com.task.miniproject;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MatchViewHolder> {
    private final List<Match> matches;
    private Context context;


    public CardViewAdapter(List<Match> matches, Context context) {
        this.matches = matches;
        this.context = context;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.name.setText("Name: " + match.getUser().getName());
        holder.contact.setText("Contact: " + match.getUser().getContact());
        holder.age.setText("Age: " + match.getUser().getAge());
        holder.email.setText("Email: " + match.getUser().getEmail());
        holder.score.setText("Score: " + match.getScore());

        // Add click listener to the card
        holder.itemView.setOnClickListener(v -> {
            // Handle the card click
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);

            String clickedUserName = match.getUser().getName();
            Toast.makeText(v.getContext(), "Clicked: " + clickedUserName, Toast.LENGTH_SHORT).show();
            Intent next=new Intent(context, OnetoOneChat.class);
            DbConnect dbConnect = new DbConnect(context);
            SQLiteDatabase db = dbConnect.getReadableDatabase();
            Matching m = new Matching(db);
            User curUser = m.getUserDetails(username);
            Log.d("sender",curUser.getName());
            Log.d("receiver",clickedUserName);
            next.putExtra("receiver",clickedUserName);
            next.putExtra("sender",curUser.getName());
            Log.d("intent","yes");
            context.startActivity(next);
            Log.d("start","yes");
            // Optionally, start a new activity or perform another action
            // Intent intent = new Intent(v.getContext(), UserDetailsActivity.class);
            // intent.putExtra("userName", clickedUserName);
            // v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView name, contact, age, email, score;

        public MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            contact = itemView.findViewById(R.id.contact);
            age = itemView.findViewById(R.id.age);
            email = itemView.findViewById(R.id.email);
            score = itemView.findViewById(R.id.score);
        }
    }
}
