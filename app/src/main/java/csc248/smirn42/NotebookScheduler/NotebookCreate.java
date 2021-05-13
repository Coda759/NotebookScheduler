package csc248.smirn42.NotebookScheduler;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NotebookCreate extends AppCompatActivity {

    private View selectedColor;
    private RadioButton rBlue,  rGreen, rYellow, rHotPink, rOrange, rCayan, rRed, rPurple, rWhite, rBlack, rGray, rBiege;
    private boolean noteIsList = false;
    private int color = R.color.blue;
    private DataBaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_creation);

        dbhelper = new DataBaseHelper(this);

        Button okBtn = findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNotebook();
                cancel();
            }
        });
    }

    public void cancel() {
        Intent intent = new Intent(this, NotebookHome.class);
        startActivity(intent);
    }

    public void checkInput(View view) {
        RadioButton note = findViewById(R.id.note_selection);
        if(note.isChecked()) {
            noteIsList = false;
        } else {
            noteIsList = true;
        }
        System.out.println(noteIsList);
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
        switch(selectedColor.getId()) {
            case R.id.radio_blue: System.out.println("Blue selected"); color = R.color.blue;
            break;
            case R.id.radio_green: System.out.println("Green selected"); color = R.color.green;
            break;
            case R.id.radio_yellow: System.out.println("Yellow selected"); color = R.color.yellow;
            break;
            case R.id.radio_hot_pink: System.out.println("Hot pink selected"); color = R.color.hot_pink;
            break;
            case R.id.radio_orange: System.out.println("Orange selected"); color = R.color.orange;
            break;
            case R.id.radio_cayan: System.out.println("Cayan selected"); color = R.color.cayan;
            break;
            case R.id.radio_red: System.out.println("Red selected"); color = R.color.red;
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
            default: System.out.println("Blue selected"); color = R.color.blue;
        }
    }

    public void newNotebook() {
        EditText notebookNameInput = findViewById(R.id.notebook_name);
        String notebookName = notebookNameInput.getText().toString();

        if(notebookName.matches("")) {
            Toast.makeText(this, "Notebook name can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            Notebook notebook = new Notebook(-1, notebookName, color, "", noteIsList);
            boolean success = dbhelper.addNotebook(notebook);
            System.out.println(noteIsList);
            if(!success){
                Toast.makeText(this, "Notebook already exists!", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Notebook " + notebookName + " created", Toast.LENGTH_SHORT).show();
                notebookNameInput.getText().clear();
            }
        }
        if (!noteIsList) {
            Note note = new Note(-1,
                    dbhelper.notebookNameToNotebookId(notebookName),
                    "",
                    "",
                    false);
            boolean successN = dbhelper.addNote(note);
        }
    }
}
