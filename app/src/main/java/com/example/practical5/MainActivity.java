package com.example.practical5;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // The same key is used in Municipality.java to read the extra
    public static final String EXTRA_MUNICIPALITY = "extra_municipality";

    private TextProcessor textProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textProcessor = new TextProcessor();

        EditText editTextMunicipality = findViewById(R.id.editTextMunicipality);

        // Handle the keyboard Enter (Done / Go) action
        editTextMunicipality.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                String input = editTextMunicipality.getText().toString();
                String validated = textProcessor.processMunicipality(input);

                if (validated == null) {
                    // Invalid or empty input: show an error and stay on this screen
                    editTextMunicipality.setError("Invalid municipality. Please enter Beijing, Shanghai, Tianjin or Chongqing.");
                    return true;
                }

                // Valid: pass the normalised name to the second Activity
                Intent intent = new Intent(MainActivity.this, Municipality.class);
                intent.putExtra(EXTRA_MUNICIPALITY, validated);
                startActivity(intent);
                return true;
            }
            return false;
        });
    }
}
