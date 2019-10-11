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

    private Context context;
    private List<Item> list;
    private OnToDoAdapterItemClickListener onMyAdapterItemClickListener;

    //Instantiate the ToDoAdapter
     ToDoAdapter (Context context, List<Item> list, OnToDoAdapterItemClickListener onMyAdapterItemClickListener){
        this.list = list;
        this.context = context;
        this.onMyAdapterItemClickListener = onMyAdapterItemClickListener;
    }

    //Inflate row.xml for each recycleView item
    @NonNull
    @Override
    public ToDoAdapter.ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ToDoViewHolder(view);
    }

    //Called to display item details in the recycler view
    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ToDoViewHolder holder, final int position) {
        holder.itemTitleTextView.setText(list.get(position).itemTitle); //Set the itemTitleTextView in the row layout to itemTitle value
        String format = "EEE, MMM d, yyyy '@' hh:mm a z"; //Format Date and Time
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        holder.itemDateAndTimeTextView.setText(sdf.format(list.get(position).itemDateAndTime)); //Set the itemDateAndTime in the row layout to the formatted date and time
        Date today = Calendar.getInstance().getTime(); //Gets today's date and hour to compare it with each item's due date and time
        if (list.get(position).itemDateAndTime.before(today)){
            holder.itemDateAndTimeTextView.setTextColor(Color.parseColor(	"#FF0000")); //If the due date is passed, display the due date in red
        }
        else {
            holder.itemDateAndTimeTextView.setTextColor(Color.parseColor(	"#3CB371")); //If the due date is not passed, display the due date in green
        }
        holder.itemStatusCheckBox.setChecked(list.get(position).itemStatus); //Display the item status value in the checkbox placed in the row layout
        holder.itemView.setOnClickListener(v -> onMyAdapterItemClickListener.onItemClicked(position)); //Return the position of the item when it is clicked by the user
    }

    //Returns the count of the items displayed in the recyclerView
    public int getItemCount(){
        return list.size();
    }

    //Captures the objects from the row layout
    class ToDoViewHolder extends RecyclerView.ViewHolder{
        TextView itemTitleTextView, itemDateAndTimeTextView;
        CheckBox itemStatusCheckBox;

         ToDoViewHolder (View itemView){
            super(itemView);
            itemStatusCheckBox = itemView.findViewById(R.id.itemStatusCheckBox);
            itemTitleTextView = itemView.findViewById(R.id.itemTitleTextView);
            itemDateAndTimeTextView = itemView.findViewById(R.id.itemTimeAndDateTextView);
        }
    }
}