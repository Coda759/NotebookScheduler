package csc248.smirn42.NotebookScheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;


public class NotesExampleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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


        // get notebook name from another activity
        Intent intent = getIntent();
        String str = intent.getStringExtra("notebookName");
        notebookName.setText(str);

        // create a database helper
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

        // get notebook Id from notebook Name
        Notebook noteBook = dataBaseHelper.getNotebooks(str).get(0);
        int noteBookId = noteBook.getNotebookId();

        // get note text and note due date from notebookId
        Note note = dataBaseHelper.getNotes(noteBookId).get(0);
        String NoteText = note.getNoteText();
        String NoteDue = note.getDueDate();


        // set the notebook text and date from the database
        noteText.setText(NoteText);
        dateText.setText(NoteDue);

        if (!NoteDue.isEmpty()) {
            dueDate.setChecked(true);
        }

    }

//    // go to calendar ( I don't think we need this)
//    public void goToCalendar(View view) {
//        Intent intent = new Intent(this, AddNotebook.class);
//        startActivity(intent);
//    }

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
        savedMonth = month + 1;
        savedYear = year;

        String date = savedMonth + "/" + savedDay + "/" + savedYear;
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
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);

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