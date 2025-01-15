package com.example.alergin;

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

    private WebView webView;
    private ImageView imageView;
    private TextView comidaTextView;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        webView = findViewById(R.id.webView);
        imageView = findViewById(R.id.imagenSubcategory);
        comidaTextView = findViewById(R.id.comidaId);
        backButton = findViewById(R.id.backButton);

        String subCategoryName = getIntent().getStringExtra("subCategoryName");
        String videoUrl = getIntent().getStringExtra("videoUrl");

        if (videoUrl == null || videoUrl.isEmpty()) {
            Log.e("VideoPlayerActivity", "No se recibió la URL del video.");
            finish();
            return;
        }

        comidaTextView.setText(subCategoryName);

        // Configurar WebView con video embebido
        String embedUrl = videoUrl.replace("watch?v=", "embed/");
        String videoHtml = "<iframe width=\"100%\" height=\"100%\" src=\"" + embedUrl +
                "\" frameborder=\"0\" allow=\"autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadData(videoHtml, "text/html", "utf-8");

        backButton.setOnClickListener(v -> onBackPressed());
    }
}









