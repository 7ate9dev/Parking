package karan.parking;

import android.app.Fragment;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by karan on 10/10/15.
 */
public class FindCar extends android.support.v4.app.Fragment {
    View view;
    Double Latitude=0.0,Longitude=0.0;
    Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.find_car, container, false);
        TextView textView=(TextView)view.findViewById(R.id.textView4);
            Main2Activity activity=(Main2Activity)getActivity();
            Latitude = activity.getLatitude();
            Longitude =  activity.getLongitude();
            if(Latitude!=0.0)
            {
                Geocoder geocoder;
                List<Address> addresses=null;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                String address=null;
                String city=null;
                try {
                    addresses = geocoder.getFromLocation(Latitude,Longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                     address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                     city = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(address==null)
                textView.setText("Your Car is parked at: Latitude"+Latitude+" Longitude "+Longitude);
                else
                    textView.setText("your car is parked at "+address+ ""+city);


            }return view;
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
