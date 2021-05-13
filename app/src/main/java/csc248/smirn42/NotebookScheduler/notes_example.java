package csc248.smirn42.NotebookScheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;


public class notes_example extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText notebookName, noteText, dateText;
    CheckBox dueDate;
    int day = 0, month = 0, year = 0;
    int savedDay, savedMonth, savedYear = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_example);

        notebookName = findViewById(R.id.notebookName);
        noteText = findViewById(R.id.noteText);
        dateText = findViewById(R.id.dateText);
        dueDate = findViewById(R.id.dueDate);

        //*************Provide a string for notebook name******************************
        // get notebook name from another activity
        Intent intent = getIntent();
        String str = intent.getStringExtra("notebookName");
        notebookName.setText(str);

        // create a database helper
        DataBaseHelper dataBaseHelper = new DataBaseHelper(notes_example.this);

        // get notebook Id from notebook Name
        Notebook noteBook = dataBaseHelper.getNotebooks(str).get(0);
        int noteBookId = noteBook.getNotebookId();

        // get note text and note due date from notebookId
        Note note = dataBaseHelper.getNotes(noteBookId).get(0);
        String NoteText = note.getNoteText();
        String NoteDue = note.getDueDate();

        // change the color of the notes according to notebook
        int color = noteBook.getNotebookColor();
        System.out.println(color);

        switch (color) {
            // light green
            case 2131034211:
                notebookName.setBackgroundColor(Color.parseColor("#5fd94c"));
                noteText.setBackgroundColor(Color.parseColor("#baf0af"));
                dateText.setBackgroundColor(Color.parseColor("#cdf2c7"));
                dueDate.setBackgroundColor(Color.parseColor("#cdf2c7"));
                break;

            // yellow
            case 2131034338:
                notebookName.setBackgroundColor(Color.parseColor("#ede737"));
                noteText.setBackgroundColor(Color.parseColor("#e8f29d"));
                dateText.setBackgroundColor(Color.parseColor("#faf8c3"));
                dueDate.setBackgroundColor(Color.parseColor("#faf8c3"));
                break;

            // red
            case 2131034317:
                notebookName.setBackgroundColor(Color.parseColor("#ed4c61"));
                noteText.setBackgroundColor(Color.parseColor("#fcb3bc"));
                dateText.setBackgroundColor(Color.parseColor("#fadce0"));
                dueDate.setBackgroundColor(Color.parseColor("#fadce0"));
                break;

            // pink
            case 2131034214:
                notebookName.setBackgroundColor(Color.parseColor("#f576d7"));
                noteText.setBackgroundColor(Color.parseColor("#facdec"));
                dateText.setBackgroundColor(Color.parseColor("#f5e4ef"));
                dueDate.setBackgroundColor(Color.parseColor("#f5e4ef"));
                break;

            // orange
            case 2131034302:
                notebookName.setBackgroundColor(Color.parseColor("#f57547"));
                noteText.setBackgroundColor(Color.parseColor("#f2c1b6"));
                dateText.setBackgroundColor(Color.parseColor("#fad2c5"));
                dueDate.setBackgroundColor(Color.parseColor("#fad2c5"));
                break;

            // cyan
            case 2131034162:
                notebookName.setBackgroundColor(Color.parseColor("#5ef7f2"));
                noteText.setBackgroundColor(Color.parseColor("#d3f5f4"));
                dateText.setBackgroundColor(Color.parseColor("#dff2f2"));
                dueDate.setBackgroundColor(Color.parseColor("#dff2f2"));
                break;
        }



        // set the notebook text and date from the database
        noteText.setText(NoteText);
        dateText.setText(NoteDue);

        if (! NoteDue.isEmpty()) {
            dueDate.setChecked(true);
        }

    }


    // get today's date
    public void getDate() {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    // pick current date in calendar
    public void pickCurrentDate(View view) {
        String date = dateText.getText().toString();
        if (date.isEmpty()) {
            dateText.setClickable(false);
        } else {
            String[] arr = date.split("/", 3);
            month = Integer.parseInt(arr[0]);
            day = Integer.parseInt(arr[1]);
            year = Integer.parseInt(arr[2]);
            new DatePickerDialog(this, this, year, month - 1, day).show();
        }
    }

    // pick today's date in calendar
    public void pickTodayDate(View view) {
        getDate();
        new DatePickerDialog(this, this, year, month, day).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        savedDay = dayOfMonth;
        savedMonth  = month + 1;
        savedYear = year;

        String date = savedMonth + "/" +  savedDay + "/" + savedYear;
        dateText.setText(date);

    }

    // check if the due date is checked or not, otherwise set empty string
    public void checkbox(View view) {
        if (dueDate.isChecked()) {
            pickTodayDate(view);
        } else {
            dateText.setText("");
        }
    }

    // save notes in the database
    public void save_notes() {
        // get the new edited text and new date from the note
        String newText = noteText.getText().toString();
        String newDate = dateText.getText().toString();


        // initialize database helper
        DataBaseHelper dataBaseHelper = new DataBaseHelper(notes_example.this);

        // get notebook Id from notebook Name
        Notebook noteBook = dataBaseHelper.getNotebooks(notebookName.getText().toString()).get(0);
        int noteBookId = noteBook.getNotebookId();

        // get note from corresponding notebookId
        Note note = dataBaseHelper.getNotes(noteBookId).get(0);

        // change the text and due date in note database
        boolean successT = dataBaseHelper.editNoteText(note, newText);
        boolean successD = dataBaseHelper.editNoteDate(note, newDate);

//        Note note = new Note(-1,
//                dataBaseHelper.notebookNameToNotebookId(notebookName.getText().toString()),
//                noteText.getText().toString(),
//                dateText.getText().toString(),
//                false);
//
//        boolean success = dataBaseHelper.addNote(note);

        // go back to homepage
        Intent intent = new Intent(this, NotebookHome.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // save everything when back button is pressed
        save_notes();
        super.onBackPressed();
    }

}