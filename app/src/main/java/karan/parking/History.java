package karan.parking;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karan on 9/10/15.
 */
public class History extends android.support.v4.app.Fragment {
    View view;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.history,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.my_recycler_view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        List<LocationHistory> history=LocationHistory.listAll(LocationHistory.class);
        MyRecyclerView myRecyclerView=new MyRecyclerView(history);
        recyclerView.setAdapter(myRecyclerView);
        return view;
    }
}
