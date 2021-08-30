package com.example.reddit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDelete extends ItemTouchHelper.SimpleCallback
{
    private PostViewAdapter postV;

    public SwipeToDelete(PostViewAdapter p)
    {
        super(0, ItemTouchHelper.LEFT);
        postV = p;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {return false;}

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {postV.remove(viewHolder.getAdapterPosition());}
}
