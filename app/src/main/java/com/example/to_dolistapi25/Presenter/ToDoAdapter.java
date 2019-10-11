package com.example.to_dolistapi25.Presenter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import com.example.to_dolistapi25.Model.Item;
import com.example.to_dolistapi25.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    Context context;
    List<Item> list;

    public OnToDoAdapterItemClickListener onMyAdapterItemClickListener;

    public ToDoAdapter (Context context, List<Item> list, OnToDoAdapterItemClickListener onMyAdapterItemClickListener){
        this.list = list;
        this.context = context;
        this.onMyAdapterItemClickListener = onMyAdapterItemClickListener;
    }

    @NonNull
    @Override
    public ToDoAdapter.ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ToDoViewHolder holder, final int position) {

        holder.itemTitleTextView.setText(list.get(position).itemTitle);
        String myFormat = "EEE, MMM d, yyyy '@' hh:mm a z";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        holder.itemDateAndTimeTextView.setText(sdf.format(list.get(position).itemDateAndTime));
        // the correct way to get today's date
        Date today = Calendar.getInstance().getTime();
        if (list.get(position).itemDateAndTime.before(today)){
            holder.itemDateAndTimeTextView.setTextColor(Color.parseColor(	"#FF0000"));
        }
        else {
            holder.itemDateAndTimeTextView.setTextColor(Color.parseColor(	"#3CB371"));
        }

        holder.itemStatusCheckBox.setChecked(list.get(position).itemStatus);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyAdapterItemClickListener.onItemClicked(position);
            }
        });
    }

    public int getItemCount(){
        return list.size();
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder{
        TextView itemTitleTextView, itemDateAndTimeTextView;
        CheckBox itemStatusCheckBox;

        public ToDoViewHolder (View itemView){
            super(itemView);
            itemStatusCheckBox = itemView.findViewById(R.id.itemStatusCheckBox);
            itemTitleTextView = itemView.findViewById(R.id.itemTitleTextView);
            itemDateAndTimeTextView = itemView.findViewById(R.id.itemTimeAndDateTextView);
        }
    }
}