package csc248.smirn42.NotebookScheduler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class NotebookMenuRecyclerAdapter extends RecyclerView.Adapter<NotebookMenuRecyclerAdapter.CustomViewHolder> { //implements Filterable

    private List<Notebook> thumbnailList;
    private List<Notebook> thumbnailListFull;
    private NotebookClickListener notebookClickListener;
    private Context context;
    private DataBaseHelper notebookDB;
    private SQLiteDatabase sqLiteDatabase;
    private String thumbnailTitle, thumbnailColor;


    public NotebookMenuRecyclerAdapter(List<Notebook> notebookList, Context context) {
        this.thumbnailList = notebookList;
        this.context = context;
        thumbnailListFull = new ArrayList<>(thumbnailList);
    }

//    @Override
//    public Filter getFilter() {
//        return notebookFilter;
//    }

    @NonNull
    @Override
    public NotebookMenuRecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.notebook_thumbnail, parent, false);
        return new NotebookMenuRecyclerAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotebookMenuRecyclerAdapter.CustomViewHolder holder, int position) {
        holder.notebookBtn.setText(thumbnailList.get(position).getNotebookName());
        holder.notebookBtn.setBackgroundResource(thumbnailList.get(position).getNotebookColor());

        //Handler for notebook deletion
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("Delete " + thumbnailList.get(position).getNotebookName() + "?");

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notebookDB = new DataBaseHelper(context);
                        notebookDB.deleteNotebook(thumbnailList.get(position).getNotebookName());
                        thumbnailList.remove(position);
                        notifyDataSetChanged();
                        notifyItemRemoved(position);
                    }
                });
                builder.show();
            }
        });
        //Decide what type of notebook
        if(thumbnailList.get(position).isList()) {
            holder.notebookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), NoteList.class);
                    intent.putExtra("notebookName", thumbnailList.get(position).getNotebookName());
                    view.getContext().startActivity(intent);
                    System.out.println(thumbnailList.get(position).isList());
                }
            });
        } else {
            holder.notebookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), notes_example.class);
                    intent.putExtra("notebookName", thumbnailList.get(position).getNotebookName());
                    view.getContext().startActivity(intent);
                    System.out.println(thumbnailList.get(position).isList());
                }
            });
        }
        //set pic here maybe
        //set description here maybe
        //set due date here maybe
    }

    @Override
    public int getItemCount() {
        return thumbnailList.size();
    }

    public void setItemClickListener(NotebookClickListener notebookClickListener) {
        this.notebookClickListener = notebookClickListener;
    }

//    private Filter notebookFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//            List<Notebook> filteredList = new ArrayList<>();
//
//            if(charSequence == null || charSequence.length() == 0) {
//                filteredList.addAll(thumbnailListFull);
//            } else {
//                String filterPattern = charSequence.toString().toLowerCase().trim();
//                for(Notebook notebookThumbnail : thumbnailListFull) {
//                    if(notebookThumbnail.getNotebookName().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(notebookThumbnail);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            thumbnailList.clear();
//            thumbnailList.addAll((List) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button notebookBtn, deleteBtn;

        public CustomViewHolder(View view) {
            super(view);
            notebookBtn = view.findViewById(R.id.notebook_thumbnail_btn);
            deleteBtn = view.findViewById(R.id.delte_btn);
        }

        @Override
        public void onClick(View view) {
            if(notebookClickListener != null) {
                notebookClickListener.onClick(view, getAdapterPosition());
            }
        }
    }

//    public void getDetails(String details) {
//        notebookDB = new DataBaseHelper(context);
//        sqLiteDatabase = notebookDB.getReadableDatabase();
//        Cursor cursor = notebookDB.getNotebookDetails(details);
//        if(cursor.moveToFirst()) {
//            thumbnailTitle = cursor.getString(0);
//            cursor.close();
//            notebookDB.close();
//        } else {
//            Log.d("error ", "something went wrong");
//        }
//    }
}
