package csc248.smirn42.NotebookScheduler;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import csc248.smirn42.NotebookScheduler.ListAdapters;
import csc248.smirn42.NotebookScheduler.ListModel;
import csc248.smirn42.NotebookScheduler.ListDataBaseHandler;


import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class NoteList extends AppCompatActivity implements ListDialogListener {

    private ListDataBaseHandler db;

    private RecyclerView tasksRecyclerView;
    private ListAdapters tasksAdapter;
    private Button addTask;
    private Button toCale;
    private Button clearAll;
    private List<ListModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);
        Objects.requireNonNull(getSupportActionBar()).hide();


        //click button to Calender page
        Button toChris= (Button) findViewById(R.id.toCal);

        toChris.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(new Intent(NoteList.this, CalendarActivity.class));
            }
        });
        //click button to delete all checked items from the page
//        Button toChris= (Button) findViewById(R.id.chrisPageB);
//
//        toChris.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                startActivity(new Intent(BarryActivity.this, ChrisActivity.class));
//            }
//        });



        db = new ListDataBaseHandler(this);
        db.openDatabase();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ListAdapters(db,NoteList.this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new ListTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        addTask = findViewById(R.id.addTask);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);

        tasksAdapter.setTasks(taskList);

        //add task button
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTask.newInstance().show(getSupportFragmentManager(), NewTask.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog){
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();
    }


}







