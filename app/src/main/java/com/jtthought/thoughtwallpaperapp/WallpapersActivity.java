package com.jtthought.thoughtwallpaperapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.GridView;
import android.widget.AdapterView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class WallpapersActivity extends AppCompatActivity {

    private ArrayList<Integer> predefinedWallpapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);

        GridView gridView = findViewById(R.id.grid_wallpapers);

        // Predefined wallpapers (list of image resources)
        predefinedWallpapers = new ArrayList<>();
        predefinedWallpapers.add(R.drawable.wallpaper1);
        predefinedWallpapers.add(R.drawable.wallpaper2);
        predefinedWallpapers.add(R.drawable.wallpaper3);
        predefinedWallpapers.add(R.drawable.wallpaper4);
        predefinedWallpapers.add(R.drawable.wallpaper5);
        predefinedWallpapers.add(R.drawable.wallpaper6);
        predefinedWallpapers.add(R.drawable.wallpaper7);
        predefinedWallpapers.add(R.drawable.wallpaper8);
        predefinedWallpapers.add(R.drawable.wallpaper9);
        predefinedWallpapers.add(R.drawable.wallpaper10);

        // Set the custom adapter to the GridView
        WallpaperAdapter adapter = new WallpaperAdapter(this, predefinedWallpapers);
        gridView.setAdapter(adapter);

        // Handle item click on predefined wallpapers
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedWallpaper = predefinedWallpapers.get(position);
                // Pass selected wallpaper and thought to PreviewActivity
                Intent intent = new Intent(WallpapersActivity.this, PreviewActivity.class);
                intent.putExtra("wallpaper", selectedWallpaper);
                intent.putExtra("thought", getIntent().getStringExtra("thought"));
                startActivity(intent);
            }
        });

        // Gallery selection logic
        findViewById(R.id.btn_select_gallery).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 101);  // Gallery selection code 101
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            Intent intent = new Intent(this, PreviewActivity.class);
            // Pass the selected image URI to PreviewActivity
            intent.putExtra("imageUri", selectedImageUri.toString());
            intent.putExtra("thought", getIntent().getStringExtra("thought"));
            startActivity(intent);
        }
    }
}
