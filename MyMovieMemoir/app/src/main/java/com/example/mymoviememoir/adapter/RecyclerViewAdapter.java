package com.example.mymoviememoir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.R;
import com.example.mymoviememoir.entity.MovieSearchEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {
    private ArrayList<MovieSearchEntity> mMovieSearchList;
    private onNoteListerner monNoteListerner;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // ViewHolder should contain variables for all the views in each row of the
        public TextView titleTextView;
        public TextView yearTextView;
        public ImageView imageView;
        onNoteListerner onNoteListerner1;
        // a constructor that accepts the entire View (itemView)
        // provides a reference and access to all the views in each row
        public ViewHolder(View itemView, onNoteListerner onNoteListerner1) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.recTitle);
            yearTextView = itemView.findViewById(R.id.recYear);
            imageView=itemView.findViewById(R.id.recImage);
            this.onNoteListerner1 = onNoteListerner1;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListerner1.onNoteClick(getAdapterPosition());
        }
    }

    public RecyclerViewAdapter(ArrayList<MovieSearchEntity> MovieSearchList, onNoteListerner onNoteListerner1) {
        mMovieSearchList = MovieSearchList;
        this.monNoteListerner = onNoteListerner1;
    }
    @Override
    public int getItemCount() {
        return mMovieSearchList.size();
    }

    public void clear() {
        int size = mMovieSearchList.size();
        mMovieSearchList.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the view from an XML layout file
        View unitsView = inflater.inflate(R.layout.rv_layout, parent, false);
        // construct the viewholder with the new view
        ViewHolder viewHolder = new ViewHolder(unitsView, monNoteListerner);
        return viewHolder;
    }
    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder,
                                 int position) {
        final MovieSearchEntity movieItem = mMovieSearchList.get(position);
        // viewholder binding with its data at the specified position
        TextView titleTextView= viewHolder.titleTextView;
        titleTextView.setText(movieItem.getMovieName());
        TextView yearTextView = viewHolder.yearTextView;
        yearTextView.setText(movieItem.getReleaseYear());
        Picasso.with(viewHolder.imageView.getContext()).load(movieItem.getPosterPath()).into(viewHolder.imageView);
    }

    public interface onNoteListerner{
        void onNoteClick(int position);

    }
}

