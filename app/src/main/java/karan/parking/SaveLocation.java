package karan.parking;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.Security;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by karan on 9/10/15.
 */
public class SaveLocation extends android.support.v4.app.Fragment {
    View view;
    Button button;
    LocationManager locationManager;
    Location location;
    TextView textView;
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
        textView=(TextView)view.findViewById(R.id.save);

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




        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button = (Button) view.findViewById(R.id.button);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATES, MINIMUM_TIME_BETWEEN_UPDATES, new MyLocationListener());
        location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
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
                Log.d("Gpsenabled",gpsEnabled+"");
                String providerName=null;
               if(gpsEnabled!=true)
                {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    Toast.makeText(getActivity(),"turn on gps",Toast.LENGTH_SHORT).show();
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
                    System.out.println("Provider"+location.getProvider());
                    String message = String.format("Location \n Longitude: %1$s \n Latitude: %2$s", location.getLongitude(), location.getLatitude());
                    Activity activity= getActivity();
                    mCallback= (OnHeadlineSelectedListener)activity;
                    mCallback.setLatitude(location.getLatitude());
                    mCallback.setLongitude(location.getLongitude());
                    history=new LocationHistory();
                    Geocoder geocoder;
                    List<Address> addresses=null;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    String address=null;
                    String city=null;
                    int flag=1;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                         address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                         city = addresses.get(0).getLocality();
                    } catch (IOException e) {
                        e.printStackTrace();

                    }


                    Calendar c=Calendar.getInstance();
                    if(address==null){
                        textView.setText("Your Car is parked at: Latitude"+location.getLatitude()+" Longitude "+location.getLongitude());
                        history.saveRecord(location.getLatitude() + "", location.getLongitude() + "", c.getTime());
                        Toast.makeText(getActivity(),"car parked at "+location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_SHORT).show();



                    }
                    else
                    {
                        textView.setText("your car is parked at "+address+ ""+city);
                        history.saveRecord(address, city, c.getTime());
                        Toast.makeText(getActivity(),"car parked at "+address+" "+city,Toast.LENGTH_SHORT).show();

                    }

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
