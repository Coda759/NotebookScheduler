package csc248.smirn42.NotebookScheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DayView extends AppCompatActivity implements CheckboxClickListener {

    Button linkButton;
    TextView day_textView;
    ListView listView;
    RecyclerView rvEvents;
    ImageButton right;
    ImageButton Left;
    Long dateTimeMillis;
    String currentDay, currentMonth, currentYear, date;
    private DataBaseHelper dbhelper;
    private ArrayList<Event> selectedEvents;
    private ArrayList<Notebook> allNotebooks;
    private ArrayList<Note> allNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        linkButton = (Button) findViewById(R.id.linkButton);
        day_textView = findViewById(R.id.day);
        listView = findViewById(R.id.list_of_events);
        rvEvents = findViewById(R.id.rvEvents);
        right = findViewById(R.id.rightImage);
        Left = findViewById(R.id.leftImage);
        //get intent
        Intent comingIntent = getIntent();
        date = getComingIntentExtras(comingIntent);
        dateTimeMillis = comingIntent.getLongExtra("dateIndateFormat", 0);
        day_textView.setText(date);
        String[] str = date.split("-");
        currentDay = str[0];
        currentMonth = str[1];
        currentYear = str[2];
        dbhelper = new DataBaseHelper(this);
        DataBaseWithUI dataBaseWithUI = new DataBaseWithUI(this);

        allNotebooks = (ArrayList<Notebook>) dbhelper.getNotebooks();
        allNotes = (ArrayList<Note>) dataBaseWithUI.getNotes();

        getNotebooks(dateTimeMillis);
        getNotes();
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                Calendar c = Calendar.getInstance();
                try {
                    Date d = sdf.parse(date);
                    c.setTime(Objects.requireNonNull(d));
                    c.add(Calendar.DATE, 1);
                    date = sdf.format(c.getTime());
                    day_textView.setText(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                getNotebooks(c.getTimeInMillis());
            }
        });
        Left.setOnClickListener(v -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
            Calendar c = Calendar.getInstance();
            try {
                Date d = sdf.parse(date);
                c.setTime(Objects.requireNonNull(d));
                c.add(Calendar.DATE, -1);
                date = sdf.format(c.getTime());
                day_textView.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            getNotebooks(c.getTimeInMillis());
        });
        linkButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NotebookHome.class);
            startActivity(intent);
        });


    }

    private void getNotebooks(long dateTimeMillis) {
        selectedEvents = new ArrayList<>();
        ArrayList<Note> selectedNotes = new ArrayList<>();

        for (Note note : allNotes) {
            if (Utils.isValidTimeMillis(note.getDueDate())) {
                if (Long.parseLong(note.getDueDate()) / 1000 == dateTimeMillis / 1000) {
                    selectedNotes.add(note);
                }
            } else {
                if (note.getDueDate().contains("/")) {
                    long timemillis = Utils.getTimeMillis(note.getDueDate());
                    if (timemillis > 0) {
                        if (timemillis / 1000 == dateTimeMillis / 1000) {
                            selectedNotes.add(note);
                        }
                    }
                }
            }

        }

        for (Notebook allNotebook : allNotebooks) {
            for (Note selectedNote : selectedNotes) {
                if (selectedNote.getBookId() == allNotebook.getNotebookId()) {
                    selectedEvents.add(new Event(allNotebook.getNotebookName(), allNotebook.getNotebookColor(), selectedNote.isCompleted(), selectedNote.getBookId()));
                }
            }
        }

        fillListView();
    }

    private void getNotes() {
        DataBaseWithUI dataBaseWithUI = new DataBaseWithUI(this);
        System.out.println("");
    }


    private String getComingIntentExtras(Intent intent) {

        String dayOfMonth = intent.getStringExtra("date");
        return dayOfMonth;
    }

    private void fillListView() {

        EventsAdapter adapter = new EventsAdapter(DayView.this, selectedEvents, this);
        rvEvents.setAdapter(adapter);

    }

    @Override
    public void onClick(int position, boolean isChecked) {
        Note note = new Note();
        note.setNoteId(selectedEvents.get(position).getBookId());
        dbhelper.updateNoteIsCompleted(note, isChecked);
    }
}