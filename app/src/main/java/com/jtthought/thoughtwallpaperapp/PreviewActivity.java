package com.jtthought.thoughtwallpaperapp;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.OutputStream;

public class PreviewActivity extends AppCompatActivity {

    private ImageView imgWallpaper;
    private TextView tvThought;
    private ImageView imgDownload;
    private Bitmap finalBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        imgWallpaper = findViewById(R.id.img_wallpaper);
        tvThought = findViewById(R.id.tv_thought);
        imgDownload = findViewById(R.id.img_download);

        // Get extras passed from the previous activity
        String thought = getIntent().getStringExtra("thought");
        String imageUri = getIntent().getStringExtra("imageUri");
        int wallpaperResId = getIntent().getIntExtra("wallpaper", 0);

        // Display the thought text
        if (thought != null) tvThought.setText(thought);

        // Display the selected wallpaper image
        if (imageUri != null) {
            imgWallpaper.setImageURI(Uri.parse(imageUri));
        } else if (wallpaperResId != 0) {
            imgWallpaper.setImageResource(wallpaperResId);
        }

        // Set click listener for the download icon
        imgDownload.setOnClickListener(v -> {
            // Combine the wallpaper and thought into a single bitmap
            finalBitmap = createCombinedBitmap();
            if (finalBitmap != null) {
                // Save the image to the gallery
                saveImageToGallery(PreviewActivity.this, finalBitmap);
            } else {
                Toast.makeText(PreviewActivity.this, "Error creating image", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to combine the wallpaper and thought into a single Bitmap
    private Bitmap createCombinedBitmap() {
        // Get the bitmap of the selected wallpaper
        imgWallpaper.setDrawingCacheEnabled(true);
        Bitmap wallpaperBitmap = Bitmap.createBitmap(imgWallpaper.getDrawingCache());
        imgWallpaper.setDrawingCacheEnabled(false);

        // Detect the wallpaper brightness and choose the appropriate text color
        int brightness = calculateBrightness(wallpaperBitmap);
        int textColor = (brightness < 128) ? Color.WHITE : Color.BLACK;  // Use white for dark backgrounds, black for light

        // Create a new bitmap with the same size as the wallpaper
        Bitmap combinedBitmap = Bitmap.createBitmap(wallpaperBitmap.getWidth(), wallpaperBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(combinedBitmap);

        // Draw the wallpaper image onto the canvas
        canvas.drawBitmap(wallpaperBitmap, 0, 0, null);

        // Set up the paint for drawing the thought text
        Paint paint = new Paint();
        paint.setColor(textColor); // Dynamic text color
        paint.setTextSize(50); // Text size (adjust as needed)
        paint.setAntiAlias(true); // Smooth the text edges
        paint.setShadowLayer(5f, 2f, 2f, Color.BLACK); // Text shadow for better visibility

        // Center the text on the screen
        float x = canvas.getWidth() / 2f - paint.measureText(tvThought.getText().toString()) / 2f;
        float y = canvas.getHeight() / 2f + tvThought.getTextSize() / 2f;

        // Draw the thought text on the canvas
        canvas.drawText(tvThought.getText().toString(), x, y, paint);

        return combinedBitmap;
    }

    // Method to calculate the brightness of the wallpaper
    private int calculateBrightness(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        long totalBrightness = 0;

        // Iterate over all pixels to calculate average brightness
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixelColor = bitmap.getPixel(x, y);
                int r = Color.red(pixelColor);
                int g = Color.green(pixelColor);
                int b = Color.blue(pixelColor);

                // Use luminance formula to calculate brightness
                totalBrightness += (0.299 * r + 0.587 * g + 0.114 * b);
            }
        }

        // Return average brightness of the image
        return (int) (totalBrightness / (width * height));
    }

    // Method to save the combined Bitmap to the gallery
    private void saveImageToGallery(Context context, Bitmap bitmap) {
        // Create a unique name for the image file
        String displayName = "ThoughtWallpaper_" + System.currentTimeMillis() + ".jpg";

        // Prepare the content values for inserting the image into the gallery
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, displayName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ThoughtWallpapers");

        // Insert the image into the MediaStore
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
                // Compress the bitmap and save it to the output stream
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

                // Notify the user that the image was saved successfully
                Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "Error saving image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
