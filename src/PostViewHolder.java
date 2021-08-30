package com.example.reddit;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder
{
    public TextView authorText, messageText, votesText;
    public ConstraintLayout background;

    public PostViewHolder(@NonNull View itemView, final PostViewAdapter p)
    {
        super(itemView);
        authorText = itemView.findViewById(R.id.authorText);
        messageText = itemView.findViewById(R.id.messageText);
        votesText = itemView.findViewById(R.id.votesText);
        votesText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {p.sendUpVote(getAdapterPosition());}
        });
        background = itemView.findViewById(R.id.postings);
        background.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {p.startReply(getAdapterPosition(), authorText.getText().toString(), messageText.getText().toString());}
        });
    }
}
