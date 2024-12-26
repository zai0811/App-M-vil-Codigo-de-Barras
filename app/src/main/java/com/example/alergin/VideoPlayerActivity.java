package com.example.alergin;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Recibir el nombre de la subcategoría
        String subCategoryName = getIntent().getStringExtra("subCategoryName");

        // Mostrar el nombre de la subcategoría en el TextView
        TextView comidaTextView = findViewById(R.id.comidaId);
        comidaTextView.setText(subCategoryName);  // Asignar el nombre de la subcategoría al TextView

        // Recibir el código de la imagen
        String imageCode = getIntent().getStringExtra("imageCode");
        ImageView imageView = findViewById(R.id.imagenSubcategory);

        // Obtener el recurso de la imagen usando el código de la imagen
        int imageResourceId = getResources().getIdentifier(imageCode, "drawable", getPackageName());
        if (imageResourceId != 0) {
            imageView.setImageResource(imageResourceId);  // Mostrar la imagen de la subcategoría
        } else {
            imageView.setImageResource(R.drawable.ic_launcher_foreground);  // Imagen predeterminada si no se encuentra la imagen
        }

        String videoUrl = getIntent().getStringExtra("videoUrl");
        Log.d("VideoPlayerActivity", "Received Video URL: " + videoUrl);

        // Convertir la URL de YouTube a su versión de incrustación (embed)
        String embedUrl = videoUrl != null ? videoUrl.replace("watch?v=", "embed/") : "";

        // Verificar si el video URL es válido antes de cargarlo
        if (embedUrl != null && !embedUrl.isEmpty()) {
            // Habilitar contenido mixto (http y https) en WebView
            WebView webView = findViewById(R.id.webView);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            }

            String videoHtml =
                    "<iframe width=\"100%\" height=\"100%\" src=\"" + embedUrl + "\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadData(videoHtml, "text/html", "utf-8");
        } else {
            Log.e("VideoPlayerActivity", "No video URL provided");
        }

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onBackPressed());
    }
}

