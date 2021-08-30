package com.example.reddit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PostWriter extends AppCompatActivity
{
    private String pKey;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_writer);
        pKey = getIntent().getStringExtra("pkey");
        TextView a = findViewById(R.id.parentPost1);
        TextView p = findViewById(R.id.parentPost2);
        if(!pKey.equals(""))
        {
            Button b = findViewById(R.id.postButton);
            b.setText("Reply");
            EditText m = findViewById(R.id.messageBox);
            m.setHint("New Reply (required)");
            a.setText("Replying to: " + getIntent().getStringExtra("author"));
            p.setText(getIntent().getStringExtra("message"));
        }
        else
        {
            a.setVisibility(View.GONE);
            p.setVisibility(View.GONE);
        }
    }

    public void add(View v)
    {
        EditText a = findViewById(R.id.nameBox);
        EditText m = findViewById(R.id.messageBox);
        String i = a.getText().toString();
        String j = m.getText().toString();
        if(i.length() > 0 && j.length() > 0) {setResult(1, new Intent().putExtra("author", i).putExtra("message", j).putExtra("pkey", pKey)); finish();}
    }

    public void back(View v) {finish();}
}