package csc248.smirn42.NotebookScheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<Event> events;
    private CheckboxClickListener listener;

    public EventsAdapter(Context context, ArrayList<Event> events, CheckboxClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.events = events;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.event_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position) {
        Event event = events.get(position);

        //boolean checked = event.isChecked();
        String titleOfElement = event.getEventName();
        holder.eventTitle.setText(titleOfElement);
        holder.checkBox.setChecked(event.isChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onClick(position, isChecked);
            }
        });

        holder.itemView.setBackgroundColor(holder.itemView.getContext().getResources().getColor(event.getEventColor()));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView eventTitle;
        CheckBox checkBox;

        ViewHolder(View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.element_title);
            checkBox = itemView.findViewById(R.id.checkbox_);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
}

