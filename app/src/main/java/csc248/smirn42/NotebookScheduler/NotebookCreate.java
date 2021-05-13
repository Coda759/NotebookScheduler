package csc248.smirn42.NotebookScheduler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NotebookCreate extends AppCompatActivity {

    View selectedColor;
    RadioButton rBlue, rGreen, rYellow, rHotPink, rOrange, rCayan, rRed, rPurple, rWhite, rBlack, rGray, rBiege;
    boolean noteIsList = true;
    long selectedDate = 0;
    int color = R.color.blue;
    private DataBaseHelper dbhelper;
    DatePickerDialog.OnDateSetListener mDateListener;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_creation);

        dbhelper = new DataBaseHelper(this);

        Button cancelBtn = findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

        Button okBtn = findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNotebook();
            }
        });

        Button selectDateBTn = findViewById(R.id.select_date_btn);
        selectDateBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        NotebookCreate.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


            }
        });

        mDateListener = (datePicker, year, month, day) -> {
            Calendar c1 = GregorianCalendar.getInstance();
            c1.set(year, month, day, 0, 0, 0);

            selectedDate = c1.getTimeInMillis();
        };
    }


    public void cancel() {
        Intent intent = new Intent(this, NotebookHome.class);
        startActivity(intent);
    }

    public void checkInput(View view) {
        RadioButton note = findViewById(R.id.note_selection);

        if (note.isChecked()) {
            noteIsList = false;
        } else {
            noteIsList = true;
        }
        System.out.println(noteIsList);
    }

    public boolean isList() {
        return noteIsList;
    }

    public void selectedColor(View view) {
        rBlue = findViewById(R.id.radio_blue);
        rGreen = findViewById(R.id.radio_green);
        rYellow = findViewById(R.id.radio_yellow);
        rHotPink = findViewById(R.id.radio_hot_pink);
        rOrange = findViewById(R.id.radio_orange);
        rCayan = findViewById(R.id.radio_cayan);
        rRed = findViewById(R.id.radio_red);
//        rPurple = findViewById(R.id.radio_purple);
//        rWhite = findViewById(R.id.radio_white);
//        rBlack = findViewById(R.id.radio_black);
//        rGray = findViewById(R.id.radio_gray);
//        rBiege = findViewById(R.id.radio_biege);
        //default color;
        selectedColor = rBlue;

        selectedColor = view;
        switch (selectedColor.getId()) {
            case R.id.radio_blue:
                System.out.println("Blue selected");
                color = R.color.blue;
                break;
            case R.id.radio_green:
                System.out.println("Green selected");
                color = R.color.green;
                break;
            case R.id.radio_yellow:
                System.out.println("Yellow selected");
                color = R.color.yellow;
                break;
            case R.id.radio_hot_pink:
                System.out.println("Hot pink selected");
                color = R.color.hot_pink;
                break;
            case R.id.radio_orange:
                System.out.println("Orange selected");
                color = R.color.orange;
                break;
            case R.id.radio_cayan:
                System.out.println("Cayan selected");
                color = R.color.cayan;
                break;
            case R.id.radio_red:
                System.out.println("Red selected");
                color = R.color.red;
                break;
//            case R.id.radio_purple: System.out.println("Purple selected"); color = "purple";
//            break;
//            case R.id.radio_white: System.out.println("White selected"); color = "white";
//            break;
//            case R.id.radio_black: System.out.println("Black selected"); color = "black";
//            break;
//            case R.id.radio_gray: System.out.println("Gray selected"); color = "gray";
//            break;
//            case R.id.radio_biege: System.out.println("Biege selected"); color = "biege";
//            break;
            default:
                System.out.println("Blue selected");
                color = R.color.blue;
        }
    }

    public void newNotebook() {

// Commented out, database should not be accessed directly - DF
//        SQLiteDatabase database = dbhelper.getReadableDatabase();

        EditText notebookNameInput = findViewById(R.id.notebook_name);

        String notebookName = notebookNameInput.getText().toString();

        if (notebookName.matches("")) {
            Toast.makeText(this, "Notebook name can't be empty", Toast.LENGTH_SHORT).show();
        } else if (selectedDate == 0) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
        } else {

// Added proper addNotebook method - DF
            Notebook notebook = new Notebook(-1, notebookName, color, "", isList());
            boolean success = dbhelper.addNotebook(notebook);
            if (!success) {
                Toast.makeText(this, "Notebook already exists!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notebook " + notebookName + " created", Toast.LENGTH_SHORT).show();
                notebookNameInput.getText().clear();
                addNote(notebookName);
            }


//Commented out, database should not be accessed directly
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(dbhelper.COLUMN_NOTEBOOK_NAME, notebookName);
//            contentValues.put(dbhelper.COLUMN_IS_LIST, isList());
//            contentValues.put(dbhelper.COLUMN_NOTEBOOK_COLOR, color);
//
//            database.insert(dbhelper.NOTEBOOK_TABLE, null, contentValues);
//            dbhelper.close();

//            Toast.makeText(this, "Notebook " + notebookName + " created", Toast.LENGTH_SHORT).show();
//            notebookNameInput.getText().clear();

        }
    }

    private void addNote(String notebookName) {
        Note note = new Note(-1,
                dbhelper.notebookNameToNotebookId(notebookName),
                "",
                String.valueOf(selectedDate),
                false);
        boolean successN = dbhelper.addNote(note);
    }
}
