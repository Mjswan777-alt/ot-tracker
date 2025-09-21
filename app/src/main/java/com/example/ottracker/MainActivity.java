package com.example.ottracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * Main activity for the OT tracker app. Displays the weekly OT tracker,
 * shows a roster of workers with their overtime counts, and provides
 * buttons to assign overtime, toggle opt-out status, log work for the
 * current day, and view OT history. The app rotates through the list
 * of workers to determine who should be assigned overtime next. When
 * a worker marks that they have worked, their overtime count is
 * incremented and the index moves on to the next worker. Opted-out
 * workers are skipped during assignment.
 */
public class MainActivity extends AppCompatActivity {

    private TextView rosterView, summaryView;
    private Button assignButton, optOutButton, workedButton, historyButton;

    // List of workers in the overtime rotation
    private ArrayList<Worker> workers;
    // Index of the current worker in line for overtime
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rosterView = findViewById(R.id.rosterView);
        summaryView = findViewById(R.id.summaryView);
        assignButton = findViewById(R.id.assignButton);
        optOutButton = findViewById(R.id.optOutButton);
        workedButton = findViewById(R.id.workedButton);
        historyButton = findViewById(R.id.historyButton);

        // Initialize a demo crew. In a real app this list would
        // likely come from a database or configuration screen.
        workers = new ArrayList<>();
        workers.add(new Worker("Mike"));
        workers.add(new Worker("John"));
        workers.add(new Worker("Dave"));
        workers.add(new Worker("Chris"));

        // Update the UI to reflect the initial state of the roster
        updateUI();

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignNextInLine();
            }
        });

        optOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOptOut();
            }
        });

        workedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markWorked();
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the history activity (not fully implemented)
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Assigns overtime to the next eligible worker in the rotation. Skips
     * workers that have opted out. Updates the summary view to display
     * the assignment. Does not increment overtime counts.
     */
    private void assignNextInLine() {
        int tries = workers.size();
        for (int i = 0; i < tries; i++) {
            Worker w = workers.get(currentIndex);
            if (!w.isOptOut()) {
                summaryView.setText("Assigned OT to " + w.getName());
                return;
            }
            moveToNext();
        }
        // If everyone is opted out, inform the user
        summaryView.setText("No eligible workers available");
    }

    /**
     * Toggles the opt-out status of the current worker and updates the UI.
     */
    private void toggleOptOut() {
        Worker w = workers.get(currentIndex);
        w.setOptOut(!w.isOptOut());
        updateUI();
    }

    /**
     * Marks the current worker as having worked overtime today, increments
     * their count, moves the rotation to the next worker, and updates the UI.
     */
    private void markWorked() {
        Worker w = workers.get(currentIndex);
        w.addOvertime();
        moveToNext();
        updateUI();
    }

    /**
     * Advances the currentIndex to the next worker in the list, wrapping
     * around to 0 when the end is reached.
     */
    private void moveToNext() {
        currentIndex++;
        if (currentIndex >= workers.size()) currentIndex = 0;
    }

    /**
     * Updates the roster view to show the current state of each worker,
     * including whether they are next in line, opted out, and their OT count.
     */
    private void updateUI() {
        StringBuilder roster = new StringBuilder();
        for (int i = 0; i < workers.size(); i++) {
            Worker w = workers.get(i);
            roster.append(w.getName());
            if (i == currentIndex) roster.append(" \u23ED");
            if (w.isOptOut()) roster.append(" \u1F6D1");
            roster.append(" | OT: ").append(w.getOvertimeCount()).append("\n");
        }
        rosterView.setText(roster.toString());
    }
}
