package com.jtthought.thoughtwallpaperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ThoughtsActivity extends AppCompatActivity {

    private ArrayList<String> thoughtsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thoughts);

        ListView listView = findViewById(R.id.list_thoughts);
        EditText etCustomThought = findViewById(R.id.et_custom_thought);
        Button btnAddThought = findViewById(R.id.btn_add_thought);

        // Predefined thoughts
        thoughtsList = new ArrayList<>();
        thoughtsList.add("Believe in yourself");
        thoughtsList.add("Stay positive");
        thoughtsList.add("Dream big");
        thoughtsList.add("Believe in yourself and all that you are.");
        thoughtsList.add("Your limitation—it's only your imagination.");
        thoughtsList.add("Dream it. Wish it. Do it");
        thoughtsList.add("Don’t stop when you’re tired. Stop when you’re done.");
        thoughtsList.add("Don’t wait for opportunity. Create it");
        thoughtsList.add("Do something today that your future self will thank you for");
        thoughtsList.add("Believe in the magic within you.");
        thoughtsList.add("Don’t count the days. Make the days count");
        thoughtsList.add("Hardships often prepare ordinary people for an extraordinary destiny");
        thoughtsList.add("The best way out is always through");
        thoughtsList.add("The only limit to our realization of tomorrow is our doubts of today.");
        thoughtsList.add("A goal without a plan is just a wish.");
        thoughtsList.add("Success is the sum of small efforts, repeated day in and day out");
        thoughtsList.add("Life is 10% what happens to us and 90% how we react to it.");
        thoughtsList.add("Success is not in what you have, but who you are");
        thoughtsList.add("Life is not measured by the number of breaths we take, but by the moments that take our breath away.");
        thoughtsList.add("The journey of a thousand miles begins with one step");

        // Set adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, thoughtsList);
        listView.setAdapter(adapter);

        // Add custom thought
        btnAddThought.setOnClickListener(v -> {
            String customThought = etCustomThought.getText().toString().trim();
            if (!customThought.isEmpty()) {
                thoughtsList.add(customThought);
                adapter.notifyDataSetChanged();
                etCustomThought.setText("");
            }
        });

        // On thought selection, navigate to WallpapersActivity
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedThought = thoughtsList.get(position);
            Intent intent = new Intent(ThoughtsActivity.this, WallpapersActivity.class);
            intent.putExtra("thought", selectedThought);
            startActivity(intent);
        });
    }
}
