package karan.parking;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.security.Security;
import java.util.List;
import java.util.Locale;

/**
 * Created by karan on 9/10/15.
 */
public class SaveLocation extends Fragment {
    View view;
    Button button;
    LocationManager locationManager;
    Location location;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    LocationHistory history;
    OnHeadlineSelectedListener mCallback;

    public interface OnHeadlineSelectedListener {
        public void setLatitude(double Latitude);

        public void setLongitude(double Longitude);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.save_location, container, false);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_TIME_BETWEEN_UPDATES, new MyLocationListener());



        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = (Button) view.findViewById(R.id.button);
   //     locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            System.out.println("IF");

            return;
        }

        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, new MyLocationListener());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean gpsEnabled= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                String providerName=null;
               if(gpsEnabled!=true)
                {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    Toast.makeText(getActivity(),"katta",Toast.LENGTH_SHORT).show();
                }
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    System.out.println("if");
                    return;
                }
                if(locationManager==null)
                {
                    System.out.println("Location manager is null");
                   // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_TIME_BETWEEN_UPDATES, new MyLocationListener());

                }
                location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);



                if (location!=null) {

                    String message = String.format("Location \n Longitude: %1$s \n Latitude: %2$s", location.getLongitude(), location.getLatitude());
                    Activity activity= getActivity();
                    mCallback= (OnHeadlineSelectedListener)activity;
                    mCallback.setLatitude(location.getLatitude());
                    mCallback.setLongitude(location.getLongitude());
                    history=new LocationHistory();
                    Geocoder geocoder;
                    List<Address> addresses=null;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    System.out.println("address"+address+"  city  "+city);
                    history.saveRecord(address, city);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            history.save();
                        }
                    });

                  }
                else
                {
                    Toast.makeText(getActivity(),"try after one minute",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
