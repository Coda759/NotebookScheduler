package csc248.smirn42.NotebookScheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;

public class NotebookHome extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotebookMenuRecyclerAdapter customRecyclerAdapter;
    private List<Notebook> notebookThumbnailList = new ArrayList<>();
    private DataBaseHelper notebookDB;
    NotebookClickListener listener;
    private DataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notebook_home_page);

        notebookDB = new DataBaseHelper(this);

        recyclerView = findViewById(R.id.notebook_container);
        customRecyclerAdapter = new NotebookMenuRecyclerAdapter(notebookDB.getNotebooks(), this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(customRecyclerAdapter);
        customRecyclerAdapter.setItemClickListener(listener);


        Button goToAddNotebookPage = findViewById(R.id.addNotebookBtn);
        goToAddNotebookPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotebook();
            }
        });

    }

    public void addNotebook() {
        Intent intent = new Intent(this, NotebookCreate.class);
        startActivity(intent);
    }

    public void gotoCalendar(View view) {
        Intent intent = new Intent (this, CalendarActivity.class);
        startActivity(intent);
    }

    public void gotoSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }



}