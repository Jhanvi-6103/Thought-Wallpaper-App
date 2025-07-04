package com.jtthought.thoughtwallpaperapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class CustomThoughtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_thought);

        EditText etThought = findViewById(R.id.et_custom_thought);
        Button btnSaveThought = findViewById(R.id.btn_save_thought);

        btnSaveThought.setOnClickListener(v -> {
            String thought = etThought.getText().toString().trim();
            if (!thought.isEmpty()) {
                Intent intent = new Intent(this, PreviewActivity.class);
                intent.putExtra("thought", thought);
                startActivity(intent);
            }
        });
    }
}
