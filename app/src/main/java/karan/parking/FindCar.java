package karan.parking;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by karan on 10/10/15.
 */
public class FindCar extends Fragment {
    View view;
    Double Latitude=0.0,Longitude=0.0;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.find_car, container, false);
            MainActivity activity=(MainActivity)getActivity();
            Latitude = activity.getLatitude();
            Longitude =  activity.getLongitude();

            return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        System.out.println("Latitude" + Latitude + "  Longitude" + Longitude);
        button=(Button)view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+Latitude+","+Longitude);
                Intent mapIntent=new Intent((Intent.ACTION_VIEW),gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if(mapIntent.resolveActivity(getActivity().getPackageManager())!=null)
                    startActivity(mapIntent);

            }
        });

    }
}
