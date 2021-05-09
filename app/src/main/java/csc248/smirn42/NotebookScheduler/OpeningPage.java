package csc248.smirn42.NotebookScheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class OpeningPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openingpage);
    }
    
        public void gotoAddNotebook(View view) {
            Intent intent = new Intent (this, NotebookHome.class);
            startActivity(intent);
        }

    public void gotoCalendarActivity(View view) {
        Intent intent = new Intent (this, CalendarActivity.class);

        startActivity(intent);
    }

    public void gotoSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);

    }
}
