package com.example.ottracker;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * A simple history screen that will eventually display past weeks' OT
 * assignments. Currently shows a placeholder message. This activity
 * illustrates how to add a secondary screen to the app.
 */
public class HistoryActivity extends AppCompatActivity {

    private TextView historyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        historyView = findViewById(R.id.historyView);
        historyView.setText("History feature coming soon — weekly log of OT assignments.");
    }
}
