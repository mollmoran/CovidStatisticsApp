package com.example.is4448project.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.is4448project.Models.Hero;
import com.example.is4448project.R;

import java.util.ArrayList;
import java.util.List;

public class GetAllHeroesAdapter extends RecyclerView.Adapter<GetAllHeroesAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Hero> herolist;
    private ArrayList<Hero> herolistCopy = new ArrayList<>();
    private static final String TAG = "*GetAllHeroesAdapter*";


    // pass object maybe?
    public GetAllHeroesAdapter(Context mContext, ArrayList<Hero> herolist) {
        this.mContext = mContext;
        this.herolist = herolist;
        herolistCopy.addAll(herolist);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_getallheros_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hero h = herolist.get(position);
        holder.name.setText(h.getName());
        holder.realname.setText("Real Name: " + h.getRealname());
        int stars = Integer.parseInt(h.getRating());
        holder.ratingBar.setRating(stars);
        Log.d(TAG, "stars = " + stars);
        holder.teamaff.setText("Team Affiliation: " + h.getTeamaffiliation());
    }

    @Override
    public int getItemCount() {
        if (herolist == null) return 0;
        return herolist.size();

    }

    public void filterList(ArrayList<Hero> filteredList) {
        herolist = filteredList;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView realname;
        TextView teamaff;
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            realname = itemView.findViewById(R.id.realname);
            teamaff = itemView.findViewById(R.id.teamaff);
            ratingBar = itemView.findViewById(R.id.ratingBar);

        }
    }



    }


