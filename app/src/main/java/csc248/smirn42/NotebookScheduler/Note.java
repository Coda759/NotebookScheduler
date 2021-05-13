package csc248.smirn42.NotebookScheduler;

public class Note {
    private int noteId;
    private int bookId;
    private String noteText;
    private String dueDate;
    private boolean isCompleted;

    public Note(int noteId, int bookId, String noteText, String dueDate, boolean isCompleted) {
        this.noteId = noteId;
        this.bookId = bookId;
        this.noteText = noteText;
        this.dueDate = dueDate;
        this.isCompleted = isCompleted;
    }

    public Note() {
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", bookId=" + bookId +
                ", noteText='" + noteText + '\'' +
                ", dueDate=" + dueDate +
                ", isCompleted=" + isCompleted +
                '}';
    }
}

