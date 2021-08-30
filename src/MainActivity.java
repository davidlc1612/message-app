package com.example.reddit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
{
    private RecyclerView rV;
    private RecyclerView.Adapter adapt;
    private ArrayList<Post> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rV = findViewById(R.id.viewer);
        rV.setHasFixedSize(true);
        rV.addItemDecoration(new DividerItemDecoration(rV.getContext(), DividerItemDecoration.VERTICAL));
        rV.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list = new ArrayList<>();
        adapt = new PostViewAdapter(this, list);
        rV.setAdapter(adapt);
        FirebaseDatabase.getInstance().getReference("Posts").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                list.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()) {list.add(d.getValue(Post.class));}
                list = sortPostList(list);
                adapt = new PostViewAdapter(MainActivity.this, list);
                rV.setAdapter(adapt);
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDelete((PostViewAdapter)adapt));
                itemTouchHelper.attachToRecyclerView(rV);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private ArrayList<Post> sortPostList(ArrayList<Post> l)
    {
        ArrayList<Post> children = new ArrayList<>();
        for(int i = l.size() - 1; i >= 0; i--)
        {
            Post p = l.get(i);
            if(!p.getParent().equals("")) {children.add(p); l.remove(i);}
        }
        Collections.sort(l, new Post.Comparing());
        while(children.size() > 0)
        {
            ArrayList<Post> same = new ArrayList<>();
            String parent = children.get(children.size() - 1).getParent();
            same.add(children.get(children.size() - 1));
            children.remove(children.size() - 1);
            for(int i = children.size() - 1; i >= 0; i--) {if(children.get(i).getParent().equals(parent)) {same.add(children.get(i)); children.remove(i);}}
            Collections.sort(same, new Post.Comparing());
            for(int i = 0; i < l.size(); i++)
            {
                if(l.get(i).getKey().equals(parent))
                {
                    for(int j = 0; j < same.size(); j++) {l.add(i + j + 1, same.get(j));}
                    break;
                }
            }
        }
        return l;
    }

    public void toPostWriter(View v)
    {
        startActivityForResult(new Intent(this, PostWriter.class).putExtra("pkey", ""), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1) {((PostViewAdapter)adapt).add(new Post(data.getStringExtra("author"), data.getStringExtra("message"), data.getStringExtra("pkey")));}
    }
}