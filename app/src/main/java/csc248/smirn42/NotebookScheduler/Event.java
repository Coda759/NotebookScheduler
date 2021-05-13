package csc248.smirn42.NotebookScheduler;

public class Event {
    private String eventName;
    private int EventColor;
    private boolean checked;
    private int bookId;

    public Event(String eventName, int eventColor, boolean checked, int bookId) {
        this.eventName = eventName;
        this.EventColor = eventColor;
        this.checked = checked;
        this.bookId = bookId;
    }


    public boolean isChecked() {
        return checked;
    }

    public String getEventName() {
        return eventName;
    }


    public int getEventColor() {
        return EventColor;
    }

    public int getBookId() {
        return bookId;
    }

}
