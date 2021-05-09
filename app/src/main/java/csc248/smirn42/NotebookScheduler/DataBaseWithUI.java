package csc248.smirn42.NotebookScheduler;

import android.content.Context;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class DataBaseWithUI {

    private static Context context;

    public DataBaseWithUI(Context context) {
        this.context = context;
    }

    public static ArrayList<Event> ListOfEvents(String day, String month, String year) {


        DataBaseHelper helper = new DataBaseHelper(context);
        List<Note> notes = helper.getNotes(day + "-" + month + "-" + year);
        ArrayList<Event> events = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            //if there is no color
            Event event = new Event(note.getNoteText(), Color.RED, note.isCompleted(), note.getBookId());
            if (note.getDueDate().equals(day + "-" + month + "-" + year))
                events.add(event);
        }
        return events;
    }

    public ArrayList<String> ListOfDatesHasEvents() {
        ArrayList<String> dates = new ArrayList<>();
        DataBaseHelper helper = new DataBaseHelper(context);
        List<Note> notes = helper.getNotes();
        for (int i = 0; i < notes.size(); i++) {
            String date = notes.get(i).getDueDate();
            dates.add(date);
        }
        return dates;
    }

    public List<Note> getNotes() {
        DataBaseHelper helper = new DataBaseHelper(context);
        return helper.getNotes();
    }
}

