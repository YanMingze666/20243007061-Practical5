package com.example.practical5;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Municipality extends AppCompatActivity {

    // Use the same extra key as MainActivity
    public static final String EXTRA_MUNICIPALITY = MainActivity.EXTRA_MUNICIPALITY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_municipality);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView textViewName = findViewById(R.id.textViewName);
        ImageView imageView = findViewById(R.id.imageViewMunicipality);
        TextView textViewInfo = findViewById(R.id.textViewInfo);
        Button buttonVisit = findViewById(R.id.buttonVisit);

        // Retrieve the name passed from MainActivity and validate it safely
        String name = getIntent().getStringExtra(EXTRA_MUNICIPALITY);
        String validated = new TextProcessor().processMunicipality(name);
        if (validated == null) {
            // Handle missing / invalid data safely - fall back to a default
            validated = "Beijing";
        }

        String[] summaries = getResources().getStringArray(R.array.municipalities_array);

        int position;
        int colour;
        int imageRes;
        String url;

        switch (validated) {
            case "Shanghai":
                position = 1;
                colour = R.color.municipality_shanghai;
                imageRes = R.drawable.shanghai;
                url = "https://english.shanghai.gov.cn/";
                break;
            case "Chongqing":
                position = 2;
                colour = R.color.municipality_chongqing;
                imageRes = R.drawable.chongqing;
                url = "https://english.cq.gov.cn/";
                break;
            case "Tianjin":
                position = 3;
                colour = R.color.municipality_tianjin;
                imageRes = R.drawable.tianjin;
                url = "https://en.tj.gov.cn/";
                break;
            case "Beijing":
            default:
                position = 0;
                colour = R.color.municipality_beijing;
                imageRes = R.drawable.beijing;
                url = "https://english.beijing.gov.cn/";
                break;
        }

        // Update the UI with the selected municipality's resources
        textViewName.setText(validated);
        textViewName.setBackgroundColor(ContextCompat.getColor(this, colour));
        textViewName.setTextColor(ContextCompat.getColor(this, R.color.color_text_light));
        textViewInfo.setText(summaries[position]);
        textViewInfo.setTextColor(ContextCompat.getColor(this, colour));
        imageView.setImageResource(imageRes);
        buttonVisit.setBackgroundColor(ContextCompat.getColor(this, colour));
        buttonVisit.setTextColor(ContextCompat.getColor(this, R.color.color_text_light));

        // Implicit Intent: open the official website of the selected municipality
        final String websiteUrl = url;
        buttonVisit.setOnClickListener(v -> openWebsite(websiteUrl));
    }

    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // No application can open the URL - tell the user instead of crashing
            Toast.makeText(this, "No application is available to open this website.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
