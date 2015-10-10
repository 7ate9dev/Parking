package karan.parking;

import android.content.Context;

import com.orm.SugarApp;
import com.orm.SugarRecord;

/**
 * Created by karan on 11/10/15.
 */
public class LocationHistory extends SugarRecord<LocationHistory> {
    private String address;
    private String city;

    public LocationHistory(){

    }
    public void saveRecord(String address,String city)
    {
        this.address=address;
        this.city=city;
    }

}
