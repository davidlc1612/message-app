package com.example.reddit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class PostViewAdapter extends RecyclerView.Adapter
{
    private ArrayList<Post> list;
    private Context context;
    private DatabaseReference ref;

    public PostViewAdapter(Context c, ArrayList<Post> l)
    {
        list = l;
        context = c;
        ref = FirebaseDatabase.getInstance().getReference("Posts");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.posts, parent, false), this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
    {
        if(list.get(position).getParent().equals(""))
        {
            ((PostViewHolder)holder).authorText.setText(list.get(position).getAuthor() + "     ");
            ((PostViewHolder)holder).messageText.setText(list.get(position).getMsg());
            ((PostViewHolder)holder).votesText.setText((list.get(position).getVotes()) + " VOTES");
        }
        else
        {
            ((PostViewHolder)holder).authorText.setText("     " + list.get(position).getAuthor() + "     ");
            ((PostViewHolder)holder).messageText.setText("     " + list.get(position).getMsg());
            ((PostViewHolder)holder).votesText.setText((list.get(position).getVotes()) + " VOTES");
        }
    }

    @Override
    public int getItemCount() {return list.size();}

    public void add(Post p)
    {
        p.setKey(ref.push().getKey());
        ref.child(p.getKey()).setValue(p);
        list.add(p);
        notifyDataSetChanged();
    }

    public void remove(int i)
    {
        try
        {
            ArrayList<Integer> remove = new ArrayList<>();
            for(int p = 0; p < list.size(); p++) {if(list.get(p).getParent().equals(list.get(i).getKey())) {remove.add(p);}}
            remove.add(i);
            Collections.sort(remove, Collections.<Integer>reverseOrder());
            for(int x : remove) {ref.child(list.get(x).getKey()).removeValue(); list.remove(x);}
        }
        catch(IndexOutOfBoundsException e) {}
        notifyDataSetChanged();
    }

    public void sendUpVote(int i)
    {
        try
        {
            Post p = list.get(i);
            p.upVote();
            ref.child(p.getKey()).setValue(p);
        }
        catch(IndexOutOfBoundsException e) {}
        notifyDataSetChanged();
    }

    public void startReply(int i, String a, String m)
    {
        if(list.get(i).getParent().equals("")) {((MainActivity)context).startActivityForResult(new Intent((MainActivity)context, PostWriter.class).putExtra("pkey", list.get(i).getKey()).putExtra("author", a).putExtra("message", m), 1);}
    }
}
