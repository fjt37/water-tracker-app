package jaytappen.watertracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = new Manager(this);

        final TextView statusDisp = findViewById(R.id.statusDisp);
        updateStatus();

        final Button decButton = findViewById(R.id.decButton);
        decButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                manager.incCount();
                updateStatus();
            }
        });

        final Button resetCountButton = findViewById(R.id.resetCountButton);
        resetCountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                manager.resetCount();
                updateStatus();
            }
        });
    }

    private void updateStatus (){
        // update text
        final TextView statusDisp = findViewById(R.id.statusDisp);
        if (manager.getCount() == 0) {
            statusDisp.setText("Goal: " + manager.getGoal());
        } else if (manager.getCount() < manager.getGoal()) {
            statusDisp.setText(manager.getCount()+ " down,\n" + manager.getRemaining() + " to go");
        } else { // if goalMet()
            statusDisp.setText("You're all set for today!\n Nice job :)");
        }

        // update button
        final Button decButton= findViewById(R.id.decButton);
        if (manager.goalMet()) {
            decButton.setVisibility(View.INVISIBLE);
        } else {
            decButton.setVisibility(View.VISIBLE);
        }
    }

}
