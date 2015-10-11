package karan.parking;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by karan on 11/10/15.
 */
public class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.Holder> {
    List<LocationHistory> history;
    public MyRecyclerView(List<LocationHistory> data)
    {
        this.history=data;
    }
    @Override
    public MyRecyclerView.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row,parent,false);
        Holder holder=new Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.address.setText(history.get(position).getAddress());
        holder.date.setText(history.get(position).getDate());
    }




    @Override
    public int getItemCount() {
        return this.history.size();
    }
    public static class Holder extends RecyclerView.ViewHolder{
        TextView address,date;
        CardView cv;
        public Holder(View itemView) {
            super(itemView);
            cv=(CardView)itemView.findViewById(R.id.card_view);
            address=(TextView)itemView.findViewById(R.id.textView);
            date=(TextView)itemView.findViewById(R.id.textView2);
        }
    }
}
